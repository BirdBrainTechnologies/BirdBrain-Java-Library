import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        boolean[] testDisplay = new boolean[25];
        hummingbit testHummingBit = new hummingbit();
        testHummingBit.setRotationServo(1, 100);
        testHummingBit.setPositionServo(2, 100);
        testHummingBit.setLED(2, 61);
        testHummingBit.setTriLED(2, 50, 50, 50);

        testHummingBit.print("hello");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        for (int i = 0; i < testDisplay.length; i++) {
            testDisplay[i] = true;
        }
        testHummingBit.setDisplay(testDisplay);
        System.out.println("getLight: " + testHummingBit.getLight(1));
        System.out.println("getLight: " + testHummingBit.getLight(-1));
        System.out.println("getDial: " + testHummingBit.getDial(1));
        System.out.println("getDistance: " + testHummingBit.getDistance(1));
        System.out.println("getSound: " + testHummingBit.getSound(1));
        System.out.println("getAccleration: " + Arrays.toString(testHummingBit.getAcceleration()));
        System.out.println("getMagnetometer: " + Arrays.toString(testHummingBit.getMagnetometer()));
        System.out.println("isShaking: " + testHummingBit.isShaking());
        System.out.println("getOrientation: " + testHummingBit.getOrientation());
        System.out.println("getCompass: " + testHummingBit.getCompass());
        System.out.println("getButton: " + testHummingBit.getButton("A"));
        testHummingBit.disconnect();
    }
}
