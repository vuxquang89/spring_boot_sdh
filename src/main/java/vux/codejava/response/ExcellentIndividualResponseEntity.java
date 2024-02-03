package vux.codejava.response;

public class ExcellentIndividualResponseEntity {
	private Integer status;
	private String error;
	private String message;
	private ExcellentIndividualResponse content;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ExcellentIndividualResponse getContent() {
		return content;
	}
	public void setContent(ExcellentIndividualResponse content) {
		this.content = content;
	}
	
}
