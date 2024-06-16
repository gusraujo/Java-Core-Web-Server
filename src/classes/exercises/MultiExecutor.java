package classes.exercises;

import java.util.ArrayList;
import java.util.List;

public class MultiExecutor {

    // Add any necessary member variables here
    Thread thread1 = new Thread();
    Thread thread2 = new Thread();

    List<Runnable> executes = new ArrayList<>();
    /*
     * @param tasks to executed concurrently
     */
    public MultiExecutor(List<Runnable> tasks) {
        // Complete your code here
        tasks.add(thread1);
        tasks.add(thread2);
    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {
        // complete your code here
        for (Runnable task : executes){
            task.run();
        }
    }
}

/* RESPOSTA
import java.util.ArrayList;
import java.util.List;

public class MultiExecutor {

    private final List<Runnable> tasks;

public MultiExecutor(List<Runnable> tasks) {
    this.tasks = tasks;
}


    public void executeAll() {
        List<Thread> threads = new ArrayList<>(tasks.size());

        for (Runnable task : tasks) {
            Thread thread = new Thread(task);
            threads.add(thread);
        }

        for(Thread thread : threads) {
            thread.start();
        }
    }
}*/

