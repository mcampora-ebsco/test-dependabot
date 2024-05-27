package com.ebsco.training.refarch.dependabot.controller;

import com.ebsco.training.refarch.test_dependabot.model.TransformerFaction;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@AutoConfigureObservability
public class V0TFormersApiDelegateImplTest {

    private final V0TFormersApiDelegateImpl sut = new V0TFormersApiDelegateImpl();

    @Test
    void v0GetTransformers_with_autobots() throws Exception {
        // given
        TransformerFaction faction = TransformerFaction.AUTOBOTS;

        // when
        ResponseEntity<List<String>> response = sut.v0GetTransformers(faction, false);

        // then
        assertThat(response.getBody(), containsInAnyOrder("Hercules", "Dagobert"));
    }

    @Test
    void v0GetTransformers_with_autobots_sorted() throws Exception {
        // given
        TransformerFaction faction = TransformerFaction.AUTOBOTS;

        // when
        ResponseEntity<List<String>> response = sut.v0GetTransformers(faction, true);

        // then
        assertThat(response.getBody(), containsInRelativeOrder("Dagobert", "Hercules"));
    }

    @Test
    void v0GetTransformers_with_decepticons() throws Exception {
        // given
        TransformerFaction faction = TransformerFaction.DECEPTICONS;

        // when
        ResponseEntity<List<String>> response = sut.v0GetTransformers(faction, false);

        // then
        assertThat(response.getBody(), containsInAnyOrder("Megatron", "Starscream"));
    }

    @Test
    void v0DeleteTransformer_returns_204NoContent() throws Exception {
        // given
        TransformerFaction faction = TransformerFaction.DECEPTICONS;

        // when
        ResponseEntity<Void> response = sut.v0DeleteTransformer(faction, "Shockwave");

        // then
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.NO_CONTENT)));
    }

    @Test
    void v0PostTransformers_returns_201Created() throws Exception {
        // given
        TransformerFaction faction = TransformerFaction.AUTOBOTS;

        // when
        ResponseEntity<Void> response = sut.v0PostTransformers(faction, "Sideswipe");

        // then
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.CREATED)));
    }
}

