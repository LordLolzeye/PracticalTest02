package ro.pub.cs.systems.eim.practicaltest02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PracticalTest02MainActivity extends AppCompatActivity {

    private Spinner requestedInfo;

    private Button startServerButton, connectServer;
    private EditText serverPort, serverIP, connectPort, connectIP, reply;

    private EditText op1, op2;

    public ServerThread serverSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        startServerButton = findViewById(R.id.start_server);
        connectServer = findViewById(R.id.connect);

        serverPort = findViewById(R.id.server_port);
        serverIP = findViewById(R.id.server_ip);
        connectPort = findViewById(R.id.client_port);
        connectIP = findViewById(R.id.client_ip);
        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        requestedInfo = findViewById(R.id.spinner);

        reply = findViewById(R.id.reply);

        startServerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int port = Integer.valueOf(serverPort.getText().toString());

                if (serverSocket != null) {
                    serverSocket.stopServer();
                }

                serverSocket = new ServerThread(port);
                Log.d("[SS]", "Sent command for server start");
                serverSocket.startServer();
            }

        });

        connectServer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ClientThread ct = new ClientThread(reply);
                ct.execute(connectIP.getText().toString(), connectPort.getText().toString(), requestedInfo.getSelectedItem().toString() + "," + op1.getText().toString() +  "," + op2.getText().toString());
            }

        });
    }





}
