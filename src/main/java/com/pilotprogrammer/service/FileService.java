package com.pilotprogrammer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

@Path("/file")
public class FileService {	
	@GET
	@Path("/byteArray")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public byte[] byteArray(@Context HttpServletResponse resp, @QueryParam("loc") FileLocation loc) throws IOException {
		byte[] fileContent = getResource(resp, loc);
		return fileContent;
	}
	
	@GET
	@Path("/wrappedByteArray")
	public Response wrappedByteArray(@Context HttpServletResponse resp, @QueryParam("loc") FileLocation loc) throws IOException {
		byte[] fileContent = getResource(resp, loc);
		return Response.ok(fileContent, MediaType.APPLICATION_OCTET_STREAM).build();
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
		resp.addHeader("Content-Disposition",  "attachment; filename=\""+ loc.toString() +"\"");
		byte[] fileContent = FileUtils.readFileToByteArray(file);

		return fileContent;
	}
}