package me.antoniocaccamo.player.rx.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.jackson.ResourceCollectionWrapprer;
import me.antoniocaccamo.player.rx.model.resource.RemoteResource;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;

/**
 * @author antoniocaccamo on 22/04/2020
 */
@SpringBootTest @Slf4j
public class ResourceServiceTest {

    //@Autowired
    private LegacyService legacyService;

    @Value("${micronaut.application.res-library-file}")
    private File resLibraryFile;

    @Test
    public void showAll() throws IOException {

    }
/*
    @Test
    public void load() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        File  file = Paths.get("test.xseq").toFile();


        assertTrue(file.exists(), String.format("file not found : %s", file.getAbsoluteFile()));

        Optional<Sequence> sequence =
                legacyService.readLeagacySequence(file.getAbsolutePath())
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

        Cache<String, Resource> resourceCache =  CacheBuilder.newBuilder()
                .recordStats()
                .build();

        sq.getMedias().stream().forEach(media -> resourceCache.put(media.getResource().getHash(),media.getResource()));

        ResourceCollectionWrapprer wrapper =  ResourceCollectionWrapprer
                .builder()
                .collection(resourceCache.asMap().values())
                .build();

        mapper.writerWithDefaultPrettyPrinter().writeValue( this.resLibraryFile, wrapper);

        wrapper = mapper.readValue(resLibraryFile, ResourceCollectionWrapprer.class);

        log.info( "deserialized : {}", wrapper );
    }
*/
}
