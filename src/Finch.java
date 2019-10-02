import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class extends the Robot class to incorporate functions to control the inputs and outputs
 * of the Finch. It includes methods to set the values of motors and LEDs, as well
 * as methods to read the values of the sensors.
 *
 * Krissie Lauwers, BirdBrain Technologies LLC
 * October 2019
 */
public class Finch extends Robot {

    /**
     * Default constructor for the library. Set the default device to be A.
     */
    public Finch() {
        this("A");
    }

    /**
     * General constructor for the library. Set the device to be "A", "B", or "C".
     *
     * @param device The letter corresponding to the Hummingbird device, which much be "A", "B", or "C".
     * The letter that identifies the Hummingbird device is assigned by the BlueBird Connector.
     */
    public Finch(String device) {
        if (!((device == "A")||(device == "B")||(device == "C"))) {
            System.out.println("Error: Device must be A, B, or C.");
            System.exit(0);
        } else {
            deviceInstance = device;
            if (!isConnectionValid()) System.exit(0);
            if (!isFinch()) System.exit(0);
        }
    }

    /**
     * This function sends a request to BlueBird Connector to determine whether or not
     * the device is a Finch.
     */
    private boolean isFinch() {
        StringBuilder newURL = new StringBuilder(baseUrl);
        String testURL = (newURL.append("in/isFinch/static/")
                .append(deviceInstance)).toString();

        String stringResponse = sendHttpRequest(testURL);
        if (stringResponse.equals("false")) {
            System.out.println("Error: Device " + deviceInstance + " is not a Finch");
            return false;
        } else if (stringResponse.equals("Not Connected")) {
            System.out.println("Error: Device " + deviceInstance + " is not connected.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Formats the direction string for sending to the bluebird connector.
     * If the selection made is not acceptable, returns 'Neither'.
     * @param direction
     * @return
     */
    private String formatForwardBackward(String direction) {
        switch (direction) {
            case "F":
            case "Forward":
            case "forward":
                return "Forward";
            case "B":
            case "Backward":
            case "backward":
                return "Backward";
            default:
                System.out.println("Error: Please specify direction as F or B.");
                return "Neither";
        }
    }

    /**
     * Formats the direction string for sending to the bluebird connector.
     * If the selection made is not acceptable, returns 'Neither'.
     * @param direction
     * @return
     */
    private String formatRightLeft(String direction) {
        switch (direction) {
            case "R":
            case "Right":
            case "right":
                return "Right";
            case "L":
            case "Left":
            case "left":
                return "Left";
            default:
                System.out.println("Error: Please specify direction as R or L.");
                return "Neither";
        }
    }

    /**
     * Create a url string given a list of arguments to include
     * @param args
     * @return
     */
    private String getUrl(String [] args) {
        StringBuilder resultUrl = new StringBuilder(baseUrl);
        for (String arg : args) {
            resultUrl.append(arg + "/");
        }
        String url = resultUrl.toString();
        return url.substring(0, url.length() - 1); //remove the trailing '/'
    }

    /**
     * Sends a request for the finch to move forward or backward a given distance
     * at a given speed. Direction should be specified as "F" or "B".
     * @param direction - F or B for forward or backward
     * @param distance - Distance to travel in cm. (Range: 0 to 500)
     * @param speed - Speed as a percent (Range: 0 to 100)
     */
    public void setMove(String direction, int distance, int speed) {
        String dir = formatForwardBackward(direction);
        if (dir.equals("Neither")) { return; }

        distance = clampParameterToBounds(distance, 0, 500);
        speed = clampParameterToBounds(speed, 0, 100);

        String [] urlArgs = {"out", "move", deviceInstance, dir, Integer.toString(distance), Integer.toString(speed)};
        String url = getUrl(urlArgs);
        httpRequestOut(url);
    }

    /**
     * Sends a request for the finch to turn right or left to the give angle
     * at the given speed.
     * @param direction - R or L for right or left
     * @param angle - Angle of the turn in degrees (Range: 0 to 360)
     * @param speed - Speed of the turn as a percent (Range: 0 to 100)
     */
    public void setTurn(String direction, int angle, int speed) {
        String dir = formatRightLeft(direction);
        if (dir.equals("Neither")) { return; }

        angle = clampParameterToBounds(angle, 0, 360);
        speed = clampParameterToBounds(speed, 0, 100);

        String [] urlArgs = {"out", "turn", deviceInstance, dir, Integer.toString(angle), Integer.toString(speed)};
        String url = getUrl(urlArgs);
        httpRequestOut(url);
    }

    /**
     * Set the right and left motors of the finch to the speeds given.
     * @param leftSpeed - Speed as a percent (Range: 0 to 100)
     * @param rightSpeed - Speed as a percent (Range: 0 to 100)
     */
    public void setMotors(int leftSpeed, int rightSpeed) {
        leftSpeed = clampParameterToBounds(leftSpeed, -100, 100);
        rightSpeed = clampParameterToBounds(rightSpeed, -100, 100);

        String [] urlArgs = {"out", "wheels", deviceInstance, Integer.toString(leftSpeed), Integer.toString(rightSpeed)};
        String url = getUrl(urlArgs);
        httpRequestOut(url);
    }

    /**
     * Stop the finch motors
     */
    public void stop() {
        String [] urlArgs = {"out", "stopFinch", deviceInstance};
        String url = getUrl(urlArgs);
        httpRequestOut(url);
    }

    /**
     * Private method to set led intensity. Used to set beak and tail leds.
     * @param port - led to set. 1 is beak. 2-5 are tail. (Range: 1 to 5)
     * @param redIntensity - red intensity (Range: 0 to 100)
     * @param greenIntensity - green intensity (Range: 0 to 100)
     * @param blueIntensity - blue intensity (Range: 0 to 100)
     */
    private void setTriLED(int port, int redIntensity, int greenIntensity, int blueIntensity) {
        if ((port < 1) || (port > 5)) {		// Check that port is valid
            return;
        }
        redIntensity = clampParameterToBounds(redIntensity,0,100);
        greenIntensity = clampParameterToBounds(greenIntensity,0,100);
        blueIntensity = clampParameterToBounds(blueIntensity,0,100);

        // Scale
        redIntensity = (int) (redIntensity * 255.0 / 100.0);
        greenIntensity = (int) (greenIntensity * 255.0 / 100.0);
        blueIntensity = (int) (blueIntensity * 255.0 / 100.0);

        String [] urlArgs = {"out", "triled", Integer.toString(port), Integer.toString(redIntensity), Integer.toString(greenIntensity), Integer.toString(blueIntensity), deviceInstance};
        String url = getUrl(urlArgs);
        httpRequestOut(url);
    }

    /**
     * Set the finch beak to the given rgb color.
     * @param red - red intensity (Range: 0 to 100)
     * @param green - green intensity (Range: 0 to 100)
     * @param blue - blue intensity (Range: 0 to 100)
     */
    public void setBeak(int red, int green, int blue) {
        setTriLED(1, red, green, blue);
    }

    /**
     * Set the specified tail led to the specified rgb color.
     * @param ledNum - led to set (Range: 1 to 4)
     * @param red - red intensity (Range: 0 to 100)
     * @param green - green intensity (Range: 0 to 100)
     * @param blue - blue intensity (Range: 0 to 100)
     */
    public void setTail(int ledNum, int red, int green, int blue) {
        setTriLED(ledNum + 1, red, green, blue);
    }

    /**
     * Set the finch buzzer to play the given note for the given duration
     * @param note - midi note number to play (Range: 32 to 135)
     * @param beats - duration in beats (Range: 0 to 16)
     */
    public void playNote(int note, double beats) {
        note = clampParameterToBounds(note, 32, 135);
        beats = clampParameterToBounds(beats,0,16);
        beats = beats * 1000;

        String [] urlArgs = {"out", "playnote", Integer.toString(note), Integer.toString((int)beats), deviceInstance};
        String url = getUrl(urlArgs);
        httpRequestOut(url);
    }

    /**
     * Reset the finch encoder values to 0.
     */
    public void resetEncoders() {
        String [] urlArgs = {"out", "resetEncoders", deviceInstance};
        String url = getUrl(urlArgs);
        httpRequestOut(url);
    }

    /**
     * Private function to get the value of a sensor
     * @param sensor - Light, Distance, Line, or Encoder
     * @param port - Right, Left, or static
     * @return - sensor value returned by bluebird connector or -1 in the case of a problem.
     */
    private double getSensor(String sensor, String port) {
        if (!port.equals("static")) {
            port = formatRightLeft(port);
        }
        if (port.equals("Neither")) { return -1; }
        String [] urlArgs = {"in", sensor, port, deviceInstance};
        String url = getUrl(urlArgs);
        return httpRequestInDouble(url);
    }

    /**
     * Get the current value of the right or left encoder
     * @param direction - R or L to specify right or left
     * @return - encoder value in rotations
     */
    public double getEncoders(String direction) {
        double value = getSensor("Encoder", direction);
        value = Math.round(value * 100.0)/100.0;
        return value;
    }

    /**
     * Get the current value of the finch distance sensor
     * @return - the distance to the closest obstacle in cm
     */
    public int getDistance() {
        double value = getSensor("Distance", "static");
        return (int) Math.round(value * 0.0919);
    }

    /**
     * Get the current value of the specified finch light sensor
     * @param direction - R or L to specify right or left
     * @return - brightness as a value 0-100
     */
    public int getLight(String direction) {
        double value = getSensor("Light", direction);
        return (int) Math.round(value * 100/255);
    }

    /**
     * Get the current value of the specified finch line sensor.
     * Return value is inverted (100 - value) so that more reflected
     * light = bigger number
     * @param direction - R or L to specify right or left
     * @return - brightness as a value 0-100
     */
    public int getLine(String direction) {
        double value = getSensor("Line", direction);
        return 100 - (int)value;
    }
}
