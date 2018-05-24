package ro.pub.cs.systems.eim.practicaltest02;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends AsyncTask<String, String, Void> {

    private TextView reply;

    public ClientThread(TextView reply) {
        this.reply = reply;
    }

    @Override
    protected Void doInBackground(String... params) {
        Socket socket = null;
        try {
            String serverAddress = params[0];
            int serverPort = Integer.parseInt(params[1]);

            String toDo = params[2];
            Log.d("[SS]", "TODO: " + toDo);

            socket = new Socket(serverAddress, serverPort);

            if (socket == null) {
                reply.setText("There has been an error connecting to the server!");
            }

            PrintWriter pw = CommunicationThread.getWriter(socket);
            pw.println(toDo);

            BufferedReader br = CommunicationThread.getReader(socket);
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                publishProgress(currentLine);
            }
        } catch (IOException ioException) {
            Log.e("[SS]", "An exception has occurred: " + ioException.getMessage());
            ioException.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        reply.setText("");
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        reply.append(progress[0] + "\n");
    }

    @Override
    protected void onPostExecute(Void result) {}

}