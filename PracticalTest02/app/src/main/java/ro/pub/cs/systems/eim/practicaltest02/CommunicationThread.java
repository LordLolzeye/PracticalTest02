package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicationThread extends Thread {

    private Socket socket;

    public CommunicationThread(Socket socket) {
        this.socket = socket;
    }

    public static BufferedReader getReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static PrintWriter getWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            BufferedReader br = CommunicationThread.getReader(socket);

            String toDo = br.readLine();

            Log.d("[SS]", "RECEIVED: " + toDo);

            // get parts
            String[] parts = toDo.split(",");
            String operation = parts[0];
            int op1 = Integer.valueOf(parts[1]);
            int op2 = Integer.valueOf(parts[2]);
            Log.d("[SS]", "Operation: " + operation + " OP1: " + op1 + " OP2: " + op2);
            // end get parts

            int result = 1;

            switch (operation) {
                case "mul":
                    result = op1 * op2;

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    break;

                case "add":
                    result = op1 + op2;

                    break;
            }


            PrintWriter printWriter = CommunicationThread.getWriter(socket);
            printWriter.println(result);

            Log.d("[SS]", "Sent reply to client, closing socket..");

            socket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}