package com.pilotprogrammer.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;

@Path("/file")
public class FileService {	
	@GET
	@Path("/byteArray")
	public byte[] byteArray(@Context HttpServletResponse resp, @QueryParam("loc") FileLocation loc) throws IOException {
		byte[] fileContent = getResource(resp, loc);
		return fileContent;
	}
	
	@GET
	@Path("/wrappedByteArray")
	public Response wrappedByteArray(@Context HttpServletResponse resp, @QueryParam("loc") FileLocation loc) throws IOException {
		byte[] fileContent = getResource(resp, loc);
		return Response.status(418).entity(fileContent).header("CustomeHeaderKey", "CustomHeaderValue").build();
	}
	
	private byte[] getResource(HttpServletResponse resp, FileLocation loc) throws IOException {		
		if (loc == null) {
			throw new IllegalArgumentException("File location not provided.");
		}
		
		URL url;
		if (loc.shouldUseClassLoader()) {
			url = getClass().getClassLoader().getResource(loc.getFilePath());
		} else {
			url = getClass().getResource(loc.getFilePath());
		}

		if (url == null) {
			throw new FileNotFoundException();
		}
		
		File file = new File(url.getFile());
		resp.addHeader("Content-Disposition",  "attachment; filename=\""+ loc.toString() +".txt\"");
		byte[] fileContent = FileUtils.readFileToByteArray(file);
		return fileContent;
	}
}