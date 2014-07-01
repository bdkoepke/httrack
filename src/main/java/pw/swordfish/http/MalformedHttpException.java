package pw.swordfish.http;

public class MalformedHttpException extends Exception {
	private static final long serialVersionUID = 2L;

	MalformedHttpException() {
		super();
	}

	MalformedHttpException(String message) {
		super(message);
	}

}
