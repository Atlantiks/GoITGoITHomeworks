package homework12;

public class TimerDemo {
    static Timer tm = new Timer();

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    tm.increaseTimer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(() -> {
            tm.printFiveSecondsPassed();
        }).start();
    }
}
