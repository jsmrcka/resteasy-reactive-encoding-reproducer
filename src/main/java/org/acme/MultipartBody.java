package org.acme;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

public class MultipartBody {

    @RestForm("text")
    @PartType(MediaType.TEXT_PLAIN)
    public String text;
}
