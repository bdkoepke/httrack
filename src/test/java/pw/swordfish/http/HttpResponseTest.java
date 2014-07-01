package pw.swordfish.http;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class HttpResponseTest {
    private HttpResponse instance;

    @Before
    public void setUp() throws MalformedHttpException {
        instance = new HttpResponse("" +
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
                "</body>\n" +
                "\n" +
                "</html>\n");
    }

    /**
     * Test of getStatusCode method, of class HttpResponse.
     */
    @Test
    public void testGetStatusCode() {
        System.out.println("getStatusCode");
        String expResult = "200 OK";
        String result = instance.getStatusCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDate method, of class HttpResponse.
     */
    @Test
    public void testGetDate() {
        System.out.println("getDate");
        @SuppressWarnings("deprecation")
        Date expResult = new Date("Thu, 11 Feb 2010 19:02:11");
        Date result = instance.getDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getServer method, of class HttpResponse.
     */
    @Test
    public void testGetServer() {
        System.out.println("getServer");
        String expResult = "Apache/2.0.52 (Red Hat)";
        String result = instance.getServer();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLastModified method, of class HttpResponse.
     */
    @Test
    public void testGetLastModified() {
        System.out.println("getLastModified");
        @SuppressWarnings("deprecation")
        Date expResult = new Date("Mon, 19 Oct 2009 19:56:53");
        Date result = instance.getLastModified();
        assertEquals(expResult, result);
    }

    /**
     * Test of getContentType method, of class HttpResponse.
     */
    @Test
    public void testGetContentType() {
        System.out.println("getContentType");
        String expResult = "text/html";
        String result = instance.getContentType();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class HttpResponse.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "HTTP/1.0 200 OK\n" +
                "Date: Thu, 11 Feb 2010 19:02:11\n" +
                "Server: Apache/2.0.52 (Red Hat)\n" +
                "Last-Modified: Mon, 19 Oct 2009 19:56:53\n" +
                "Content-Type: text/html";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

}