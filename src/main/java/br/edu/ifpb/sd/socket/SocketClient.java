package br.edu.ifpb.sd.socket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {

    public static void main(String[] args) throws IOException {
        final Socket clientSocket = new Socket("127.0.0.1", 5000);
        final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        final PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        final Scanner sc = new Scanner(System.in); // object to read data from user's keybord
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
        Thread receiver = new Thread(new Runnable() {
            String msg;

            @Override
            public void run() {
                try {
                    msg = in.readLine();
                    while (msg != null) {
                        System.out.println("Server : " + msg);
                        msg = in.readLine();
                    }
                    System.out.println("Servidor desconectado");
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        receiver.start();
    }

}
