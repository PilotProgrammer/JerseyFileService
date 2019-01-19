package com.pilotprogrammer.service;

import java.io.FileNotFoundException;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;

@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {

	public Response toResponse(Exception exception) {
		Integer errorCode = 500;
		String errorMessage = "Code Red!";
		
		if (exception instanceof FileNotFoundException) {
			errorCode = 400;
			errorMessage = "We couldn't find the specified file.";
		} else if (exception instanceof IllegalArgumentException) {
			errorCode = 400;
			errorMessage = "You didn't provide the right parameters.";
		}
		
		ErrorObject obj = new ErrorObject();
		obj.setErrorCode(errorCode);
		obj.setErrorMessage(errorMessage);
		
		Gson gson = new Gson();
		String content = gson.toJson(obj);
		return Response.status(errorCode).entity(content).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).build();	
	}
	
	public static class ErrorObject {
		private Integer errorCode;
		private String errorMessage;
		
		public Integer getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(Integer errorCode) {
			this.errorCode = errorCode;
		}
		public String getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
	}

}
