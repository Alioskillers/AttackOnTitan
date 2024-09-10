package game.server.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class Main {
	
	public static void main(String[] args)throws IOException {
		
		String port = System.getenv("PORT");
        if (port == null) {
            port = "8080"; // Default to port 8080 if no port is provided (local testing)
        }
        int portNumber = Integer.parseInt(port);

        // Create server listening on the port
        HttpServer server = HttpServer.create(new InetSocketAddress(portNumber), 0);
        server.start();
        System.out.println("Server is running on port " + portNumber);
		
	}

}
