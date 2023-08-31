package vux.codejava.entity;

public class CustomMessage {

	//status => 300: ok
	//		 => 305: fail
	//		 => 400: not
	private String status;
	private String message;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
