package com.ebsco.training.refarch.dependabot.rest;

import com.ebsco.training.refarch.dependabot.Application;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = { "spring.application.name=training.refarch.dependabot" })
@SpringBootTest(classes = Application.class)
@AutoConfigureObservability
public class V0TFormersApiIT {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private String expectedAppName = "training.refarch.dependabot";

    protected MockMvc mvc;

    @BeforeEach
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Value("${spring.application.name}")
    private String applicationName;

    @Test
    public void contextLoads() {
        assertEquals(expectedAppName, applicationName, "Expected to have appName set to " + expectedAppName);
    }

    @Test
    public void transformers_GET_OK() throws Exception {

        ResultActions resultActions = this.mvc.perform(get("/v0/transformers/AUTOBOTS").accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());

        List<String> resultList = Arrays.asList(
            objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), String[].class));

        assertThat(resultList).contains("Hercules");
        assertThat(resultList).contains("Dagobert");
    }

    @Test
    public void transformers_POST_CREATED() throws Exception {

        ResultActions resultActions = this.mvc.perform(post("/v0/transformers/DECEPTICONS/Frenzy"));
        resultActions.andExpect(status().isCreated());

    }

    @Test
    public void transformers_DELETE_NO_CONTENT() throws Exception {

        ResultActions resultActions = this.mvc.perform(delete("/v0/transformers/DECEPTICONS/Frenzy"));
        resultActions.andExpect(status().isNoContent());

    }

    @Test
    public void notfound_404() throws Exception {

        ResultActions resultActions = this.mvc.perform(get("/this-route-does-not-exist").accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().is(404));
    }
}
