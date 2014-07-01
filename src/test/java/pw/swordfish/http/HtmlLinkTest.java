package pw.swordfish.http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HtmlLinkTest {
    /**
     * Test of getURL method, of class HtmlLink.
     *
     * @throws MalformedHtmlException
     * @throws MalformedURLException
     */
    @Test
    public void testGetURL() {
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
    @Test(expected = IllegalStateException.class)
    public void testGetURLMissingHref() {
        System.out.println("getURLMissingHref");
        new HtmlLink("www.google.ca");
    }
}
