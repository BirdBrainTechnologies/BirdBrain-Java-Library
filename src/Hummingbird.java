
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * This class extends the Microbit class to incorporate functions to control the inputs and outputs
 * of the Hummingbird Bit. It includes methods to set the values of motors and LEDs, as well
 * as methods to read the values of the sensors.
 * 
 * Mike Yuan and Bambi Brewer, BirdBrain Technologies LLC
 * November 2018
 */
public class Hummingbird extends Microbit {

	private String outputError = "Error: Could not set Hummingbird LED output: ";
	private String inputError = "Error: Could not read Hummingbird sensor: ";
    
	/**
     * Default constructor for the library. Set the default device to be A.
     */
    public Hummingbird() {
          deviceInstance = "A";
    }

    /* This function checks whether a port is within the given bounds. It returns a boolean value 
	   that is either true or false and prints an error if necessary. */
	protected boolean isPortValid(int port, int portMax) {
		if ((port < 1) || (port > portMax)) {
			System.out.println("Error: Please choose a port value between 1 and " + portMax);
			return false;
		}
		else
			return true;	
	}
    /**
     * General constructor for the library. Set the device to be "A", "B", or "C".
     *
     * @param device The letter corresponding to the Hummingbird device, which much be "A", "B", or "C". 
     * The letter that identifies the Hummingbird device is assigned by the BlueBird Connector.
     *      */
    public Hummingbird(String device) {
        if (!((device == "A")||(device == "B")||(device == "C"))) {
        	System.out.println("Error: Device must be A, B, or C.");
        	deviceInstance = "A";
        } else {
        	deviceInstance = device;
        }
    }

    /**
     * setPositionServo sets the positionServo at a given port to a specific angle.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param port     The port that the position servo is attached to. (Range: 1-4)
     * @param position The angle of the position servo. (Range: 0-180)
     */
    public void setPositionServo(int port, int position) {
    	if (!(port >= 1 && port <= 4)) {		// Check that port is valid
        	System.out.println("Error: Please choose a port value between 1 and 4");
        	return; 
        } else if (!(position >= 0 && position <= 180)) {	// Correct the position if it is out of bounds
            System.out.println("Warning: Please choose an angle between 0 and 180");
            position = Math.min(180,Math.max(0,position));
        }
        try {
            int degrees = (int) (position * 254.0 / 180.0);
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String servoUrl = (resultUrl.append("out/")
                    .append("servo/")
                    .append(Integer.toString(port) + "/")
                    .append(Integer.toString(degrees) + "/")
                    .append(deviceInstance)).toString();
           
            requestUrl = new URL(servoUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            verifyOutputResponse();
        } catch (IOException e) {
        		System.out.println(outputError + e.getMessage());
        }
    }

    /**
     * setRotationServo sets the rotationServo at a given port to a specific speed.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param port  The port that the rotation servo is attached to. (Range: 1-4)
     * @param speed The speed of the rotation servo. (Range: -100-100)
     */
    public void setRotationServo(int port, int speed) {
        if (!(port >= 1 && port <= 4)) {		// Check that port is valid
        	System.out.println("Error: Please choose a port value between 1 and 4");
        	return; 
        } else if (!(speed >= -100 && speed <= 100)) {	// Correct the speed if it is out of bounds
            System.out.println("Warning: Please choose a speed between -100 and 100");
            speed = Math.min(100,Math.max(-100,speed));
        }
        try {	// Send the command to the Hummingbird
            if ((speed > -10) && (speed < 10)) {	// dead zone to turn off the motor
                speed = 255;
            } else {	// Scale the speed so that it is semi-linear
                speed = ((speed * 23) / 100) + 122;
            }
            
            // Create the http command
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String rotationUrl = (resultUrl.append("out/")
                    .append("rotation/")
                    .append(Integer.toString(port) + "/")
                    .append(Integer.toString(speed) + "/")
                    .append(deviceInstance)).toString();
            
            requestUrl = new URL(rotationUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            verifyOutputResponse();
        } catch (IOException e) {
        		System.out.println(outputError + e.getMessage());
        }
    }

    /**
     * setLED sets the LED at a given port to a specific light intensity.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param port      The port that the LED is attached to. (Range: 1-4)
     * @param intensity The intensity of the LED. (Range: 0-100)
     */
    public void setLED(int port, int intensity) {
    	if (!(port >= 1 && port <= 3)) {		// Check that port is valid
        	System.out.println("Error: Please choose a port value between 1 and 3");
        	return; 
        } else if (!(intensity >= 0 && intensity <= 180)) {	// Correct the intensity if it is out of bounds
            System.out.println("Warning: Please choose an intensity between 0 and 100");
            intensity = Math.min(100,Math.max(0,intensity));
        }
        try {	// Create and send the http request
            intensity = (int) (intensity * 255.0 / 100.0);
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String ledUrl = (resultUrl.append("out/")
                    .append("led/")
                    .append(Integer.toString(port) + "/")
                    .append(Integer.toString(intensity) + "/")
                    .append(deviceInstance)).toString();
            
            requestUrl = new URL(ledUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            verifyOutputResponse();
        } catch (IOException e) {
        		System.out.println(outputError + e.getMessage());
        }
    }

    /**
     * setTriLED sets the triLED at a given port to a specific color.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param port           The port that the LED is attached to. (Range: 1-4)
     * @param redIntensity   The intensity of red light of the triLED. (Range: 0-100)
     * @param greenIntensity The intensity of green light of the triLED. (Range: 0-100)
     * @param blueIntensity  The intensity of blue light of the triLED. (Range: 0-100)
     */
    public void setTriLED(int port, int redIntensity, int greenIntensity, int blueIntensity) {
    	if (!(port >= 1 && port <= 2)) {		// Check that port is valid
        	System.out.println("Error: Please choose a port value between 1 and 2");
        	return; 
        } else if (!(redIntensity >= 0 && redIntensity <= 100) || !(greenIntensity >= 0 && greenIntensity <= 100) || !(blueIntensity >= 0 && blueIntensity <= 100)) {	// Correct the color intensities if it is out of bounds
            System.out.println("Warning: Please choose intensities between 0 and 100");
            redIntensity = Math.min(100,Math.max(0,redIntensity));
            greenIntensity = Math.min(100,Math.max(0,greenIntensity));
            blueIntensity = Math.min(100,Math.max(0,blueIntensity));
            System.out.println(blueIntensity);
        }
        try {	// Create and send the http request
            redIntensity = (int) (redIntensity * 255.0 / 100.0);
            greenIntensity = (int) (greenIntensity * 255.0 / 100.0);
            blueIntensity = (int) (blueIntensity * 255.0 / 100.0);

            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String triLedUrl = (resultUrl.append("out/")
                    .append("triled/")
                    .append(Integer.toString(port) + "/")
                    .append(Integer.toString(redIntensity) + "/")
                    .append(Integer.toString(greenIntensity) + "/")
                    .append(Integer.toString(blueIntensity) + "/")
                    .append(deviceInstance)).toString();
           
            requestUrl = new URL(triLedUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            verifyOutputResponse();
        } catch (IOException e) {
        		System.out.println(outputError + e.getMessage());
        }
    }
    
    void playNote(int note, double beats) {
    	if (!(note >= 32 && note <= 135)) {
    		System.out.println("Warning: Please choose a note between 32 and 135");
            note = Math.min(135,Math.max(32,note));
    	}
    	if (!(beats >= 0  && beats <= 16)) {
    		System.out.println("Warning: Please choose a number of beats between 0 and 16");
            beats = Math.min(16,Math.max(0,beats));
            System.out.println(beats);
    	}
    	System.out.println(beats);
    	try {	// Create and send the http request
    		beats = beats * 1000;
    		StringBuilder resultUrl = new StringBuilder(baseUrl);
            String buzzerUrl = (resultUrl.append("out/")
                    .append("playnote/")
                    .append(Integer.toString(note) + "/")
                    .append(Integer.toString((int)beats) + "/")
                    .append(deviceInstance)).toString();
            requestUrl = new URL(buzzerUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            verifyOutputResponse();
        } catch (IOException e) {
        		System.out.println(outputError + e.getMessage());
        }
    }

    /**
     * getSensorValue returns the raw sensor value at a given port
     *
     * @param port The port that the sensor is attached to. (Range: 1-3)
     */
    private int getSensorValue(int port) {
        try {
            int response;
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String sensorUrl = (resultUrl.append("in/")
                    .append("sensor/")
                    .append(Integer.toString(port) + "/")
                    .append(deviceInstance)).toString();
      

            requestUrl = new URL(sensorUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            response = Integer.parseInt(verifyResponse());
            return response;
        } catch (IOException e) {
        		System.out.println(inputError + e.getMessage());
            return -1;
        }
    }

    /**
     * getLight returns light sensor value at a given port after processing the raw sensor value retrieved.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param port The port that the light sensor is attached to. (Range: 1-3)
     */
    public int getLight(int port) {
        if (!(port >= 1 && port <= 3)) {
            System.out.println("Error: Please choose a port value between 1 and 3");
            return -1;
        }
        int sensorResponse = getSensorValue(port);
        return (int) (sensorResponse * 100.0 / 255.0);
    }

    /**
     * getSound returns sound sensor value at a given port after processing the raw sensor value retrieved.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param port The port that the sound sensor is attached to. (Range: 1-3)
     */
    public int getSound(int port) {
        if (!(port >= 1 && port <= 3)) {
            System.out.println("Error: Please choose a port value between 1 and 3");
            return -1;
        }
        int sensorResponse = getSensorValue(port);
        return (int) (sensorResponse * 200.0 / 255.0);
    }

    /**
     * getDistance returns distance sensor value at a given port after processing the raw sensor value retrieved.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param port The port that the distance sensor is attached to. (Range: 1-3)
     */
    public int getDistance(int port) {
        if (!(port >= 1 && port <= 3)) {
            System.out.println("Error: Please choose a port value between 1 and 3");
            return -1;
        }
        int sensorResponse = getSensorValue(port);
        return (int) (sensorResponse * 117.0 / 100.0);
    }

    /**
     * getDial returns dial value at a given port after processing the raw sensor value retrieved.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param port The port that the dial is attached to. (Range: 1-4)
     */
    public int getDial(int port) {
        if (!(port >= 1 && port <= 3)) {
            System.out.println("Error: Please choose a port value between 1 and 3");
            return -1;
        }
        int sensorResponse = getSensorValue(port);
        int processedResponse = (int) (sensorResponse * 100.0 / 230.0);
        return processedResponse >= 100 ? 100 : processedResponse;
    }
    
    /**
     * getDial returns dial value at a given port after processing the raw sensor value retrieved.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param port The port that the dial is attached to. (Range: 1-4)
     */
    public double getVoltage(int port) {
        if (!(port >= 1 && port <= 3)) {
            System.out.println("Error: Please choose a port value between 1 and 3");
            return -1;
        }
        int sensorResponse = getSensorValue(port);
        return (sensorResponse * 3.3 / 255);
    }

}
