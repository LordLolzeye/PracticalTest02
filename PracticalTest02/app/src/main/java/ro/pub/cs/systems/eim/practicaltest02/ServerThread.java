package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    private boolean isRunning;

    private ServerSocket serverSocket;

    private int port;

    public ServerThread(int port) {
        this.port = port;
    }

    public void startServer() {
        isRunning = true;
        start();
    }

    public void stopServer() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);

            Log.d("[SS]", "Awaiting Clients");

            while (isRunning) {
                Socket socket = serverSocket.accept();
                if (socket != null) {
                    Log.d("[SS]", "Client found! Creating new Communication Thread");
                    CommunicationThread communicationThread = new CommunicationThread(socket);
                    communicationThread.start();
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}