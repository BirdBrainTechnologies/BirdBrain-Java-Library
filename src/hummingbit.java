import com.oracle.tools.packager.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The HummingBit Library functions.
 */
public class hummingbit {
    private HttpURLConnection connection = null;
    private DataOutputStream out = null;
    private static String baseUrl;
    private URL requestUrl;
    private String deviceInstance;
    private static final String SCREEN_UP = "ScreenUp";
    private static final String SCREEN_DOWN = "ScreenDown";
    private static final String TILT_LEFT = "TiltLeft";
    private static final String TILT_RIGHT = "TiltRight";
    private static final String LOGO_UP = "LogoUp";
    private static final String LOGO_DOWN = "LogoDown";
    private static final JFrame curFrame = new JFrame();

    /**
     * verifyOutputResponse checks whether the HTTP request response is valid or not.
     * If the response code indicates that the response is invalid, the connector will be disconnected.
     */
    private void verifyOutputResponse() {
        try {
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                System.out.println("GET request not worked");
                disconnect();
            }
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            disconnect();
        }
    }

    /**
     * verifyResponse is used for retrieving sensor information.
     * It checks whether the HTTP request request is valid or not and returns the response if it is valid.
     * Otherwise, the connector will be disconnected.
     *
     * @return
     */
    private String verifyResponse() {
        try {
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                if (response.toString().contains("Not Connected")) {
                    return "Not Connected";
                } else {
                    return response.toString();
                }
            } else {
                System.out.println("GET request not worked");
                disconnect();
                return "Not Connected";
            }
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            disconnect();
            return "Not Connected";
        }
    }

    /**
     * default constructor for the library. Construct the baseUrl and set the default device to be A
     */
    public hummingbit() {
        baseUrl = "http://127.0.0.1:30061/hummingbird/";
        deviceInstance = "A";
    }

    /**
     * constructor for the library. Construct the baseUrl and set the default device to be input.
     *
     * @param device the input device that will be specified by the user.
     */
    public hummingbit(String device) {
        baseUrl = "http://127.0.0.1:30061/hummingbird/";
        deviceInstance = device;
    }

    /**
     * setPositionServo sets the positionServo at a given port to a specific angle.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param port     The port that the position servo is attached to. (Range: 1-4)
     * @param position The angle of the position servo. (Range: 0-180)
     */
    public void setPositionServo(int port, int position) {
        if (!(port >= 1 && port <= 4) || !(position >= 0 && position <= 180)) {
            JOptionPane.showMessageDialog(curFrame, "Invalid Argument");
            return;
        }
        try {
            int degrees = (int) (position * 254.0 / 180.0);
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String servoUrl = (resultUrl.append("out/")
                    .append("servo/")
                    .append(Integer.toString(port) + "/")
                    .append(Integer.toString(degrees) + "/")
                    .append(deviceInstance)).toString();
            if (deviceInstance == "") {
                servoUrl = servoUrl.substring(0, servoUrl.length() - 1);
            }
            requestUrl = new URL(servoUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            verifyOutputResponse();
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
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
        if (!(port >= 1 && port <= 4) || !(speed >= -100 && speed <= 100)) {
            JOptionPane.showMessageDialog(curFrame, "Invalid Argument");
            return;
        }
        try {
            if ((speed > -10) && (speed < 10)) {
                speed = 255;
            } else {
                speed = ((speed * 23) / 100) + 122;
            }

            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String rotationUrl = (resultUrl.append("out/")
                    .append("rotation/")
                    .append(Integer.toString(port) + "/")
                    .append(Integer.toString(speed) + "/")
                    .append(deviceInstance)).toString();
            if (deviceInstance == "") {
                rotationUrl = rotationUrl.substring(0, rotationUrl.length() - 1);
            }
            requestUrl = new URL(rotationUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            verifyOutputResponse();
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
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
        if (!(port >= 1 && port <= 4) || !(intensity >= 0 && intensity <= 100)) {
            JOptionPane.showMessageDialog(curFrame, "Invalid Argument");
            return;
        }
        try {
            intensity = (int) (intensity * 255.0 / 100.0);
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String ledUrl = (resultUrl.append("out/")
                    .append("led/")
                    .append(Integer.toString(port) + "/")
                    .append(Integer.toString(intensity) + "/")
                    .append(deviceInstance)).toString();
            if (deviceInstance == "") {
                ledUrl = ledUrl.substring(0, ledUrl.length() - 1);
            }
            requestUrl = new URL(ledUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            verifyOutputResponse();
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
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
        if (!(port >= 1 && port <= 4) || !(redIntensity >= 0 && redIntensity <= 100) ||
                !(greenIntensity >= 0 && greenIntensity <= 100) || !(blueIntensity >= 0 && blueIntensity <= 100)) {
            JOptionPane.showMessageDialog(curFrame, "Invalid Argument");
            return;
        }
        try {
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
            if (deviceInstance == "") {
                triLedUrl = triLedUrl.substring(0, triLedUrl.length() - 1);
            }
            requestUrl = new URL(triLedUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            verifyOutputResponse();
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
        }
    }

    /**
     * print lets the LED Array display a given message.
     *
     * @param msg The message that will be displayed on the LED Array.
     */
    public void print(String msg) {
        try {
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String printUrl = (resultUrl.append("out/")
                    .append("print/")
                    .append(msg + "/")
                    .append(deviceInstance)).toString();
            if (deviceInstance == "") {
                printUrl = printUrl.substring(0, printUrl.length() - 1);
            }
            requestUrl = new URL(printUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            verifyOutputResponse();
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
        }
    }

    /**
     * setDisplay lets the LED Array display a pattern based on an array of booleans.
     *
     * @param booVals The list of booleans that the function takes in to set the LED Array.
     *                True means on and False means off.
     */
    public void setDisplay(boolean[] booVals) {
        try {
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            int ledLen = booVals.length;

            resultUrl = resultUrl.append("out/")
                    .append("symbol/");
            if (deviceInstance != "") {
                resultUrl = resultUrl.append(deviceInstance + "/");
            }

            for (int i = 0; i < ledLen; i++) {
                resultUrl = resultUrl.append(String.valueOf(booVals[i]) + "/");
            }

            String symbolUrl = resultUrl.toString();
            symbolUrl = symbolUrl.substring(0, symbolUrl.length() - 1);

            requestUrl = new URL(symbolUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            verifyOutputResponse();
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
        }
    }

    /**
     * getSensorValue returns the raw sensor value at a given port
     *
     * @param port The port that the sensor is attached to. (Range: 1-4)
     */
    private int getSensorValue(int port) {
        try {
            int response;
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String sensorUrl = (resultUrl.append("in/")
                    .append("sensor/")
                    .append(Integer.toString(port) + "/")
                    .append(deviceInstance)).toString();

            if (deviceInstance == "") {
                sensorUrl = sensorUrl.substring(0, sensorUrl.length() - 1);
            }

            requestUrl = new URL(sensorUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            response = Integer.parseInt(verifyResponse());
            return response;
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            return -1;
        }
    }

    /**
     * getLight returns light sensor value at a given port after processing the raw sensor value retrieved.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param port The port that the light sensor is attached to. (Range: 1-4)
     */
    public int getLight(int port) {
        if (!(port >= 1 && port <= 4)) {
            JOptionPane.showMessageDialog(curFrame, "Invalid Port Number");
            return -1;
        }
        int sensorResponse = getSensorValue(port);
        return (int) (sensorResponse * 100.0 / 255.0);
    }

    /**
     * getSound returns sound sensor value at a given port after processing the raw sensor value retrieved.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param port The port that the sound sensor is attached to. (Range: 1-4)
     */
    public int getSound(int port) {
        if (!(port >= 1 && port <= 4)) {
            JOptionPane.showMessageDialog(curFrame, "Invalid Port Number");
            return -1;
        }
        int sensorResponse = getSensorValue(port);
        return (int) (sensorResponse * 200.0 / 255.0);
    }

    /**
     * getDistance returns distance sensor value at a given port after processing the raw sensor value retrieved.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param port The port that the distance sensor is attached to. (Range: 1-4)
     */
    public int getDistance(int port) {
        if (!(port >= 1 && port <= 4)) {
            JOptionPane.showMessageDialog(curFrame, "Invalid Port Number");
            return -1;
        }
        int sensorResponse = getSensorValue(port);
        return sensorResponse;
    }

    /**
     * getDial returns dial value at a given port after processing the raw sensor value retrieved.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param port The port that the dial is attached to. (Range: 1-4)
     */
    public int getDial(int port) {
        if (!(port >= 1 && port <= 4)) {
            JOptionPane.showMessageDialog(curFrame, "Invalid Port Number");
            return -1;
        }
        int sensorResponse = getSensorValue(port);
        int processedResponse = (int) (sensorResponse * 100.0 / 230.0);
        return processedResponse >= 100 ? 100 : processedResponse;
    }

    /**
     * getAccelerationInDirs returns accleration value in a specified direction.
     *
     * @param dir The direction of which the accleration will be returned.
     */
    private double getAccelerationInDirs(String dir) {
        try {
            double response;
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String acclUrl = (resultUrl.append("in/")
                    .append("Accelerometer/")
                    .append(dir + "/")
                    .append(deviceInstance)).toString();

            if (deviceInstance == "") {
                acclUrl = acclUrl.substring(0, acclUrl.length() - 1);
            }

            requestUrl = new URL(acclUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            response = Double.parseDouble(verifyResponse());
            return response;
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            return -1;
        }
    }

    /**
     * getMagnetometerValInDirs returns magnetometer value in a specified direction.
     *
     * @param dir The direction of which the magnetometer value will be returned.
     */
    private double getMagnetometerValInDirs(String dir) {
        try {
            double response;
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String magUrl = (resultUrl.append("in/")
                    .append("Magnetometer/")
                    .append(dir + "/")
                    .append(deviceInstance)).toString();

            if (deviceInstance == "") {
                magUrl = magUrl.substring(0, magUrl.length() - 1);
            }

            requestUrl = new URL(magUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            response = Double.parseDouble(verifyResponse());
            return response;
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            return -1;
        }
    }

    /**
     * getAcceleration returns accelerations in 3 directions (X,Y,Z) in m/s^2.
     *
     * @return the accelerations in 3 directions (X,Y,Z) in m/s^2.
     */
    public double[] getAcceleration() {
        double[] accelerations = new double[3];
        double resX = getAccelerationInDirs("X");
        double resY = getAccelerationInDirs("Y");
        double resZ = getAccelerationInDirs("Z");
        accelerations[0] = resX;
        accelerations[1] = resY;
        accelerations[2] = resZ;
        return accelerations;
    }

    /**
     * getMagnetometer returns magnetometer values in 3 directions (X,Y,Z) in microT.
     *
     * @return the magnetometer values in 3 directions (X,Y,Z) in microT.
     */
    public double[] getMagnetometer() {
        double[] magnetometerVals = new double[3];
        double resX = getMagnetometerValInDirs("X");
        double resY = getMagnetometerValInDirs("Y");
        double resZ = getMagnetometerValInDirs("Z");
        magnetometerVals[0] = resX;
        magnetometerVals[1] = resY;
        magnetometerVals[2] = resZ;
        return magnetometerVals;
    }

    /**
     * getCompass returns the direction in degrees.
     *
     * @return the direction in degrees. (Range: 0-360)
     */
    public int getCompass() {
        try {
            int response;
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String compasslUrl = (resultUrl.append("in/")
                    .append("Compass/")
                    .append(deviceInstance)).toString();

            if (deviceInstance == "") {
                compasslUrl = compasslUrl.substring(0, compasslUrl.length() - 1);
            }

            requestUrl = new URL(compasslUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            response = (int) Double.parseDouble(verifyResponse());
            return response;
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            return -1;
        }
    }

    /**
     * getButton takes in a button and checks whether it is pressed.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param button the button that will be checked whether is it pressed or not. (Range: "A", "B")
     * @return "True" if the button is pressed and "false" otherwise.
     */
    public String getButton(String button) {
        if (!(button.equals("A") || button.equals("B"))) {
            JOptionPane.showMessageDialog(curFrame, "Invalid Button");
            return "";
        }
        try {
            String response;
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String buttonUrl = (resultUrl.append("in/")
                    .append("button/")
                    .append(button + "/")
                    .append(deviceInstance)).toString();

            if (deviceInstance == "") {
                buttonUrl = buttonUrl.substring(0, buttonUrl.length() - 1);
            }

            requestUrl = new URL(buttonUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            response = verifyResponse();
            return response;
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            return "";
        }
    }

    /**
     * isShaking indicates whether the device is being shaked
     *
     * @return true if the device is being shaked and false otherwise.
     */
    public boolean isShaking() {
        return getOrientationBoolean("Shake");
    }

    /**
     * getOrientationBoolean checks whether the device currently being held to a specific orientation.
     *
     * @param orientation The orientation that will be checked.
     * @return "true" if the device is held to the orientation and false otherwise.
     */
    private boolean getOrientationBoolean(String orientation) {
        try {
            boolean response;
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String orientationUrl = (resultUrl.append("in/")
                    .append("orientation/")
                    .append(orientation + "/")
                    .append(deviceInstance)).toString();

            if (deviceInstance == "") {
                orientationUrl = orientationUrl.substring(0, orientationUrl.length() - 1);
            }

            requestUrl = new URL(orientationUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            response = Boolean.parseBoolean(verifyResponse());
            return response;
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            return false;
        }

    }

    /**
     * getOrientation informs about the device's current orientation.
     *
     * @return the orientation of the device. (Range: screenUp, screenDown, tiltLeft, tiltRight, logoUp, logoDown)
     */
    public String getOrientation() {
        boolean screenUp = getOrientationBoolean(SCREEN_UP);
        boolean screenDown = getOrientationBoolean(SCREEN_DOWN);
        boolean tiltLeft = getOrientationBoolean(TILT_LEFT);
        boolean tiltRight = getOrientationBoolean(TILT_RIGHT);
        boolean logoUp = getOrientationBoolean(LOGO_UP);
        boolean logoDown = getOrientationBoolean(LOGO_DOWN);
        if (screenUp) return SCREEN_UP;
        else if (screenDown) return SCREEN_DOWN;
        else if (screenDown) return SCREEN_DOWN;
        else if (tiltLeft) return TILT_LEFT;
        else if (tiltRight) return TILT_RIGHT;
        else if (logoUp) return LOGO_UP;
        else if (logoDown) return LOGO_DOWN;
        return "";
    }

    /**
     * disconnect disconnects the library from the connector.
     */
    public void disconnect() {
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
    }
}
