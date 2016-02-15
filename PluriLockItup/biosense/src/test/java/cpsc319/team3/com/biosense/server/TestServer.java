import java.net.*;
import java.io.*;

public class TestServer {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java TestServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket =
                        new ServerSocket(Integer.parseInt(args[0]));
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("Client connected.");
            String inputLine;
            float conflvl;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);
                conflvl = (float)Math.random() * 100;
                out.println(conflvl);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}