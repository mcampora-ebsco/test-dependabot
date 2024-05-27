package com.ebsco.training.refarch.dependabot.controller;

import com.ebsco.platform.shared.junit.RestAssuredExtension;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("V0TFormer API Tests")
@Log4j2
class V0TFormersApiE2ETest {

    @Test
    @DisplayName("should return status 200 when calling /v0/transformers/AUTOBOTS")
    void shouldReturnStatus200AndAutobotsWhenCallingGet() {
        List<String> expectedTransformers = List.of("Bumblebee", "Optimus Prime");

        String[] response = given()
            .header("X-EIS-LOG-AUTHNID", "id:authn:abcde")
            .header("X-EIS-LOG-AUTHZID", "id:authz:vwxyz")
            .header("isTest", true)
            .when()
            .get("/v0/transformers/AUTOBOTS")
            .then()
            .statusCode(OK.value())
            .contentType("application/json")
            .extract()
            .as(String[].class);

        assertThat(response).containsAll(expectedTransformers);
    }

    @Test
    @DisplayName("should return status 200 when calling /v0/transformers/DECEPTICONS")
    void shouldReturnStatus200AndDecepticonsWhenCallingGet() {
        List<String> expectedTransformers = List.of("Megatron", "Starscream");

        String[] response = given()
            .header("X-EIS-LOG-AUTHNID", "id:authn:abcde")
            .header("X-EIS-LOG-AUTHZID", "id:authz:vwxyz")
            .header("isTest", true)
            .when()
            .get("/v0/transformers/DECEPTICONS")
            .then()
            .statusCode(OK.value())
            .contentType("application/json")
            .extract()
            .as(String[].class);

        assertThat(response).containsAll(expectedTransformers);
    }

    @Test
    @DisplayName("should return status 404")
    void shouldReturn404() {
        given()
            .header("X-EIS-LOG-AUTHNID", "id:authn:abcde")
            .header("X-EIS-LOG-AUTHZID", "id:authz:vwxyz")
            .header("isTest", true)
            .when()
            .get("/this-route-does-not-exist")
            .then()
            .log().all()
            .statusCode(NOT_FOUND.value())
            .contentType("application/json")
            .assertThat().body("error", not(blankOrNullString()));
    }
}
