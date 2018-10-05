package domain;

public class URLFetchNYTimes {
	private String url;
	private int httpStatusCode;
	
	public URLFetchNYTimes() {
		
	}
	
	public URLFetchNYTimes(String url, int httpStatusCode) {
		this.url = url;
		this.httpStatusCode = httpStatusCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

}
