package com.ebsco.training.refarch.dependabot.controller;

import com.ebsco.training.refarch.test_dependabot.api.V0TFormersApiDelegate;
import com.ebsco.training.refarch.test_dependabot.model.TransformerFaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
public class V0TFormersApiDelegateImpl implements V0TFormersApiDelegate {

    private final EnumMap<TransformerFaction, List<String>> transformers;

    public V0TFormersApiDelegateImpl() {
        transformers = new EnumMap<>(TransformerFaction.class);
        transformers.put(TransformerFaction.AUTOBOTS, Stream.of("Hercules", "Dagobert")
            .collect(Collectors.toList()));
        transformers.put(TransformerFaction.DECEPTICONS, Stream.of("Megatron", "Starscream")
            .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<Void> v0DeleteTransformer(TransformerFaction transformerFaction, String transformerName) throws Exception {

        transformers.get(transformerFaction).remove(transformerName);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<String>> v0GetTransformers(TransformerFaction transformerFaction, Boolean sorted) throws Exception {

        List<String> transformersToReturn = new ArrayList<>(transformers.get(transformerFaction));
        if (Boolean.TRUE.equals(sorted)) {
            Collections.sort(transformersToReturn);
        }
        return ResponseEntity.of(Optional.of(transformersToReturn));
    }

    @Override
    public ResponseEntity<Void> v0PostTransformers(TransformerFaction transformerFaction, String transformerName) throws Exception {
        // complex regexp, can allow DDOS attack
        if (transformerName.matches("(.)*solve/challenges/server-side(.)*")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        // array index out of bound
        int sum = 0;
        int[] a = {1, 2, 3};
        for (int i = 0; i <= a.length; i++) { // BAD
            sum += a[i];
        }
        System.out.println(sum);

        transformers.get(transformerFaction).add(transformerName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
