package pw.swordfish.http;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpResponse {
	private SimpleDateFormat httpSimpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

	private static Pattern statusCodePattern = Pattern.compile("HTTP\\/[0-9]\\.[0-9] [0-9][0-9][0-9] .*");
	private static Pattern statusCodePatternNumber = Pattern.compile("[0-9][0-9][0-9] .*");
	private static Pattern datePattern = Pattern.compile("Date: .*");
	private static Pattern serverPattern = Pattern.compile("Server: .*");
	private static Pattern lastModifiedPattern = Pattern.compile("Last-Modified: .*");
	private static Pattern contentTypePattern = Pattern.compile("Content-Type: .*\\/.*");

	private String statusCode;
	private Date date;
	private String server = "Brandon/1.0.0";
	private Date lastModified = null;
	private String contentType = "text/html";

	/**
	 * Generate an HttpResponse given a string
	 * @param httpResponse
	 * @throws MalformedHttpException
	 */
	public HttpResponse(String httpResponse) throws MalformedHttpException {
		setStatusCode(httpResponse);
		setDate(httpResponse);
		setServer(httpResponse);
		setLastModified(httpResponse);
		setContentType(httpResponse);
	}

	/**
	 * Get the status code of the http response
	 * @return the status code
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * Get the date that http response was created
	 * @return the date of the header
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Get the server string of the http response
	 * @return the server string
	 */
	public String getServer() {
		return server;
	}

	/**
	 * Get the last modified time of the http response
	 * @return the last modified time
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * Get the content type of the http response
	 * @return the content type
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Method to simplify getting the content type
	 * @param httpResponse the http response to parse
	 * @throws MalformedHttpException thrown if a correct content type was not specified
	 */
	private void setContentType(String httpResponse) throws MalformedHttpException {
		Matcher contentTypeMatcher = contentTypePattern.matcher(httpResponse);

		/*
		 * if we don't find a content type then we throw an exception
		 */
		if(!contentTypeMatcher.find()) {
			throw new MalformedHttpException();
		}

		/*
		 * now we create the content type
		 */
		this.contentType = contentTypeMatcher.group().replace("Content-Type: ", "");
	}

	/**
	 * Method to simplify getting the last modified date
	 * @param httpResponse the http response to parse
	 * @throws MalformedHttpException thrown if a correct last modified date was not found
	 */
	@SuppressWarnings("deprecation")
	private void setLastModified(String httpResponse) throws MalformedHttpException {
		Matcher lastModifiedMatcher = lastModifiedPattern.matcher(httpResponse);

		if(lastModifiedMatcher.find()) {
			this.lastModified = new Date(lastModifiedMatcher.group().replaceFirst("Last-Modified: ", ""));
		}
	}
	/**
	 * Method to simplify getting the server string
	 * @param httpResponse the http response to parse
	 * @throws MalformedHttpException thrown if a correct server was not found
	 */
	private void setServer(String httpResponse) throws MalformedHttpException {
		Matcher serverMatcher = serverPattern.matcher(httpResponse);

		/*
		 * if we don't find a server header then we throw an exception
		 */
		if(!serverMatcher.find()) {
			throw new MalformedHttpException();
		}

		/*
		 * now we create the server string
		 */
		this.server = serverMatcher.group().replaceFirst("Server: ","");
	}

	/**
	 * Method to simplify setting the set date
	 * @param httpResponse the http response to parse
	 * @throws MalformedHttpException thrown if a correct date was not found
	 */
	@SuppressWarnings("deprecation")
	private void setDate(String httpResponse) throws MalformedHttpException {
		Matcher dateMatcher = datePattern.matcher(httpResponse);

		/*
		 * if we don't find a date header then we throw an exception
		 */
		if(! dateMatcher.find()) {
			throw new MalformedHttpException();
		}

		/*
		 * now we create the date
		 */
		this.date = new Date(dateMatcher.group().replaceFirst("Date: ",""));
	}

	/**
	 * Method to simplify setting the status code given the httpResponse
	 * @param httpResponse the http response to parse
	 * @throws MalformedHttpException thrown if a correct status code string was not found
	 */
	private void setStatusCode(String httpResponse) throws MalformedHttpException {
		Matcher statusCodeMatcher = statusCodePattern.matcher(httpResponse);
		Matcher statusCodeMatcherNumber = statusCodePatternNumber.matcher(httpResponse);

		/*
		 * if we don't find a valid status code header then we throw an exception
		 */
		if(! statusCodeMatcher.find()) {
			throw new MalformedHttpException();
		}

		/*
		 * now we look for the statusCode and set it
		 */
		statusCodeMatcherNumber.find();
		this.statusCode = statusCodeMatcherNumber.group();

		/*
		 * now we make sure that it is a status code that we actually understand
		 */
		if((! statusCode.equals(HttpStatusCodes.HTTPSTATUSOK)) && (! statusCode.equals(HttpStatusCodes.HTTPSTATUSBADREQUEST)) && (! statusCode.equals(HttpStatusCodes.HTTPSTATUSNOTFOUND))) {
			throw new MalformedHttpException();
		}
	}

	/**
	 * Method to construct an Http response message given various input variables
	 * @param statusCode the status code of the http header
	 * @param server the server type
	 * @param lastModified the last modified date
	 * @param contentType the content type of the message
	 */
	public HttpResponse(String statusCode, String server, Date lastModified, String contentType) {
		this(statusCode, lastModified);
		this.server = server;
		this.contentType = contentType;
	}

	/**
	 * Method to construct an Http response message without a last-modified time
	 * @param statusCode the status code of the http header
	 * @param server the server type
	 * @param contentType the content type of the message
	 */
	public HttpResponse(String statusCode, String server, String contentType) {
		this.statusCode = statusCode;
		this.server = server;
		this.contentType = contentType;
		this.date = new Date();
	}

	/**
	 * Method to construct an Http response message given only the status code and lastModified date
	 * @param statusCode the status code of the http header
	 * @param lastModified the last modified date
	 */
	public HttpResponse(String statusCode, Date lastModified) {
		this.statusCode = statusCode;
		this.date = new Date();
		this.lastModified = lastModified;
	}

	/**
	 * Makes it easier to represent an http response, we simply call the
	 * toString method and we have generated an http response
	 * @return the string representing an http response
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("HTTP/1.0 " + statusCode + "\n");
		result.append("Date: " + httpSimpleDateFormat.format(date) + "\n");
		result.append("Server: " + server + "\n");
		if(! (lastModified == null)) {
			result.append("Last-Modified: " + httpSimpleDateFormat.format(lastModified) + "\n");
		}
		result.append("Content-Type: " + contentType);
		return result.toString();
	}
}
