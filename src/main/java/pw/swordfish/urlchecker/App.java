package pw.swordfish.urlchecker;

import pw.swordfish.http.*;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    /*
     * if the user has not specified just a URL then
	 * we don't even want to try to handle the request
	 */
        if (args.length != 1)
            usage();

	/*
     * We setup a URL object to parse the hostname and
	 */
        URL url = new URL(args[0]);

	/*
     * we setup a socket so we can communicate with a server, and
	 * an http client that will handle the connections
	 */
        Socket socket;
        HttpClient httpClient = null;

	/*
     * now we construct an http client so we can talk to the server
	 */
        try {
            socket = new Socket(url.getHostname(), url.getPortNumber());
            httpClient = new HttpClient(socket);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            usage();
        }

	/*
     * first we have to get the original url
	 */
        String response = null;
        try {
            assert httpClient != null;
            response = httpClient.send(new HttpRequest(new URL(url.getFullPath())));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            usage();
        }

	/*
	 * now we construct an htmlBody object so we can
	 * manipulate the links
	 */
        HtmlBody htmlBody = new HtmlBody(response);

	/*
	 * now we specify a URL prefix to make printing easier
	 */
        String urlPrefix = "http://" + url.getHostname() + url.getPath() + "/";

	/*
	 * while we still have more links we want to print the
	 * status of the links
	 */
        for (HtmlLink htmlLink : htmlBody) {
            HttpResponse httpResponse = null;
            URL currentURL = htmlLink.getURL();

            if (currentURL.getHostname().equals("")) {
			/*
			 * we create an http client so we can request the next url
			 * and we formulate the http client response after sending a
			 * request for the httpClient
			 */
                try {
                    httpClient = new HttpClient(new Socket(url.getHostname(), url.getPortNumber()));
                    httpResponse = new HttpResponse(httpClient.send(new HttpRequest(new URL(url.getPath() + "/" + currentURL.getFullPath()))));
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedHttpException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }

                assert httpResponse != null;
                if (httpResponse.getStatusCode().equals(HttpStatusCodes.HTTPSTATUSOK))
                    System.out.println(urlPrefix + currentURL.getFullPath() + " ACTIVE " + httpResponse.getLastModified());
                else if (httpResponse.getStatusCode().equals(HttpStatusCodes.HTTPSTATUSNOTFOUND))
                    System.out.println(urlPrefix + currentURL.getFullPath() + " INACTIVE ");
            }
        }
    }

    /**
     * Print the usage of the program and exit
     */
    private static void usage() {
        System.err.println("Usage: url_checker <url>");
        System.err.println("	<url>: The url to check");
        System.exit(0);
    }
}
