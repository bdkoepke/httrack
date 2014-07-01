package pw.swordfish.http;

import org.junit.Test;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;

public class URLTest {

    /**
     * Test of getPortNumber method, of class URL.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testGetPortNumber() throws MalformedURLException {
        System.out.println("getPortNumber");
        URL instance = new URL("http://www.google.ca", 2000);
        int expResult = 2000;
        int result = instance.getPortNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of nextPath method, of class URL.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testNextPath() throws MalformedURLException {
        System.out.println("nextPath");
        URL instance = new URL("http://www.google.ca/usr/share/temp");
        String expResult = "share";
        instance.next();
        String result = instance.next();
        assertEquals(expResult, result);
    }

    /**
     * Test of hasNextPath method, of class URL.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testHasNextPathFile() throws MalformedURLException {
        System.out.println("hasNextPathFile");
        URL instance = new URL("http://www.google.ca/usr/share/temp.html");
        instance.next();
        instance.next();
        boolean expResult = false;
        boolean result = instance.hasNext();
        assertEquals(expResult, result);
    }

    /**
     * Test of hasNextPath method, of class URL.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testHasNextPath() throws MalformedURLException {
        System.out.println("hasNextPath");
        URL instance = new URL("http://www.google.ca/usr/share/test.html");
        instance.next();
        boolean expResult = true;
        boolean result = instance.hasNext();
        assertEquals(expResult, result);
    }

    /**
     * Test of isHtml method, of class URL.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testIsHtmlFail() throws MalformedURLException {
        System.out.println("isHtmlFail");
        URL instance = new URL("http://www.google.ca/index.htm");
        boolean expResult = false;
        boolean result = instance.isHtml();
        assertEquals(expResult, result);
    }

    /**
     * @throws MalformedURLException
     */
    @Test
    public void testIsHtml() throws MalformedURLException {
        System.out.println("isHtml");
        URL instance = new URL("http://www.google.ca/index.html");
        boolean expResult = true;
        boolean result = instance.isHtml();
        assertEquals(expResult, result);
    }

    /**
     * @throws MalformedURLException
     */
    @Test
    public void testIsHtmlNoHostname() throws MalformedURLException {
        System.out.println("isHtmlNoHostname");
        URL instance = new URL("cpsc441/index.html");
        boolean expResult = true;
        boolean result = instance.isHtml();
        assertEquals(expResult, result);
    }

    /**
     * @throws MalformedURLException
     */
    @Test
    public void testGetFileNoHostname() throws MalformedURLException {
        System.out.println("getFileNoHostname");
        URL instance = new URL("cpsc441/index.html");
        String expResult = "index.html";
        String result = instance.getFile();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFile method, of class URL.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testGetFile() throws MalformedURLException {
        System.out.println("getFile");
        URL instance = new URL("http://www.google.ca/index.html");
        String expResult = "index.html";
        String result = instance.getFile();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFile method, of class URL
     *
     * @throws MalformedURLException
     */
    @Test(expected = MalformedURLException.class)
    public void testGetFileNone() throws MalformedURLException {
        System.out.println("getFileNone");
        URL instance = new URL("http://www.google.ca");
        instance.getFile();
    }

    /**
     * Test of getHostname method, of class URL.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testGetHostname() throws MalformedURLException {
        System.out.println("getHostname");
        URL instance = new URL("http://www.cs.ucalgary.ca");
        String expResult = "www.cs.ucalgary.ca";
        String result = instance.getHostname();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPath method, of class URL.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testGetPath() throws MalformedURLException {
        System.out.println("getPath");
        URL instance = new URL("http://www.complicated.com///morecomplicated///supercomplicated");
        String expResult = "/morecomplicated";
        String result = instance.getPath();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHostname method, of class URL.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testGetHostnameComplex() throws MalformedURLException {
        System.out.println("getHostnameComplex");
        URL instance = new URL("http://www.complicated.com///morecomplicated///supercomplicated");
        String expResult = "www.complicated.com";
        String result = instance.getHostname();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFullPath method, of class URL.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testGetFullPath() throws MalformedURLException {
        System.out.println("getFullPath");
        URL instance = new URL("http://www.google.ca");
        String expResult = "";
        instance.getFullPath();
        String result = instance.getFullPath();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPath method, of class URL.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testGetPathComplex() throws MalformedURLException {
        System.out.println("getPathComplex");
        URL instance = new URL("http://www.complicated.com///morecomplicated///supercomplicated");
        String expResult = "/morecomplicated/supercomplicated";
        String result = instance.getFullPath();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFullPath method, of class URL.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testFullPathOnly() throws MalformedURLException {
        System.out.println("pathOnly");
        URL instance = new URL("/usr/index.html");
        String expResult = "/usr/index.html";
        String result = instance.getFullPath();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHostname method, of class URL.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testPathOnlyFile() throws MalformedURLException {
        System.out.println("pathOnlyFile");
        URL instance = new URL("index.html");
        String expResult = "index.html";
        String result = instance.getFile();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class URL.
     *
     * @throws MalformedURLException
     */
    @Test
    public void testToString() throws MalformedURLException {
        System.out.println("toString");
        URL instance = new URL("http://www.google.ca/mail");
        String expResult = "www.google.ca/mail";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}