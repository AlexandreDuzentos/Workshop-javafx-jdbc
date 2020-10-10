package model.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	Map<String,String> error = new HashMap<>();
	
	public Map<String,String> getErrors(){
		return error;
	}
	
	public ValidationException(String msg) {
		super(msg);
	}
	
	public void addError(String fieldName,String errorMessage) {
		error.put(fieldName, errorMessage);
	}

}
