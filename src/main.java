import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        boolean[] testDisplay = new boolean[25];
        Hummingbird testHummingBit = new Hummingbird("B");
        Microbit testM = new Microbit("A");
        testHummingBit.playNote(60, 0.5);
        /*testHummingBit.setPositionServo(1, 90);
        testHummingBit.setPositionServo(2, 0);
        testHummingBit.setLED(1, 100);
        testHummingBit.setLED(2, 61);
        testHummingBit.setTriLED(2, 50, 50, 50);

        testHummingBit.print("0123");*/
        /*testM.print("hello");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        for (int i = 0; i < testDisplay.length; i++) {
            testDisplay[i] = true;
        }
        testHummingBit.setDisplay(testDisplay);
        System.out.println("getLight: " + testHummingBit.getLight(1));
        System.out.println("getDial: " + testHummingBit.getDial(1));
        System.out.println("getDistance: " + testHummingBit.getDistance(1));
        System.out.println("getSound: " + testHummingBit.getSound(1));*/
        System.out.println("getAccleration: " + Arrays.toString(testM.getAcceleration()));
        System.out.println("getMagnetometer: " + Arrays.toString(testM.getMagnetometer()));
        //System.out.println("isShaking: " + testM.isShaking());
        System.out.println("getOrientation: " + testM.getOrientation());
        System.out.println("getCompass: " + testM.getCompass());
        System.out.println("getButton: " + testM.getButton("A"));
        //testHummingBit.setRotationServo(1, 0);
        //testHummingBit.disconnect();
        testM.disconnect();
    }
}
