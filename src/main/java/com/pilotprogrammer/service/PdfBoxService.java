package com.pilotprogrammer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.ServletContext;
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

@Path("/pdfDoc")
public class PdfBoxService {
	protected ServletContext servletContext;
	
	public PdfBoxService(final @Context ServletContext servletContext) {
		this.servletContext = servletContext;
	}
		
	@GET
	@Path("/byteArray")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public byte[] byteArray(@Context HttpServletResponse resp, @QueryParam("loc") FileLocation loc) throws IOException {
		if (loc == null) {
			throw new IllegalArgumentException("File location not provided.");
		}
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(loc.getFilePath()).getFile());
		resp.addHeader("Content-Disposition",  "attachment; filename=\"doc3.pdf\"");
		byte[] fileContent = FileUtils.readFileToByteArray(file);
		return fileContent;
	}
	
	@GET
	@Path("/byteArrayResponseObject")
	public Response byteArrayResponseObject() throws IOException {
		File file = new File(getClass().getResource("/SimpleForm.pdf").getFile());
		byte[] fileContent = FileUtils.readFileToByteArray(file);
		return Response.ok(fileContent, MediaType.APPLICATION_OCTET_STREAM)
				.header("content-disposition", "attachment; filename=\"doc.pdf\"").build();
	}
		
	@GET
	@Path("/byteArrayResponseObjectBigFile")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response byteArrayResponseObjectBigFile() throws IOException {
		byte[] fileContent = FileUtils.readFileToByteArray(
				new File("/Users/garrettgranacher/eclipse-workspace/google-samples/big-file.zip"));
		return Response.ok(fileContent, MediaType.APPLICATION_OCTET_STREAM)
				.header("content-disposition", "attachment; filename=\"big-file.zip\"").build();
	}
	
	@GET
	@Path("/outputStreamBigFile")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response outputStreamBigFile(@Context HttpServletResponse resp) throws IOException {
		FileInputStream ois = FileUtils.openInputStream(new File("/Users/garrettgranacher/eclipse-workspace/google-samples/big-file.zip"));
				
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