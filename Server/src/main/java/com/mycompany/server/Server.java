package com.mycompany.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import lombok.SneakyThrows;
import util.FileUtility;

public class Server {

    public static void main(String[] args) throws Exception {

        Scanner scn = new Scanner(System.in);

        System.out.print("What will you accept? (1 - Image, 2 - Message): ");

        int choice = scn.nextInt();
        switch (choice) {
            case 1:
                receiveImage(2001, "/path/to/store/image.jpg");
                break;
            case 2:
                receiveMessage(2001);
                break;
            default:
                System.out.println("Incorrect choice!");
                break;
        }
    }

    @SneakyThrows
    public static void receiveImage(int port, String imagePath) {

        ServerSocket ss = new ServerSocket(port);

        System.out.println("Server is Waiting for request...");

        Socket connection = ss.accept();

        System.out.println("Connecting...");
        Thread.sleep(2000);
        System.out.println("Connected with: " + connection.getInetAddress());

        DataInputStream dis = new DataInputStream(connection.getInputStream());

        byte[] arr = readMessage(dis);

        FileUtility.writeBytes(arr, imagePath);
        System.out.println("\nServer: The image was successfully received from the client");
    }

    @SneakyThrows
    public static byte[] readMessage(DataInputStream din) {
        int msgLen = din.readInt();

        byte[] msg = new byte[msgLen];

        din.readFully(msg);
        return msg;
    }

    @SneakyThrows
    public static void receiveMessage(int port) {
        ServerSocket ss = new ServerSocket(port);

        System.out.println("Server is Waiting for request...\n");
        while (true) {
            Socket s = ss.accept();

            InputStream is = s.getInputStream();

            InputStreamReader isr = new InputStreamReader(is);

            BufferedReader br = new BufferedReader(isr);

            String client_message = br.readLine();

            System.out.println("Client: " + client_message);

            if (client_message.equalsIgnoreCase("bye")) {
                break;
            }

        }
    }

}
