/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pw.swordfish.httrack;

import pw.swordfish.http.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Integer maxUrlDepth = 3;
    private static final HashSet<String> urlHashSet = new HashSet<String>();
    private static HttpClient httpClient = null;
    private static URL inputUrl = null;
    private static String userDirectory = System.getProperty("user.dir") + "/";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
         * if the argument length is not 1, then we know
		 * for sure that the user has specified invalid arguments
		 */
        if (args.length != 1) {
            usage();
        }

		/*
         * now we set the url of the site to download
		 */
        inputUrl = new URL(args[0]);

        userDirectory += inputUrl.getHostname();

		/*
         * now we try to create the directory 'hostname'
		 */
        if (!(new File(inputUrl.getHostname())).mkdir()) {
            if (!new File(userDirectory).exists()) {
                System.err.println("Could not create directory: " + userDirectory);
                usage();
            }
        }

		/*
		 * we setup a socket so we can communicate with a server, and
		 * an http client that will handle the connections
		 */

        try {
            httpClient = new HttpClient(new Socket(inputUrl.getHostname(), inputUrl.getPortNumber()));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            usage();
        }

		/*
		 * now we get all the links, the reason we do this
		 * here is so we can avoid link duplication,
		 * essentially we add everything to a set, so it
		 * only gets processed once later. This is a trade-off
		 * because we actually add a few more requests, but in
		 * the long-run it doesn't really matter
		 */
        try {
            urlHashSet.add(inputUrl.getFile());
            handleLink(new URL(inputUrl.getFile()));
            findLinks(new URL(inputUrl.getFile()), 0);
        } catch (MalformedURLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }

    /**
     * Method to handle the links and download them
     *
     * @param url the url to handle
     */
    private static void handleLink(URL url) {

        String fullPath = userDirectory;

		/*
		 * now we want to build the necessary directories
		 */
        for (String path : url) {
            fullPath += "/" + path;
            new File(fullPath).mkdir();
        }

        HtmlBody htmlBody;
        BufferedWriter bufferedWriter;

		/*
		 * now we create a buffered writer to create the file and
		 * get the html body that we want and write it to the correct
		 * file
		 */
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File(fullPath + "/" + url.getFile())));
            httpClient = new HttpClient(new Socket(inputUrl.getHostname(), inputUrl.getPortNumber()));
            htmlBody = new HtmlBody(httpClient.send(new HttpRequest("GET " + inputUrl.getPath() + "/" + url.getFullPath() + " HTTP/1.0")));

            bufferedWriter.write(htmlBody.getHtml());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (MalformedHttpException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Method to recurse over the process link
     *
     * @param url     the url to recurse over
     * @param counter the number of links
     */
    private static void findLinks(URL url, Integer counter) {
		/*
		 * now we construct the first html body
		 */
        HtmlBody htmlBody;

		/*
		 * now we actually create the html body
		 */
        try {
			/*
			 * we have to create a new http client for every request because
			 * the sockets are use once-only
			 */
            httpClient = new HttpClient(new Socket(inputUrl.getHostname(), inputUrl.getPortNumber()));
            htmlBody = new HtmlBody(httpClient.send(new HttpRequest("GET " + inputUrl.getPath() + "/" + url.getFullPath() + " HTTP/1.0")));

        } catch (MalformedHttpException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

		/*
		 * if our counter is the max url depth then
		 * we are done
		 */
        if (counter.equals(maxUrlDepth)) {
            return;
        }

		/*
		 * while we still have more links we want to download them
		 */
        for (HtmlLink htmlLink : htmlBody) {
            URL currentUrl = htmlLink.getURL();

			/*
			 * we only download links if they are html and don't have a
			 * different hostname
			 */
            if (currentUrl.isHtml() && currentUrl.getHostname().equals("") && !urlHashSet.contains(currentUrl.toString())) {
                urlHashSet.add(currentUrl.toString());
                handleLink(currentUrl);
                findLinks(currentUrl, counter + 1);
            }
        }

    }

    /**
     * Print the usage of the program and exit
     */
    private static void usage() {
        System.err.println("Usage: my_httrack <url>");
        System.err.println("	<url>: The url to check");
        System.exit(0);
    }
}
