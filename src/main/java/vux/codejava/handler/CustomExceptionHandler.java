package vux.codejava.handler;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vux.codejava.exception.CategoryNotFoundException;


@RestControllerAdvice
public class CustomExceptionHandler{
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationArgument(MethodArgumentNotValidException ex){
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error->{
			errorMap.put(error.getField(), error.getDefaultMessage());
		});
		return errorMap;
	}
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, String> handleConstraintViolation(ConstraintViolationException ex){		
		Map<String, String> errorMap = new HashMap<>();
		String details = ex.getLocalizedMessage();
		errorMap.put("status", "401");
		errorMap.put("error", details);
		errorMap.put("message", "Kiểm tra dữ liệu đã tồn tại");
		return errorMap;
		
	}
	
	/*
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Map<String, String> handleAllExceptions(Exception ex){
		Map<String, String> errorMap = new HashMap<>();
		String details = ex.getLocalizedMessage();
		errorMap.put("status", "500");
		errorMap.put("error", details);
		errorMap.put("message", details);
		
		return errorMap;
	}
	*/
	/*
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
			
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("timestamp", new Date());
		responseBody.put("status", status.value());
			
		List<String> errors = ex.getBindingResult().getFieldErrors()
				.stream()
				.map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
			
		responseBody.put("errors", errors);
		return new ResponseEntity<>(responseBody, headers, status);
	}
	*/
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(CategoryNotFoundException.class)
	public Map<String, String> handleBusinessExcption(CategoryNotFoundException ex){
		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put("errorMessage", ex.getMessage());
		return errorMap;
	}
}
