package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.MultiPartSpecification;

@QuarkusTest
public class ReactiveGreetingResourceTest {
    private static final byte[] TEXT_WITH_DIACRITICS_BYTES = new byte[] { 80, -59, -103, 105, 107, 114, -61, -95, -59, -95, 108,
            101, 110, -61, -67, 32, -59, -66, 108, 111, -59, -91, 111, 117, -60, -115, 107, -61, -67, 32, 107, -59, -81, -59,
            -120, 32, -61, -70, 112, -60, -101, 108, 32, -60, -113, -61, -95, 98, 101, 108, 115, 107, -61, -87, 32, -61, -77,
            100, 121, 46 };
    // "Přikrášlený žloťoučký kůň úpěl ďábelské ódy."
    private static final String TEXT_WITH_DIACRITICS = new String(TEXT_WITH_DIACRITICS_BYTES, StandardCharsets.UTF_8);

    @Test
    public void testTextPartFromMultipart() {
        whenSendMultipartData("/multipart/text")
                .contentType(ContentType.TEXT)
                .body(equalTo(TEXT_WITH_DIACRITICS));
    }

    private ValidatableResponse whenSendMultipartData(String path) {
        MultiPartSpecification textSpec = new MultiPartSpecBuilder(TEXT_WITH_DIACRITICS)
                .controlName("text")
                .mimeType("text/plain")
                .charset(StandardCharsets.UTF_8)
                .build();

        return RestAssured.given()
                .contentType("multipart/form-data")
                .multiPart(textSpec)
                .post(path)
                .then()
                .statusCode(200);
    }

}