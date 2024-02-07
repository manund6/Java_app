import static spark.Spark.*;

public class SimpleMicroservice {
    public static void main(String[] args) {
        // Set the port for the microservice
        port(8080);

        // Define a route
        get("/hello", (req, res) -> {
            // Return a simple message
            return "Hello, World!";
        });

        // Define another route
        get("/greet/:name", (req, res) -> {
            // Retrieve the name parameter from the request URL
            String name = req.params(":name");
            // Return a greeting message
            return "Hello, " + name + "!";
        });
    }
}
