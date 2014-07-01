/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pw.swordfish.myserver;

import pw.swordfish.http.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author brandon
 */
public class App implements Runnable {
    private static final String userDirectory = System.getProperty("user.dir");
    private static int portNumber = 8080;
    private Socket socket = null;

    /**
     * Method to create a url handler
     *
     * @param socket the socket to handle
     */
    private App(Socket socket) {
        this.socket = socket;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
         * if the argument length is not 1, then we know
		 * for sure that the user has specified invalid arguments
		 */
        if (args.length > 1) {
            usage();
        } else if (args.length == 1) {
            try {
                portNumber = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                usage();
            }
        }

		/*
         * first we setup a server socket
		 */
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }

		/*
         * now we indefinitely loop, handling the
		 * user requests
		 */
        while (true) {
			/*
			 * now we actually handle the requests
			 */
            try {
                Socket socket = serverSocket.accept();
                new Thread(new App(socket)).start();
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Print the usage and exit
     */
    private static void usage() {
        System.err.println("Usage: my_server <port>");
        System.err.println("	<port>: The port number to run on (default 8080)");
        System.exit(0);
    }

    /**
     * Method to handle a url
     */
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			/*
			 * we only read in one line because all of our requests are one line
			 */
            String input = bufferedReader.readLine();

			/*
			 * we don't want any more user input
			 */
            socket.shutdownInput();

            URL url;

			/*
			 * we now parse the url specified by the user
			 */
            url = (new HttpRequest(input)).getURL();


			/*
			 * now we try to get the file of the url
			 */
            getFile(url);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedHttpException ex) {
            badRequest();
            try {
                socket.close();
            } catch (IOException e1) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Get the file
     *
     * @param url the url to handle
     */
    private void getFile(URL url) {
        BufferedWriter bufferedWriter;

        File file = new File(userDirectory + url.toString());

		/*
		 * now we see if the file exists, if it does then we send the file,
		 * otherwise we tell the client that the html is not available
		 */
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            if (!file.exists()) {
                bufferedWriter.write(new HttpResponse(HttpStatusCodes.HTTPSTATUSNOTFOUND, "Brandon 1.0.0", "text/html").toString() + "\n\n" + notFound(url, socket, portNumber));
            } else {
                String html = readFileAsString(file);
                bufferedWriter.write(new HttpResponse(HttpStatusCodes.HTTPSTATUSOK, "Brandon 1.0.0", "text/html").toString() + "\n\n" + html);
            }

            bufferedWriter.flush();
            bufferedWriter.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to generate not found requests
     *
     * @param url        the url of the html
     * @param socket     the socket used, used to get the inet address
     * @param portNumber the port number of the server
     * @return the not found request
     * @throws MalformedURLException
     */
    private String notFound(URL url, Socket socket, Integer portNumber) throws MalformedURLException {
        return "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n" +
                "<html><head>\n" +
                "<title>" + HttpStatusCodes.HTTPSTATUSNOTFOUND + "</title>\n" +
                "</head><body>\n" +
                "<h1>Not Found</h1>\n" +
                "<p>The requested URL " + url.getFile() + " was not found on this server.</p>\n" +
                "<hr>\n" +
                "<address>Brandon (1.0.0) Server at " + socket.getInetAddress() + " Port " + portNumber + "</address>\n" +
                "</body></html>\n";
    }

    /**
     * This method just reads in a file to a string
     *
     * @param file the file to read in
     * @return the file as a string
     * @throws java.io.IOException
     */
    private String readFileAsString(File file) throws java.io.IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder fileAsString = new StringBuilder();

        while (reader.ready()) {
            fileAsString.append(reader.readLine());
        }

        return fileAsString.toString();
    }

    /**
     * Method to handle bad requests
     */
    private void badRequest() {
        BufferedWriter bufferedWriter;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write((new HttpResponse(HttpStatusCodes.HTTPSTATUSBADREQUEST, "Brandon 1.0.0", "text/html")).toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
