package pw.swordfish.http;

import java.net.MalformedURLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HtmlLinkTest {

    public HtmlLinkTest() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

	/**
	 * Test of getURL method, of class HtmlLink.
	 *
	 * @throws MalformedHtmlException
	 * @throws MalformedURLException
	 */
	@Test
	public void testGetURL() throws MalformedHtmlException, MalformedURLException {
		System.out.println("getURL");
		HtmlLink instance = new HtmlLink("href=\"http://www.google.ca/\"");
		URL expResult = new URL("http://www.google.ca/");
		URL result = instance.getURL();
		assertEquals(expResult.toString(), result.toString());
	}

	/**
	 * Test of getURL method, of class HtmlLink.
	 *
	 * @throws MalformedHtmlException
	 * @throws MalformedURLException
	 */
	@Test(expected=MalformedHtmlException.class)
	public void testGetURLMissingHref() throws MalformedHtmlException, MalformedURLException {
		System.out.println("getURLMissingHref");
		new HtmlLink("www.google.ca");
	}
}