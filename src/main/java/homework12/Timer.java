package homework12;

public class Timer {
    private int secondsPassed = 0;

    synchronized public void increaseTimer() {
        secondsPassed++;
        printTimePassed();
        notify();
    }

    private void printTimePassed() {
        System.out.println(Thread.currentThread().getName() +
                " Прошло " + secondsPassed + " секунд от запуска программы");
    }

    synchronized public void printFiveSecondsPassed() {
        while (true) {
            if (secondsPassed % 5 == 0 && secondsPassed != 0) System.out.println("\033[0;93m" +
                    Thread.currentThread().getName() + " Прошло 5 секунд" + "\u001B[0m");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
