import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;






public class Server {


    private static final ConcurrentHashMap<String, String> tupleSpace = new ConcurrentHashMap<>();
    private static final AtomicInteger totalClients = new AtomicInteger(0);
    private static final AtomicInteger totalOperations = new AtomicInteger(0);
    private static final AtomicInteger totalPuts = new AtomicInteger(0);
    private static final AtomicInteger totalGets = new AtomicInteger(0);
    private static final AtomicInteger totalReads = new AtomicInteger(0);
    private static final AtomicInteger totalErrors = new AtomicInteger(0);
    private static final AtomicLong totalKeyLength = new AtomicLong(0);
    private static final AtomicLong totalValueLength = new AtomicLong(0);


    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java Server <port>");
            System.exit(1);
        }

        try {
            int port = Integer.parseInt(args[0]);


        } catch (NumberFormatException e) {
            System.err.println("Invalid port number: " + args[0]);
            System.exit(2);
        }

        int port = Integer.parseInt(args[0]);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(Server::printStats, 0, 10, TimeUnit.SECONDS);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
                totalClients.incrementAndGet();
            }
        }
    }



}
