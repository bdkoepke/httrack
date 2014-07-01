package pw.swordfish.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {
    private static final Pattern HTTPRequestPattern = Pattern.compile("get .* http\\/[0-9]\\.[0-9]", Pattern.CASE_INSENSITIVE);

    private URL url;

    /**
     * Method to construct an HttpRequest object from a given string
     *
     * @param header the header to parse
     * @throws MalformedHttpException thrown if the format of the string does not match the correct syntax of an http get header
     */
    public HttpRequest(String header) throws MalformedHttpException {
        Matcher HTTPHeaderRequestMatcher = HTTPRequestPattern.matcher(header);

        if (!HTTPHeaderRequestMatcher.find()) {
            throw new MalformedHttpException();
        }

        url = new URL(header.toLowerCase().replaceFirst("get ", "").replaceFirst(" http\\/[0-9]\\.[0-9]", ""));
    }

    /**
     * Method to construct an HTTPHeaderGet object for a specific URL
     *
     * @param url the url to construct the http header from
     */
    public HttpRequest(URL url) {
        this.url = url;
    }

    /**
     * Method to get the url of this header object
     *
     * @return the url
     */
    public URL getURL() {
        return url;
    }

    /**
     * We override the toString() method so we can construct an http get header
     *
     * @return a string representnig an http get header
     */
    @Override
    public String toString() {
        String HTTPVersion = "1.0";
        return "GET " + url + " HTTP/" + HTTPVersion;
    }
}
