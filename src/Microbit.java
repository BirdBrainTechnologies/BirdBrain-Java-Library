
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class controls a micro:bit via bluetooth. It includes methods to print on the micro:bit LED array or 
 * set those LEDs individually. It also contains methods to read the values of the micro:bit accelerometer and magnetometer.
 * 
 * Mike Yuan and Bambi Breewer, BirdBrain Technologies LLC
 * November 2018
 */
public class Microbit {
	// Variables used to make http request to control the micro:bit (and Hummingbird Bit)
    protected HttpURLConnection connection = null;
    protected static String baseUrl = "http://127.0.0.1:30061/hummingbird/";
    protected URL requestUrl;
    
    protected String deviceInstance;		// A, B, or C
    
    // String variables used to return the orientation of the micro:bit
    private static final String SCREEN_UP = "Screen%20Up";
    private static final String SCREEN_DOWN = "Screen%20Down";
    private static final String TILT_LEFT = "Tilt%20Left";
    private static final String TILT_RIGHT = "Tilt%20Right";
    private static final String LOGO_UP = "Logo%20Up";
    private static final String LOGO_DOWN = "Logo%20Down";
    private static final String SHAKE = "Shake";

    private String outputError = "Error: Could not set output on the device ";
	private String inputError = "Error: Could not read sensor on the device ";
	
	protected boolean[] displayStatus = new boolean[25];
    
    /**
     * verifyOutputResponse checks whether the HTTP request response is valid or not.
     * If the response code indicates that the response is invalid, the connector will be disconnected.
     */
    protected void verifyOutputResponse() {
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
                System.out.println(outputError);
                disconnect();
            }
        } catch (IOException e) {
        		System.out.println(outputError + e.getMessage());
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
    protected String verifyResponse() {
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
                System.out.println(inputError);
                disconnect();
                return "Not Connected";
            }
        } catch (IOException e) {
        		System.out.println(inputError + e.getMessage());
            disconnect();
            return "Not Connected";
        }
    }
    
    /* This function tests a connection by attempting to read whether or not the micro:bit is shaking. 
     * Return true if the connection is good and false otherwise. 
     */
    protected boolean isConnectionValid() {
    	try {
	    	 StringBuilder newURL = new StringBuilder(baseUrl);
	         String testURL = (newURL.append("in/")
	                 .append("orientation/")
	                 .append(SHAKE + "/")
	                 .append(deviceInstance)).toString();
	    	
	        requestUrl = new URL(testURL);
	        connection = (HttpURLConnection) requestUrl.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setDoOutput(true);
	
	        String stringResponse = verifyResponse();
	        if (stringResponse.equals("Not Connected")) {
	        	System.out.println("Error: Device " + deviceInstance + " is not connected");
	        	return false;
	        } else {
	        	return true;
	        }
    	} catch (IOException e) {
            System.out.println("Error: Device " + deviceInstance + " is not connected");
            return false;
        }
    }
    private boolean isMicrobit() {
    	try { 
	    	StringBuilder newURL = new StringBuilder(baseUrl);
	        String testURL = (newURL.append("in/")
	                .append("sensor/4/")
	                .append(deviceInstance)).toString();
	   	
	       requestUrl = new URL(testURL);
	       connection = (HttpURLConnection) requestUrl.openConnection();
	       connection.setRequestMethod("GET");
	       connection.setDoOutput(true);
	
	       String stringResponse = verifyResponse();
	       if (stringResponse.equals("255")) return true;
	       else {
	    	   System.out.println("Error: Device "+deviceInstance+" is not a micro:bit");
	    	   return false;
	       }
		} catch (IOException e) {
	       System.out.println("Error: Device " + deviceInstance + " is not connected");
	       return false;
	   }
    	
    }
    /* This function checks whether an input parameter is within the given bounds. If not, it prints
	   a warning and returns a value of the input parameter that is within the required range.
	   Otherwise, it just returns the initial value. */
    protected int clampParameterToBounds(int parameter, int inputMin, int inputMax) {
    	if ((parameter < inputMin) || (parameter > inputMax)) {
    		System.out.println("Warning: Please choose a parameter between " + inputMin + " and " + inputMax);
    		return Math.max(inputMin, Math.min(inputMax,  parameter));
    	} else
    		return parameter;
    }
    
    /* This function checks whether an input parameter is within the given bounds. If not, it prints
	   a warning and returns a value of the input parameter that is within the required range.
	   Otherwise, it just returns the initial value. */
	protected double clampParameterToBounds(double parameter, double inputMin, double inputMax) {
	 	if ((parameter < inputMin) || (parameter > inputMax)) {
	 		System.out.println("Warning: Please choose a parameter between " + inputMin + " and " + inputMax);
	 		return Math.max(inputMin, Math.min(inputMax,  parameter));
	 	} else
	 		return parameter;
	 }
    
	/* This function sends http requests that set outputs (lights, motors, buzzer, 
	 * etc.) on the micro:bit and Hummingbird. */
    protected void httpRequestOut(String URLRequest) {
    	try {
            requestUrl = new URL(URLRequest);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            verifyOutputResponse();
        } catch (IOException e) {
        		System.out.println(outputError + e.getMessage());
        }
    }
    
    /* This function sends http requests that return a double response from a sensor. */
    protected double httpRequestInDouble(String URLRequest) {
    	try {
            double response;
            String stringResponse;
            requestUrl = new URL(URLRequest);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            stringResponse = verifyResponse();
            if (stringResponse.equals("Not Connected")) {
            	System.out.println("Error: The device is not connected");
            	return -1;
            } else {
            	response = Double.parseDouble(stringResponse);
            	return response;
            }
        } catch (IOException e) {
            System.out.println(inputError + e.getMessage());
            return -1;
        }
    }
    
    /* This function sends http requests that return a boolean response from a sensor. */
    protected boolean httpRequestInBoolean(String URLRequest) {
    	try {
            String response;
            requestUrl = new URL(URLRequest);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            response = verifyResponse();
            if (response.equals("Not Connected")) {
            	System.out.println("Error: The device is not connected");
            	return false;
            } else return (response.equals("true"));
        } catch (IOException e) {
            System.out.println(inputError + e.getMessage());
            return false;
        }
    }

    /**
     * default constructor for the library. Construct the baseUrl and set the default device to be A
     */
    public Microbit() {
        deviceInstance = "A";
        if (!isConnectionValid()) System.exit(0);
        if (!isMicrobit()) System.exit(0);
    }

    /**
     * constructor for the library. Construct the baseUrl and set the default device to be input.
     *
     * @param device the input device that will be specified by the user.
     */
    public Microbit(String device) {
    	if (!((device == "A")||(device == "B")||(device == "C"))) {
        	System.out.println("Error: Device must be A, B, or C.");
        	System.exit(0);
        } else {
        	deviceInstance = device;
        	if (!isConnectionValid()) System.exit(0);
        	if (!isMicrobit()) System.exit(0);       
        }
    }

    
    /**
     * print() lets the LED Array display a given message.
     *
     * @param msg The message that will be displayed on the LED Array.
     */
    public void print(String message) {
        if (message.length() > 15) {
        		System.out.println("Warning: print() requires a String with 15 or fewer characters."); 
        }
        // Warn the user if there are any special characters. Note that we don't use isCharacterOrDigit() because we can only display English characters
        char letter;
        for (int i = 0; i < message.length(); i++) {
        	letter = message.charAt(i);
        	if (!(((letter >= 'a') && (letter <= 'z')) || ((letter >= 'A') && (letter <= 'Z')) || ((letter >= '0') && (letter <= '9')) || (letter == ' '))) {
        		System.out.println("Warning: Many special characters cannot be printed on the LED display");
        	}
        }
    	for (int i = 0; i < displayStatus.length; i++) displayStatus[i] = false;
 			
    	// Get rid of spaces
    	message = message.replace(" ", "%20");
    	
    	// Build http request
        StringBuilder resultUrl = new StringBuilder(baseUrl);
        String printUrl = (resultUrl.append("out/")
                    .append("print/")
                    .append(message + "/")
                    .append(deviceInstance)).toString();
         httpRequestOut(printUrl);
            
    }

    /**
     * setDisplay lets the LED Array display a pattern based on an array of 1s and 0s.
     *
     * @param ledValues The list of integers that the function takes in to set the LED Array.
     *                1 means on and 0 means off.
     */
    public void setDisplay(int[] ledValues) {
    	StringBuilder resultUrl = new StringBuilder(baseUrl);
        int ledLen = ledValues.length;
        
        for (int i = 0; i < ledLen; i++){
        	ledValues[i] = clampParameterToBounds(ledValues[i],0,1);
        }
        
        if (ledLen != 25) {
        	System.out.println("Error: setDisplay() requires a int array of length 25");
        	return;
        }         
        
        /* For the http request, we need to convert the 0s and 1s to boolean values. We can do this while
         * also ensuring that the user only used 0 and 1.
         */
    	for (int i = 0; i < ledLen; i++){
    		if (ledValues[i] == 1)
                displayStatus[i] = true;
            else 
                displayStatus[i] = false;
                
        }      
    		
        resultUrl = resultUrl.append("out/")
                    .append("symbol/").append(deviceInstance.toString()+"/");
          
        for (int i = 0; i < ledLen; i++) {
            resultUrl = resultUrl.append(String.valueOf(displayStatus[i]) + "/");
        }
           
        String symbolUrl = resultUrl.append(deviceInstance).toString();
        symbolUrl = symbolUrl.substring(0, symbolUrl.length() - 1);
        
        httpRequestOut(symbolUrl);
    }
    
    /* This function turns on or off a single LED on the micro:bit LED array. 
     * 
     * @param x The column of the LED (1-5)
     * @param y The row of the LED (1-5)
     * @param value The value of the LED (0 for off, 1 for on)
     * */
    public void setPoint(int row, int column, int value) {
    	
    	StringBuilder resultUrl = new StringBuilder(baseUrl);
    	
    	row = clampParameterToBounds(row, 1, 5);
    	column = clampParameterToBounds(column, 1, 5);
    	value = clampParameterToBounds(value, 0, 1);
    		
    	// Find the position of this led in displayStatus
		int position = (row - 1)*5 + (column-1);
		/* For the http request, we need to convert the 0 or 1 to a boolean. We can do this warning if the user didn't use 0 or 1 for the value
         */
		if (value == 1)
			displayStatus[position] = true;
		else 
			displayStatus[position] = false;
		
        resultUrl = resultUrl.append("out/")
                .append("symbol/").append(deviceInstance.toString()+"/");
      
        for (int i = 0; i < displayStatus.length; i++) {
            resultUrl = resultUrl.append(String.valueOf(displayStatus[i]) + "/");
        }
       
        String symbolUrl = resultUrl.append(deviceInstance).toString();
        symbolUrl = symbolUrl.substring(0, symbolUrl.length() - 1);

        httpRequestOut(symbolUrl);
    }
   
    /**
     * getAccelerationInDirs returns acceleration value in a specified direction.
     *
     * @param dir The direction of which the acceleration will be returned.
     */
    private double getAccelerationInDirs(String dir) {
        
        StringBuilder resultUrl = new StringBuilder(baseUrl);
        String acclUrl = (resultUrl.append("in/")
                .append("Accelerometer/")
                .append(dir + "/")
                .append(deviceInstance)).toString();

        return httpRequestInDouble(acclUrl);     
    }

    /**
     * getMagnetometerValInDirs returns magnetometer value in a specified direction.
     *
     * @param dir The direction of which the magnetometer value will be returned.
     */
    private double getMagnetometerValInDirs(String dir) {
        StringBuilder resultUrl = new StringBuilder(baseUrl);
        String magUrl = (resultUrl.append("in/")
                .append("Magnetometer/")
                .append(dir + "/")
                .append(deviceInstance)).toString();

        return httpRequestInDouble(magUrl);
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
     * getCompass returns the direction in degrees from north.
     *
     * @return the direction in degrees. (Range: 0-360)
     */
    public int getCompass() {
        
        StringBuilder resultUrl = new StringBuilder(baseUrl);
        String compassUrl = (resultUrl.append("in/")
                .append("Compass/")
                .append(deviceInstance)).toString();

        return (int) httpRequestInDouble(compassUrl);
       
    }

    /**
     * getButton() takes in a button and checks whether it is pressed.
     * The function shows a warning dialog if the inputs are not in the specified range.
     *
     * @param button the button that will be checked whether is it pressed or not. (Range: "A", "B")
     * @return true if the button is pressed and false otherwise.
     */
    public boolean getButton(String button) {
        if (!(button.equals("A") || button.equals("B"))) {
            System.out.println("Error: Please choose button A or B");
            return false;
        }
        
        StringBuilder resultUrl = new StringBuilder(baseUrl);
        String buttonUrl = (resultUrl.append("in/")
                .append("button/")
                .append(button + "/")
                .append(deviceInstance)).toString();
        
        return httpRequestInBoolean(buttonUrl);
          
    }


    /**
     * getOrientationBoolean checks whether the device currently being held to a specific orientation or shaken.
     *
     * @param orientation The orientation that will be checked.
     * @return "true" if the device is held to the orientation and false otherwise.
     */
    private boolean getOrientationBoolean(String orientation) {
    	
        StringBuilder resultUrl = new StringBuilder(baseUrl);
        String orientationUrl = (resultUrl.append("in/")
                .append("orientation/")
                .append(orientation + "/")
                .append(deviceInstance)).toString();
      
        return httpRequestInBoolean(orientationUrl);  
        
    }

    /* isShaking() tells you whether the micro:bit is being shaken. 
     * 
     * @return a boolean value telling you the shake state
     * */
    public boolean isShaking() {
    	return getOrientationBoolean(SHAKE);
    }
    /**
     * getOrientation() provides information about the device's current orientation.
     *
     * @return the orientation of the device. (Range: Screen up, Screen down, Tilt left, Tilt right, Logo up, Logo down)
     */
    public String getOrientation() {
        boolean screenUp = getOrientationBoolean(SCREEN_UP);
        boolean screenDown = getOrientationBoolean(SCREEN_DOWN);
        boolean tiltLeft = getOrientationBoolean(TILT_LEFT);
        boolean tiltRight = getOrientationBoolean(TILT_RIGHT);
        boolean logoUp = getOrientationBoolean(LOGO_UP);
        boolean logoDown = getOrientationBoolean(LOGO_DOWN);
        
        if (screenUp) return "Screen up";
        else if (screenDown) return "Screen down";
        else if (tiltLeft) return "Tilt left";
        else if (tiltRight) return "Tilt right";
        else if (logoUp) return "Logo up";
        else if (logoDown) return "Logo down";
        return "In between";
    }

    /* Pauses the program for a time in seconds. */
    public void pause(double numSeconds) {
    	
    	double milliSeconds = 1000*numSeconds;
    	try {
            Thread.sleep(Math.round(milliSeconds));
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * disconnect closes the http connection to save memory
     */
    public void disconnect() {
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
    }
    /* stopAll() turns off all the outputs. */
    public void stopAll() {
    
		// Build http request to turn off all the outputs
        StringBuilder resultUrl = new StringBuilder(baseUrl);
        String stopUrl = (resultUrl.append("out/")
                .append("stopall/")
                .append(deviceInstance)).toString();
        
        httpRequestOut(stopUrl);
    }
}
