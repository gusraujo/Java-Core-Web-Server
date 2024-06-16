package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SingleThreadedServer implements Runnable {

    // Port on which the server will listen for incoming connections
    protected int serverPort = 8080;
    // Server socket to accept client connections
    protected ServerSocket serverSocket = null;
    // Flag to control the server running state
    protected boolean isStopped = false;
    // Thread in which the server is running
    protected Thread runningThread = null;

    // Constructor to initialize the server with a specific port
    public SingleThreadedServer(int port) {
        this.serverPort = port;
    }

    @Override
    public void run() {
        // Set the running thread to the current thread
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        // Open the server socket
        openServerSocket();

        // Main loop to accept client connections
        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                // Accept incoming client connection
                clientSocket = this.serverSocket.accept();
                // Process the client request
                processClientRequest(clientSocket);
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    // Method to process the client request
    private void processClientRequest(Socket clientSocket) throws IOException {
        // Get input and output streams from the client socket
        InputStream input = clientSocket.getInputStream();
        OutputStream output = clientSocket.getOutputStream();
        long time = System.currentTimeMillis();

        // Prepare the response document
        byte[] responseDocument = ("<html><body>" +
                "Singlethreaded Server: " +
                time +
                input.toString() +
                output.toString() +
                "</body></html>").getBytes(UTF_8);

        // Prepare the HTTP response header
        byte[] responseHeader = ("HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html; charset=UTF_8\r\n" +
                "Content-Length: " + responseDocument.length +
                "\r\n\r\n").getBytes(UTF_8);

        // Send the HTTP response to the client
        output.write(responseHeader);
        output.write(responseDocument);
        output.close();
        input.close();
        System.out.println("Request processed: " + time);
    }

    // Synchronized method to check if the server is stopped
    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    // Synchronized method to stop the server
    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    // Method to open the server socket
    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + this.serverPort, e);
        }
    }
}
