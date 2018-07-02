import com.oracle.tools.packager.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class hummingbit {
    private HttpURLConnection connection = null;
    private DataOutputStream out = null;
    private static String baseUrl;
    private URL requestUrl;
    private String deviceInstance;


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
                System.out.println("response" + response.toString());
            } else {
                System.out.println("GET request not worked");
                disconnect();
            }
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            disconnect();
        }
    }

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

    public hummingbit() {
        baseUrl = "http://127.0.0.1:30061/hummingbird/";
        deviceInstance = "A";
    }

    public hummingbit(String device) {
        baseUrl = "http://127.0.0.1:30061/hummingbird/";
        deviceInstance = device;
    }

    public void setPositionServo(int port, int position) {
        try {
            int degrees = (int) (position * 254.0/180.0);
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String servoUrl = (resultUrl.append("out/")
                                        .append("servo/")
                                        .append(Integer.toString(port) + "/")
                                        .append(Integer.toString(degrees) + "/")
                                        .append(deviceInstance)).toString();
            if (deviceInstance == "") {
                servoUrl = servoUrl.substring(0, servoUrl.length() - 1);
            }
            System.out.println("servoUrl: " + servoUrl);
            requestUrl = new URL(servoUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            System.out.println("positioning");
            verifyOutputResponse();
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
        }
    }

    public void setRotationServo(int port, int speed) {
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
            System.out.println("servoUrl: " + rotationUrl);
            requestUrl = new URL(rotationUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            System.out.println("rotating");
            verifyOutputResponse();
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
        }
    }


    public void setLED(int port, int intensity) {
        try {
            intensity = (int) (intensity * 255.0/100.0);
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String ledUrl = (resultUrl.append("out/")
                    .append("led/")
                    .append(Integer.toString(port) + "/")
                    .append(Integer.toString(intensity) + "/")
                    .append(deviceInstance)).toString();
            if (deviceInstance == "") {
                ledUrl = ledUrl.substring(0, ledUrl.length() - 1);
            }
            System.out.println("lighting: " + ledUrl);
            requestUrl = new URL(ledUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            verifyOutputResponse();
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
        }
    }

    public void setTriLED(int port, int redIntensity, int greenIntensity, int blueIntensity) {
        try {
            redIntensity = (int) (redIntensity * 255.0/100.0);
            greenIntensity = (int) (greenIntensity * 255.0/100.0);
            blueIntensity = (int) (blueIntensity * 255.0/100.0);

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
            System.out.println("tri-lighting: " + triLedUrl);
            requestUrl = new URL(triLedUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            verifyOutputResponse();
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
        }
    }

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
            System.out.println("printing: " + printUrl);
            requestUrl = new URL(printUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            verifyOutputResponse();
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
        }
    }

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

            System.out.println("symbols: " + symbolUrl);
            requestUrl = new URL(symbolUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            verifyOutputResponse();
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
        }
    }

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

            System.out.println("gettingSensor: " + sensorUrl);
            requestUrl = new URL(sensorUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            response = Integer.parseInt(verifyResponse());
            System.out.println("sensor response: " + response);
            return response;
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            return -1;
        }
    }

    public int getLight(int port){
        int sensorResponse = getSensorValue(port);
        return (int) (sensorResponse * 255.0/100.0);
    }

    public int getSound(int port){
        int sensorResponse = getSensorValue(port);
        return (int) (sensorResponse * 255.0/100.0);
    }

    public int getDistance(int port){
        int sensorResponse = getSensorValue(port);
        return (int) (sensorResponse * 255.0/100.0);
    }

    public int getDial(int port){
        int sensorResponse = getSensorValue(port);
        return (int) (sensorResponse * 255.0/100.0);
    }

    public double getAccelerationInDirs(String dir) {
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

            System.out.println("gettingAccleration: " + acclUrl);
            requestUrl = new URL(acclUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            response = Double.parseDouble(verifyResponse());
            System.out.println("accleration response: " + response);
            return response;
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            return -1;
        }
    }

    public double getMagnetometerValInDirs(String dir) {
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

            System.out.println("gettingMagnetometerVals: " + magUrl);
            requestUrl = new URL(magUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            response = Double.parseDouble(verifyResponse());
            System.out.println("magnetometer response: " + response);
            return response;
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            return -1;
        }
    }

    public double[] getAcceleration(){
        double[] accelerations = new double[3];
        double resX = getAccelerationInDirs("X");
        double resY = getAccelerationInDirs("Y");
        double resZ = getAccelerationInDirs("Z");
        accelerations[0] = resX;
        accelerations[1] = resY;
        accelerations[2] = resZ;
        return accelerations;
    }
    public int getCompass(){
        try {
            int response;
            StringBuilder resultUrl = new StringBuilder(baseUrl);
            String compasslUrl = (resultUrl.append("in/")
                    .append("Compass/")
                    .append(deviceInstance)).toString();

            if (deviceInstance == "") {
                compasslUrl = compasslUrl.substring(0, compasslUrl.length() - 1);
            }

            System.out.println("gettingCompass: " + compasslUrl);
            requestUrl = new URL(compasslUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            response = Integer.parseInt(verifyResponse());
            System.out.println("Compass response: " + response);
            return response;
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            return -1;
        }
    }
    public double[] getMagnetometer(){
        double[] magnetometerVals = new double[3];
        double resX = getMagnetometerValInDirs("X");
        double resY = getMagnetometerValInDirs("Y");
        double resZ = getMagnetometerValInDirs("Z");
        magnetometerVals[0] = resX;
        magnetometerVals[1] = resY;
        magnetometerVals[2] = resZ;
        return magnetometerVals;
    }

    public String getButton(String button){
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

            System.out.println("gettingButton: " + buttonUrl);
            requestUrl = new URL(buttonUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            response = verifyResponse();
            System.out.println("Compass response: " + response);
            return response;
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            return "";
        }
    }
    public boolean isShaking(){
        return true;
    }

    public boolean getOrientation(String orientation) {
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

            System.out.println("gettingOrientation: " + orientationUrl);
            requestUrl = new URL(orientationUrl);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            response = Boolean.parseBoolean(verifyResponse());
            System.out.println("Orientation response: " + response);
            return response;
        } catch (IOException e) {
            Log.debug("error:" + e.getMessage());
            return false;
        }

    }
    public void disconnect() {
        if (connection != null) {
            System.out.println("disconnecting from device");
            connection.disconnect();
        }
    }
}
