package com.pilotprogrammer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

@Path("/bigFile")
public class BigFileService {				
	@GET
	@Path("/byteArrayResponseObject")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response byteArrayResponseObject() throws IOException {
		byte[] fileContent = FileUtils.readFileToByteArray(
				new File("~/eclipse-workspace/google-samples/big-file.zip"));
		return Response.ok(fileContent, MediaType.APPLICATION_OCTET_STREAM)
				.header("content-disposition", "attachment; filename=\"big-file.zip\"").build();
	}
	
	@GET
	@Path("/outputStream")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response outputStream(@Context HttpServletResponse resp) throws IOException {
		FileInputStream ois = FileUtils.openInputStream(new File("~/eclipse-workspace/google-samples/big-file.zip"));
				
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                try {
                	IOUtils.copy(ois, output);
                } catch (Exception e) {
                    throw new WebApplicationException(e);
                } 
            }
        };

	    return Response.ok(stream).header("content-disposition","attachment; filename=\"big-file.zip\"").build();
	}
}