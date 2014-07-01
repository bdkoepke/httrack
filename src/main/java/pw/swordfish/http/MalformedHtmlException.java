package pw.swordfish.http;

public class MalformedHtmlException extends Exception {
	private static final long serialVersionUID = 2L;
	MalformedHtmlException() {
		super();
	}

	MalformedHtmlException(String message) {
		super(message);
	}
}
