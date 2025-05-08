
import java.io.*;
import java.net.*;
import java.util.Arrays;
public class Client {
    public static void main(String[] args) {


        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String filePath = args[2];





        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    String request = buildRequest(line);
                    if (request == null) {
                        System.err.println("Invalid line: " + line);
                        continue;
                    }
                    out.println(request);
                    String response = in.readLine();
                    System.out.println(line + ": " + response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
