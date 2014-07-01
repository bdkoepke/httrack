package pw.swordfish.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlLink {
    private static final Pattern hrefPattern = Pattern.compile("href=\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE);

    private URL url;

    /**
     * Method to construct an html link from a string
     *
     * @param link the string to construct the html link from
     */
    HtmlLink(String link) {
        Matcher hrefMatcher = hrefPattern.matcher(link);

		/*
         * if we don't find a link in the string, then
		 * we must have been given an invalid link
		 * we'll let someone else deal with this...
		 */
        if (!hrefMatcher.find())
            throw new IllegalStateException("There is no link in the specified string");

		/*
         * now we construct the URL from the href
		 */
        link = link.substring(hrefMatcher.start(), hrefMatcher.end()).replaceFirst("href=\"", "").replaceFirst("\"", "");

        url = new URL(link);
    }

    /**
     * Method to get the constructed URL
     *
     * @return the url of the html link
     */
    public URL getURL() {
        return url;
    }
}
