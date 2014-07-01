package pw.swordfish.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpClient {
	private BufferedReader bufferedReader;
	private PrintWriter printWriter;
	/*
	 * we want to make sure that the connection autoflushes
	 * on complete, for looks we create a variable here
	 */
	private static final boolean autoFlush = true;

	/**
	 * Constructs a very simple http client
	 * @param socket the socket to communicate over
	 * @throws IOException thrown when an IOException occurs with the socket
	 */
	public HttpClient(Socket socket) throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		printWriter = new PrintWriter(socket.getOutputStream(), autoFlush);
	}

	/**
	 * Method to send a string to an http server
	 * @param message the message to send to the server
	 * @return the http response
	 * @throws IOException thrown when an IOException occurs with the socket
	 * @throws MalformedHttpException thrown when the http response is improperly formatted
	 */
	public String send(String message) throws IOException, MalformedHttpException {
		StringBuilder response = new StringBuilder();

		/*
		 * now that we have our message, we simply send it over the socket
		 * http requires two end-of-line characters to consider the
		 * connection completed
		 */
		printWriter.println(message);
		printWriter.println();

		/*
		 * now we loop through the input and append it to the
		 * response message
		 */
		String input = "";
		while(input != null) {
			response.append(input + '\n');
			input = bufferedReader.readLine();
		}

		/*
		 * now we construct an http response
		 */
		return response.toString();
	}

	/**
	 * Method to send an httpRequest to an http server
	 * @param httpRequest the request to send
	 * @return the http response
	 * @throws IOException thrown when an IOException occurs with the socket
	 * @throws MalformedHttpException thrown when the http response is improperly formatted
	 */
	public String send(HttpRequest httpRequest) throws IOException, MalformedHttpException {
		return send(httpRequest.toString());
	}
}
