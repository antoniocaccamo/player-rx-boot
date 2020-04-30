package me.antoniocaccamo.player.rx.service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import me.antoniocaccamo.player.rx.model.preference.LoadedSequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;

/**
 * @author antoniocaccamo on 23/04/2020
 */
@SpringBootTest @Slf4j
public class SequenceServiceTest {

    @Autowired
    private SequenceService sequenceService;

    @Test
    public void testSave() throws IOException {

        Path path = Constants.Sequence.DefaultSequenceNamePath;

        if ( ! Files.exists(path.toAbsolutePath().getParent()))
            Files.createDirectories(path.toAbsolutePath().getParent());

        if ( ! Files.exists(path.toAbsolutePath()) )
            Files.createFile(path.toAbsolutePath());

        log.info("path : {}", path.toAbsolutePath());

        Sequence sqdefault  = Constants.Sequence.DEFAULT_SEQUENCE;

         Sequence sq = sequenceService.save( LoadedSequence.builder()
                 .sequence(sqdefault)
                 .path(path)
                 .build()
         );

        log.info("sequence : {}" , sqdefault);

       // Assertions.assertEquals( sqdefault, sq);
    }

}
