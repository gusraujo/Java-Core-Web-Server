import server.SingleThreadedServer;

public class Main {
    public static void main(String[] args) {
        SingleThreadedServer server = new SingleThreadedServer(8080);

        Thread thread = new Thread(server);

        try {
            thread.start();
            Thread.sleep(100 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
        server.stop();
    }
}
