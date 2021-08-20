package org.acme;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class MultipartBody {

    @FormParam("text")
    @PartType(MediaType.TEXT_PLAIN)
    public String text;
}
