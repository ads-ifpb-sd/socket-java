package br.edu.ifpb.sd.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServer {

    public static void main(String[] args) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(5000);
        final Socket clientSocket = serverSocket.accept();
        final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        final PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        final Scanner sc = new Scanner(System.in);

        Thread sender = new Thread(new Runnable() {
            String msg;

            @Override
            public void run() {
                while (true) {
                    msg = sc.nextLine();
                    out.println(msg);
                    out.flush();
                }
            }
        });
        sender.start();

        Thread receive = new Thread(new Runnable() {
            String msg;

            @Override
            public void run() {
                try {
                    msg = in.readLine();
                    while (msg != null) {
                        System.out.println("Cliente: " + msg);
                        msg = in.readLine();
                    }

                    System.out.println("Cliente desconectado");

                    out.close();
                    clientSocket.close();
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        receive.start();
    }
}
