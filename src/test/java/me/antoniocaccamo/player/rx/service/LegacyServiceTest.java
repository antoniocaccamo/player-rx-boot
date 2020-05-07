package me.antoniocaccamo.player.rx.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.preference.LoadedSequence;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * @author antoniocaccamo on 09/04/2020
 */

@SpringBootTest @Slf4j
class LegacyServiceTest {

    @Autowired
    private LegacyService legacyService;

    @Autowired
    private SequenceService sequenceService;

    @Test
    void readSequence() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        File  file = Paths.get("test.xseq").toFile();


        assertTrue(file.exists(), String.format("file not found : %s", file.getAbsoluteFile()));

        Optional<Sequence> sequence =
                legacyService.readLeagacySequence(file.getAbsolutePath())
                ;
        assertNotNull(sequence.get());
        Sequence sq = sequence.get();



        LoadedSequence loadedSequence  = LoadedSequence.builder()
                .sequence(sq)
                .path(Paths.get(String.format("%s%s", sq.getName(), Constants.Sequence.Extension)))
                .build();


        sequenceService.save(loadedSequence);

        sq = sequenceService.read(loadedSequence.getPath()).orElseThrow(RuntimeException::new);
        log.info("sequence serialization: {}" , mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sq) );

    }
}