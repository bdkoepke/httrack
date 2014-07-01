package pw.swordfish.http;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URL implements Iterator<String>, Iterable<String> {

    private static final Pattern hostnamePattern = Pattern.compile("http://([^/:]+)");
    private final ListIterator<String> pathListIterator;
    private int portNumber = 80;
    private String hostname = "";
    private String fullPath = "";
    private String fileName = "";

    /**
     * Construct a URL object with the default port number 80
     *
     * @param url the URL to parse
     */
    public URL(String url) {
        /*
         * first we create a URL matcher that makes sure that we have a valid URL
		 * the leading slash that we add here will be removed in the case of a duplicate
		 * the reason that we add this is so we can accurately grab the hostnames of a url
		 */
        Matcher hostnameMatcher = hostnamePattern.matcher(url);

		/*
         * now we set the hostname based on a matcher
		 */
        if (!hostnameMatcher.find()) {
            hostname = "";
        } else {
            hostname = url.substring(hostnameMatcher.start(), hostnameMatcher.end());
        }

		/*
         * we remove the hostname from the path
		 * we remove multiple slashes from the url
		 * and replace them with a single slash
		 */
        fullPath = (url.replaceFirst(hostname, "")).replaceAll("(/)\\1+", "/");

		/*
		 * we wanted to remove http from fullPath
		 * so we remove http from the hostname here
		 * and we also want to remove the first slash
		 */
        hostname = hostname.replaceFirst("http://", "");

		/*
		 * now we construct a pathlist that also contains the hostname
		 */
        LinkedList<String> pathLinkedList = new LinkedList<String>(Arrays.asList(fullPath.split("/")));

		/*
		 * this is a nasty hack to remove the first element if it
		 * is empty, essentially we fix the file name
		 */
        if (pathLinkedList.size() > 1) {
            if (pathLinkedList.get(0).equals("")) {
                pathLinkedList.remove(0);
            }
            fileName = pathLinkedList.removeLast();
        } else if (pathLinkedList.size() == 1) {
            fileName = fullPath;
            pathLinkedList.remove(0);
        }

		/*
		 * now we simply create an iterator over the path list so we
		 * can grab the next part of the path
		 */
        pathListIterator = pathLinkedList.listIterator();
    }

    /**
     * Construct a URL object with a set URL and portNumber
     *
     * @param url        the URL to parse
     * @param portNumber the portNumber to set the URL to
     */
    URL(String url, int portNumber) {
        this(url);
        this.portNumber = portNumber;
    }

    /**
     * Get the hostname of the URL
     *
     * @return the hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Get the full path of the URL
     *
     * @return the full path
     */
    public String getFullPath() {
        return fullPath;
    }

    /**
     * Get the port number
     *
     * @return the port number for the url.
     */
    public int getPortNumber() {
        return portNumber;
    }

    /**
     * Get the path without the last index
     *
     * @return the path without the last index
     */
    public String getPath() {
        Integer fullPathIndex = fullPath.lastIndexOf("/");
        if (fullPathIndex == -1) {
            return fullPath;
        }
        return fullPath.substring(0, fullPath.lastIndexOf("/"));
    }

    /**
     * Get the next path
     *
     * @return the next path
     */
    public String next() {
        return pathListIterator.next();
    }

    /**
     * Get the next path
     *
     * @return the next path
     */
    public boolean hasNext() {
        return pathListIterator.hasNext();
    }

    /**
     * Detect if the URL is an html link
     *
     * @return true if it is html, false otherwise
     */
    public boolean isHtml() {
        return fullPath.contains(".html");
    }

    /**
     * Get the file of the URL
     *
     * @return the file
     * @throws MalformedURLException thrown when there is no file
     */
    public String getFile() throws MalformedURLException {
        if (fileName.equals("")) {
            throw new MalformedURLException();
        }
        return fileName;
    }

    /**
     * We override the toString method to make
     * URL building easier
     *
     * @return the string
     */
    @Override
    public String toString() {
        return hostname + fullPath;
    }

    /**
     * Makes iterator happy
     */
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Get the iterator object
     *
     * @return this iterator
     */
    public Iterator<String> iterator() {
        return this;
    }
}
