package me.antoniocaccamo.player.rx.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.resource.RemoteResource;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;

/**
 * @author antoniocaccamo on 09/04/2020
 */

@SpringBootTest @Slf4j
class LegacyServiceTest {

    @Autowired
    private LegacyService legacyService;

    @Test
    void readSequence() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        File  file = Paths.get("test.xseq").toFile();


        assertTrue(file.exists(), String.format("file not found : %s", file.getAbsoluteFile()));

        Optional<Sequence> sequence =
                legacyService.readSequence(file.getAbsolutePath())
        ;
        assertNotNull(sequence.get());
        Sequence sq = sequence.get();

        sq.getMedias().add(
                Media.builder()
                        .duration(Duration.ofSeconds(5))
                        .resource(RemoteResource.builder()
                                .withType(Constants.Resource.Type.PHOTO)
                                .withRemote(Constants.Resource.Remote.FTP)
                                .build()
                        )
                        .build()
        );

        String serialization = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sq);

       log.info("sequence serialization: {}" , serialization );

       log.info("from serialization: {}" , mapper.readValue(serialization, Sequence.class) );

    }
}