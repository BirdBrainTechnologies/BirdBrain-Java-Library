import java.util.Arrays;

public class HummingbirdTest {
    public static void main(String[] args) {
        //boolean[] testDisplay = new boolean[25];
        Hummingbird myBit = new Hummingbird("B");
        Microbit testM = new Microbit("A");
        int patternArray[] = {0,0,0,0,0,1,1,1,1,1,0,0,1,0,0,1,1,1,1,1,0,0,0,0,0};       
        testM.setDisplay(patternArray);
        //testM.print("hel lo");
        //myBit.setPoint(1, 1, 1);
        //myBit.setPoint(2, 3, 0);
        //myBit.setLED(1, 100);
        myBit.setPositionServo(1, 180);
        /*testHummingBit.setPositionServo(2, 0);
        testHummingBit.setLED(1, 100);
        testHummingBit.setLED(2, 61);
        testHummingBit.setTriLED(2, 50, 50, 50);

        testHummingBit.print("0123");*/
        testM.print("0 29");
        testM.pause(2);
        
        myBit.setPositionServo(1, 0);
        testM.setPoint(5, 1, 1);
        testM.setPoint(2, 3, 0);
        testM.pause(2);
        
        while (!testM.isShaking()) {
        	if (myBit.getLight(1) < 20) {
        		myBit.setLED(1, 100);
        		myBit.setLED(2, 100);
        		myBit.setLED(3, 100);
        		myBit.setTriLED(1, 100, 100, 0);
        		myBit.setTriLED(2, 0, 100, 100);
        		myBit.setPositionServo(3, 0);
        		myBit.setRotationServo(2, 100);
        		myBit.playNote(60, 0.1);
        		myBit.pause(0.15);
        	} else {
        		myBit.setLED(1, 0);
        		myBit.setLED(2, 0);
        		myBit.setLED(3, 0);
        		myBit.setTriLED(1, 0, 0, 0);
        		myBit.setTriLED(2, 0, 0, 0);
        		myBit.setRotationServo(2, 0);
        		myBit.setPositionServo(3, 180);
        		myBit.playNote(88, 0.1);
        		myBit.pause(0.15);
        	}
        }

        /*for (int i = 0; i < testDisplay.length; i++) {
            testDisplay[i] = true;
        }
        testHummingBit.setDisplay(testDisplay);
        System.out.println("getLight: " + testHummingBit.getLight(1));*/
        //System.out.println("getDial: " + myBit.getDial(1));
        //System.out.println("getDistance: " + myBit.getDistance(1));
        //System.out.println("getOther: " + myBit.getVoltage(1));
        System.out.println("Acceleration: " + Arrays.toString(testM.getAcceleration()));
        System.out.println("getMagnetometer: " + Arrays.toString(testM.getMagnetometer()));
        System.out.println("isShaking: " + testM.isShaking());
        System.out.println("getOrientation: " + testM.getOrientation());
        System.out.println("getCompass: " + testM.getCompass());
        System.out.println("Button A is pressed: " + testM.getButton("A"));
        //testHummingBit.setRotationServo(1, 0);
        //testHummingBit.disconnect();
        testM.stopAll();
        //myBit.disconnect();
        testM.disconnect();
    }
}
