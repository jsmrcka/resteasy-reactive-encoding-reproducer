package org.acme;

import static org.hamcrest.CoreMatchers.equalTo;

import java.nio.charset.StandardCharsets;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.MultiPartSpecification;

@QuarkusTest
public class EchoResourceTest {
    private static final byte[] TEXT_WITH_DIACRITICS_BYTES = new byte[] { 75, -59, -107, 100, 101, -60, -66, 32, -59, -95, -59,
            -91, 97, 115, 116, 110, -61, -67, 99, 104, 32, -60, -113, 97, 116, -60, -66, 111, 118, 32, 117, -60, -115, -61, -83,
            32, 112, 114, 105, 32, -61, -70, 115, 116, -61, -83, 32, 86, -61, -95, 104, 117, 32, 109, -60, -70, 107, 118, 101,
            104, 111, 32, 107, 111, -59, -120, 97, 32, 111, 98, 104, 114, -61, -67, 122, 97, -59, -91, 32, 107, -61, -76, 114,
            117, 32, 97, 32, -59, -66, 114, 97, -59, -91, 32, -60, -115, 101, 114, 115, 116, 118, -61, -87, 32, 109, -61, -92,
            115, 111, 46 };
    // "Kŕdeľ šťastných ďatľov učí pri ústí Váhu mĺkveho koňa obhrýzať kôru a žrať čerstvé mäso."
    private static final String TEXT_WITH_DIACRITICS = new String(TEXT_WITH_DIACRITICS_BYTES, StandardCharsets.UTF_8);

    @Test
    public void testEcho() {
        RestAssured.given()
//                .config(RestAssured.config()
//                        .decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset(StandardCharsets.UTF_8)))
                .contentType(ContentType.TEXT.withCharset(StandardCharsets.UTF_8))
                .body(TEXT_WITH_DIACRITICS)
                .post("/echo/text")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.TEXT)
                .body(equalTo(TEXT_WITH_DIACRITICS));
    }

    @Test
    public void testMultipartEcho() {
        MultiPartSpecification textSpec = new MultiPartSpecBuilder(TEXT_WITH_DIACRITICS)
                .controlName("text")
                .mimeType("text/plain")
                .charset(StandardCharsets.UTF_8)
                .build();
        RestAssured.given()
//                .config(RestAssured.config()
//                        .decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset(StandardCharsets.UTF_8)))
                .contentType(ContentType.MULTIPART)
                .multiPart(textSpec)
                .post("/echo/multipart")
                .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body(equalTo(TEXT_WITH_DIACRITICS));
    }
}