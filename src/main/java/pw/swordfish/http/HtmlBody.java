package pw.swordfish.http;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlBody implements Iterable<HtmlLink>, Iterator<HtmlLink> {
	private static Pattern htmlTagPattern = Pattern.compile("<a\\b[^>]*href=\"[^>]*>(.*?)", Pattern.CASE_INSENSITIVE);
	private static Pattern htmlPattern = Pattern.compile("<html.*");
	private LinkedList<HtmlLink> htmlLinkLinkedList = new LinkedList<HtmlLink>();
	private Iterator<HtmlLink> htmlLinkLinkedListIterator;
	private String html = "";

	/**
	 * Method to construct an html Body that will spit out HtmlLinks
	 * @param htmlBody the html body to search for links in
	 */
	public HtmlBody(String htmlBody) {
		Matcher htmlTagMatcher = htmlTagPattern.matcher(htmlBody);
		Matcher htmlMatcher = htmlPattern.matcher(htmlBody);

		htmlMatcher.find();
		html = htmlBody.substring(htmlMatcher.start());

		/**
		 * we want to iterate through the entire list of links
		 * so we simply continue matching links until we cannot
		 * find another. we also inform the user of improperly
		 * formatted links. we could have done this search on the fly,
		 * but I like this solution better
		 */
		while(htmlTagMatcher.find()) {
			String htmlLink = htmlTagMatcher.group();
			try {
				HtmlLink htmlLinkTemp = new HtmlLink(htmlLink);
				htmlLinkLinkedList.add(htmlLinkTemp);
			} catch (MalformedHtmlException ex) {
				System.err.println("Malformed Html Link: " + htmlLink);
			} catch (MalformedURLException ex) {
				System.err.println("Malformed URL in Html Link: " + htmlLink);
			}
		}

		htmlLinkLinkedListIterator = htmlLinkLinkedList.iterator();
	}

	/**
	 * Method to get the next html url
	 * @return the next html url
	 */
	public HtmlLink next() {
		return htmlLinkLinkedListIterator.next();
	}

	/**
	 * Method to check to see if we still have more links in the html message
	 * @return true if there is another url, false otherwise
	 */
	public boolean hasNext() {
		return htmlLinkLinkedListIterator.hasNext();
	}

	/**
	 * Method to get the iterator of an html body to make iterating easier
	 * @return this iterator
	 */
	public Iterator<HtmlLink> iterator() {
		return this;
	}

	/**
	 * Makes iterator happy
	 */
	public void remove() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Get the html document
	 * @return the html document
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * Change the links of the html body so that their
	 * prefix maps differently
	 * @param prefix the new prefix
	 */
	public void appendLinkPrefix(String prefix) {
		/*
		 * we could do this in the main method,
		 * but a lot of the time we don't need to
		 * compute this, so we will just do it on the fly
		 */
		for(HtmlLink htmlLink : htmlLinkLinkedList) {
			html = html.replace(htmlLink.getURL().toString(), prefix + htmlLink.getURL().toString());
		}
	}
}
