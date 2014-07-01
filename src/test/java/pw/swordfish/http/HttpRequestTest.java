package pw.swordfish.http;

import org.junit.Test;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;

public class HttpRequestTest {
    /**
     * Test of getURL method, of class HttpRequest.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testGetURLFromURL() throws MalformedURLException {
        System.out.println("getURLFromURL");
        HttpRequest instance = new HttpRequest(new URL("http://www.google.ca"));
        URL expResult = new URL("http://www.google.ca");
        URL result = instance.getURL();
        assertEquals(expResult.toString(), result.toString());
    }

    /**
     * Test of getURL method, of class HttpRequest.
     *
     * @throws MalformedURLException
     * @throws MalformedHttpException
     */
    @Test
    public void testGetURLFromString() throws MalformedURLException, MalformedHttpException {
        System.out.println("getURLFromString");
        HttpRequest instance = new HttpRequest("GET http://www.google.ca/ HTTP/1.1");
        URL expResult = new URL("http://www.google.ca/");
        URL result = instance.getURL();
        assertEquals(expResult.toString(), result.toString());
    }

    /**
     * Test of getURL method, of class HttpRequest.
     *
     * @throws MalformedURLException
     * @throws MalformedHttpException
     */
    //@Test(expected=MalformedHttpException.class)
    @Test
    public void testGetURLFromStringCaseSensitivity() throws MalformedURLException, MalformedHttpException {
        System.out.println("getURLFromStringCaseSensitivity");
        HttpRequest instance = new HttpRequest("get http://www.google.ca/ HTTP/1.1");
        URL expResult = new URL("http://www.google.ca/");
        URL result = instance.getURL();
        assertEquals(expResult.toString(), result.toString());
    }

    /**
     * Test of getURL method, of class HttpRequest.
     *
     * @throws MalformedURLException
     * @throws MalformedHttpException
     */
    @Test(expected = MalformedHttpException.class)
    public void testGetURLFromStringMissingGet() throws MalformedURLException, MalformedHttpException {
        System.out.println("getURLFromStringMissingGet");
        HttpRequest instance = new HttpRequest("http://www.google.ca HTTP/1.1");
        URL expResult = new URL("http://www.google.ca");
        URL result = instance.getURL();
        assertEquals(expResult.toString(), result.toString());
    }

    /**
     * Test of getURL method, of class HttpRequest.
     *
     * @throws MalformedURLException
     * @throws MalformedHttpException
     */
    @Test(expected = MalformedHttpException.class)
    public void testGetURLFromStringMissingHttp() throws MalformedURLException, MalformedHttpException {
        System.out.println("getURLFromStringMissingHttp");
        HttpRequest instance = new HttpRequest("GET http://www.google.ca");
        URL expResult = new URL("http://www.google.ca");
        URL result = instance.getURL();
        assertEquals(expResult.toString(), result.toString());
    }
}