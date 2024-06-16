package classes.exercises;

import java.math.BigInteger;
/*
In this coding exercise you will use all the knowledge from the previous lectures.
Before taking the exercise make sure you review the following topics in particular:
1. Thread Creation - how to create and start a thread using the Thread class and the start() method.

2. Thread Join - how to wait for another thread using the Thread.join() method.



In this exercise we will efficiently calculate the following result = base1 ^ power1 + base2 ^ power2

Where a^b means: a raised to the power of b.

For example 10^2 = 100

We know that raising a number to a power is a complex computation, so we we like to execute:

result1 = x1 ^ y1

result2 = x2 ^ y2

In parallel.

and combine the result in the end : result = result1 + result2

This way we can speed up the entire calculation.

Note :

base1 >= 0, base2 >= 0, power1 >= 0, power2 >= 0

*/
public class ComplexCalculation {
    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) throws InterruptedException {
        PowerCalculatingThread calculator1 = new PowerCalculatingThread(base1, power1);
        PowerCalculatingThread calculator2 = new PowerCalculatingThread(base2, power2);

        calculator1.start();
        calculator2.start();

        try {
            calculator1.join();
            calculator2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Calculate the final result
        return calculator1.getResult().add(calculator2.getResult());
    }

    private static class PowerCalculatingThread extends Thread {
        private final BigInteger base;
        private final BigInteger power;
        private BigInteger result = BigInteger.ONE;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) < 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
        }

        public BigInteger getResult() {
            return result;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ComplexCalculation calculation = new ComplexCalculation();
        BigInteger base1 = new BigInteger("2");
        BigInteger power1 = new BigInteger("10");
        BigInteger base2 = new BigInteger("3");
        BigInteger power2 = new BigInteger("5");

        BigInteger result = calculation.calculateResult(base1, power1, base2, power2);
        System.out.println("Result: " + result); // Expected output: 2^10 + 3^5
    }
}
