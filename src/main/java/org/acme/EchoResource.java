package org.acme;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/echo")
public class EchoResource {

    @POST
    @Path("/text")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String echo(String request) {
        return request;
    }

    @POST
    @Path("/multipart")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String multipartEcho(@MultipartForm MultipartBody multipartBody) {
        return multipartBody.text;
    }

}