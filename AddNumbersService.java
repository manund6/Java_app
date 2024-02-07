import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class AddNumbersService {

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        HttpServer server = HttpServer.create(new java.net.InetSocketAddress(port), 0);
        server.createContext("/add", new AddHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server running on port " + port);
    }

    static class AddHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String[] parts = exchange.getRequestURI().getPath().split("/");
            if (parts.length == 4 && parts[1].equals("add")) {
                try {
                    int num1 = Integer.parseInt(parts[2]);
                    int num2 = Integer.parseInt(parts[3]);
                    int result = num1 + num2;
                    String response = Integer.toString(result);
                    exchange.sendResponseHeaders(200, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } catch (NumberFormatException e) {
                    String response = "Invalid numbers provided";
                    exchange.sendResponseHeaders(400, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            } else {
                String response = "Invalid URL";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }
}
