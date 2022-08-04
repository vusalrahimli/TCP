package com.mycompany.client;

import java.io.BufferedWriter;
import util.FileUtility;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import lombok.SneakyThrows;

public class Client {

    public static void main(String[] args) throws Exception {
        Scanner scn = new Scanner(System.in);

        System.out.print("What do you want to send? (1 - Image, 2 - Message): ");

        int choice = scn.nextInt();
        switch (choice) {
            case 1:
                sendImage("localhost", 2001, "/path/to/your/image.jpg");
                break;
            case 2:
                sendMessage("localhost", 2001);
                break;
            default:
                System.out.println("Incorrect choice!");
                break;
        }
    }

    @SneakyThrows
    public static void sendImage(String ip, int port, String imagePath) {

        Socket socket = new Socket(ip, port);

        OutputStream outputStream = socket.getOutputStream();

        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        byte[] bytes = FileUtility.readBytes(imagePath);

        dataOutputStream.writeInt(bytes.length);
        dataOutputStream.write(bytes);

        socket.close();

        System.out.println("Sending...");
        Thread.sleep(2000);
        System.out.println("The image was successfully sent to the client.");

    }

    @SneakyThrows
    public static void sendMessage(String ip, int port) {
        Scanner scn = new Scanner(System.in);
        while (true) {
            System.out.print("Type message: ");
            String message = scn.nextLine();

            Socket s = new Socket(ip, port);

            OutputStream ous = s.getOutputStream();
            BufferedWriter b;
            DataOutputStream dout = new DataOutputStream(ous);

            dout.write(message.getBytes());

            s.close();
            if (message.equalsIgnoreCase("bye")) {
                break;
            }
        }
    }

}
