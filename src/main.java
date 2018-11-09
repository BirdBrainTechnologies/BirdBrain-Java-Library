import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        //boolean[] testDisplay = new boolean[25];
        Hummingbird myBit = new Hummingbird("A");
        Microbit testM = new Microbit("B");
        int patternArray[] = {0,0,0,0,0,1,1,1,1,1,0,0,2,0,0,1,1,1,1,1,0,0,0,0,1};       
        //testM.setDisplay(patternArray);
        testM.print("hel lo");
        myBit.setPoint(1, 1, 1);
        myBit.setPoint(2, 3, 0);
        myBit.setLED(1, 100);
        /*testHummingBit.setPositionServo(1, 90);
        testHummingBit.setPositionServo(2, 0);
        testHummingBit.setLED(1, 100);
        testHummingBit.setLED(2, 61);
        testHummingBit.setTriLED(2, 50, 50, 50);

        testHummingBit.print("0123");*/
        //testM.print("hello");
        //testM.setPoint(1, 1, 1);
        //testM.setPoint(2, 3, 0);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        
        myBit.playNote(60, 0.5);

        /*for (int i = 0; i < testDisplay.length; i++) {
            testDisplay[i] = true;
        }
        testHummingBit.setDisplay(testDisplay);
        System.out.println("getLight: " + testHummingBit.getLight(1));*/
        System.out.println("getDial: " + myBit.getDial(1));
        System.out.println("getDistance: " + myBit.getDistance(1));
        System.out.println("getOther: " + myBit.getVoltage(1));
        //System.out.println("Accleration: " + Arrays.toString(myBit.getAcceleration()));
        //System.out.println("getMagnetometer: " + Arrays.toString(testM.getMagnetometer()));
        System.out.println("isShaking: " + testM.isShaking());
        System.out.println("getOrientation: " + myBit.getOrientation());
        //System.out.println("getCompass: " + testM.getCompass());
        //System.out.println("Button A is pressed: " + myBit.getButton("A"));
        //testHummingBit.setRotationServo(1, 0);
        //testHummingBit.disconnect();
        //testM.stopAll();
        myBit.disconnect();
        testM.disconnect();
    }
}
