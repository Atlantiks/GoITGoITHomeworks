package homework12.fizzBuzz;

import java.util.Objects;
import java.util.Scanner;

public class Counter {
    volatile int number = 1;
    Integer userInput = null;

    public static void main(String[] args) {
        Counter counter = new Counter();
        Scanner sc = new Scanner(System.in);

        while (Objects.isNull(counter.userInput)) {
            System.out.println("Введите число: ");
            try {
                counter.userInput = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
        }

        new Thread(() -> {
            Thread.currentThread().setName("Поток A");
            try {
                while (counter.number <= counter.userInput) {
                    counter.fizz();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            Thread.currentThread().setName("Поток B");
            try {
                while (counter.number <= counter.userInput) {
                    counter.buzz();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            Thread.currentThread().setName("Поток C");
            try {
                while (counter.number <= counter.userInput) {
                    counter.fizzbuzz();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            Thread.currentThread().setName("Поток D");
            try {
                while (counter.number <= counter.userInput) {
                    counter.number();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    synchronized public void number() throws InterruptedException {
        if (number % 3 != 0 && number % 5 != 0) {
            System.out.print(number + endLine());
            increaseNumber();
            notifyAll();
        } else {
            wait();
        }
    }

    synchronized public void increaseNumber() {
        number++;
    }

    synchronized public void fizzbuzz() throws InterruptedException {
        if (number % 3 == 0 && number % 5 == 0) {
            System.out.print("fizzbuzz" + endLine());
            increaseNumber();
            notifyAll();
        } else {
            wait();
        }
    }

    synchronized public void fizz() throws InterruptedException {
        if (number % 3 == 0 && number % 5 != 0) {
            System.out.print("fizz"  + endLine());
            increaseNumber();
            notifyAll();
        } else {
            wait();
        }
    }

    synchronized public void buzz() throws InterruptedException {
        if (number % 3 != 0 && number % 5 == 0) {
            System.out.print("buzz" + endLine());
            increaseNumber();
            notifyAll();
        } else {
            wait();
        }
    }

    private String endLine() {
        return number == userInput ? "" : ",";
    }
}
