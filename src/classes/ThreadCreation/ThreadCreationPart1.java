package classes.ThreadCreation;

public class ThreadCreationPart1 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from a thread!" + Thread.currentThread().getName());
                System.out.println("Thread priority is: " + Thread.currentThread().getPriority());
            }
        });
        thread.setName("New Worker");
        //When you need to give more responsiveness to a thread you can increase its priority
        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("Starting thread..." + Thread.currentThread().getName());
        thread.start();
        System.out.println("Thread has started..." + Thread.currentThread().getName());
    }
}
