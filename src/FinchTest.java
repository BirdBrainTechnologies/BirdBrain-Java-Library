public class FinchTest {
    public static void main(String[] args) {
        Finch myFinch = new Finch("A");

        for (int i = 0; i < 10; i++) {
            myFinch.setBeak(10, 100, 100);
            myFinch.pause(1);
            myFinch.setBeak(60, 20, 100);
            myFinch.pause(1);
            myFinch.setBeak(100, 55, 20);
            myFinch.pause(1);
        }

        myFinch.stopAll();
        myFinch.disconnect();
    }
}
