




public class main {
    public static void main(String[] args) {
        boolean[] testDisplay = new boolean[25];
        hummingbit testHummingBit = new hummingbit();
        testHummingBit.setPositionServo(1, 80);
        testHummingBit.setLED  (2, 61);
        testHummingBit.setTriLED(2, 50, 50, 50);
        testHummingBit.print("hello");

        try {
            Thread.sleep(5000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        for (int i = 0; i < testDisplay.length; i++) {
            testDisplay[i] = true;
        }
        testHummingBit.setDisplay(testDisplay);
        testHummingBit.getLight(1);
    }
}
