package pw.swordfish.http;

import java.net.MalformedURLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HtmlBodyTest {
	HtmlBody instance;

    public HtmlBodyTest() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

    @Before
    public void setUp() {
	    instance = new HtmlBody("" +
		    "HTTP/1.0 " + HttpStatusCodes.HTTPSTATUSOK + "\n" +
		    "Date: Thu, 11 Feb 2010 19:02:11\n" +
		    "Server: Apache/2.0.52 (Red Hat)\n" +
		    "Last-Modified: Mon, 19 Oct 2009 19:56:53\n" +
		    "ETag: \"1f5c09f-134-1c79ffc0\"\n" +
		    "Accept-Ranges: bytes\n" +
		    "Content-Length: 308\n" +
		    "Connection: close\n" +
		    "Content-Type: text/html\n" +
		    "\n" +
		    "<html>\n" +
		    "\n" +
		    "<head>\n" +
		    "<meta http-equiv=\"Content-Language\" content=\"en-us\"\n" +
		    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\n" +
		    "<title>New Page 1</title>\n" +
		    "</head>\n" +
		    "\n" +
		    "<body>\n" +
		    "\n" +
		    "<p><a href=\"d1.html\"> d1 </a></p>\n" +
		    "\n" +
		    "<p><a href=\"d2.html\"> d2 </a></p>\n" +
		    "\n" +
		    "<p><a href=\"d3.html\"> d3 </a></p>\n" +
		    "\n" +
		    "<li>CPSC 441: <a href=\"cpsc441/index.html\">Computer\n" +
		    "Communications</a></li>\n" +
		    "</body>\n" +
		    "\n" +
		    "</html>\n");
    }

    @After
    public void tearDown() {
    }

	/**
	 * Test of nextHtmlLink method, of class HtmlBody.
	 *
	 * @throws MalformedURLException
	 */
	@Test
	public void testNextHtmlLink() throws MalformedURLException {
		System.out.println("nextHtmlLink");
		URL expResult = new URL("cpsc441/index.html");
		instance.next();
		instance.next();
		instance.next();
		URL result = instance.next().getURL();
		assertEquals(expResult.toString(), result.toString());
	}

	/**
	 * Test of hasNextHtmlLink method, of class HtmlBody.
	 */
	@Test
	public void testHasNextHtmlLinkFalse() {
		System.out.println("hasNextHtmlLinkFalse");
		instance.next();
		instance.next();
		instance.next();
		instance.next();
		boolean result = instance.hasNext();
		assertEquals(false, result);
	}

	/**
	 * Test of hasNextHtmlLink method, of class HtmlBody.
	 */
	@Test
	public void testHashNextHtmlLinkTrue() {
		System.out.println("hasNextHtmlLinkTrue");
		instance.next();
		boolean result = instance.hasNext();
		assertEquals(true, result);
	}

	@Test
	public void testChangeLinkPrefix() {
		System.out.println("changeLinkPrefix");
		instance.appendLinkPrefix("http://www.google.ca/");
		String expResult = "<html>\n" +
		    "\n" +
		    "<head>\n" +
		    "<meta http-equiv=\"Content-Language\" content=\"en-us\"\n" +
		    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\n" +
		    "<title>New Page 1</title>\n" +
		    "</head>\n" +
		    "\n" +
		    "<body>\n" +
		    "\n" +
		    "<p><a href=\"http://www.google.ca/d1.html\"> d1 </a></p>\n" +
		    "\n" +
		    "<p><a href=\"http://www.google.ca/d2.html\"> d2 </a></p>\n" +
		    "\n" +
		    "<p><a href=\"http://www.google.ca/d3.html\"> d3 </a></p>\n" +
		    "\n" +
		    "<li>CPSC 441: <a href=\"http://www.google.ca/cpsc441/index.html\">Computer\n" +
		    "Communications</a></li>\n" +
		    "</body>\n" +
		    "\n" +
		    "</html>\n";
		String result = instance.getHtml();
		assertEquals(expResult, result);
	}

}