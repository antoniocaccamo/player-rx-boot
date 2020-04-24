package me.antoniocaccamo.player.rx.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.model.Model;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;
import me.antoniocaccamo.player.rx.repository.SequenceRepository;
import me.antoniocaccamo.player.rx.service.MediaService;
import me.antoniocaccamo.player.rx.service.ResourceService;
import me.antoniocaccamo.player.rx.service.SequenceService;
import me.antoniocaccamo.player.rx.service.TranscodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class SequenceServiceImpl implements SequenceService {

    private final Cache<String, Sequence> sequenceCache = CacheBuilder.newBuilder()
            .recordStats()
            .build()
            ;

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private TranscodeService transcodeService;



    @Override
    public  Optional<Sequence>  read( Path path ){
        return read(Model.Location.LOCAL, path);
    }


    @Override
    public Optional<Sequence> read(Model.Location location, Path path) {

        log.info("reading sequence from location {} and path : {} => exists ? : {}", location, path, path.toFile().exists());

        Sequence sequence = null;
        if ( path.toFile().exists() ) {
            try {
                sequence = mapper.readValue(path.toFile(), Sequence.class);
            } catch (IOException e) {
                log.error("error occurred", e);
            }
        }
        return Optional.ofNullable(sequence);

    }

    @Override
    public Sequence save(Sequence sequence, Path path) throws IOException {
//        sequence.getMedias()
//                .stream()
//                .forEach(media -> {
//                    Resource resource = media.getResource();
//                    resourceService.save(resource);
//                    //media.setResource(resource);
//                    mediaService.save(media);
//                });
//        sequenceRepository.save(sequence);
//        sequenceCache.put(sequence.getName(), sequence);
//        return sequence;
        log.info("saving seguence {} to file : {}", sequence, path);
        path.toFile().mkdirs();
        mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), sequence);
        return sequence;
    }

    @Override
    public Optional<Sequence> getSequenceByName(String sequenceName) {
        Optional<Sequence> optionalSequence = Optional.empty();
//        if ( ! sequenceMap.containsKey(sequenceName)) {
//            log.info("loading optionalSequence : {}", sequenceName);
//            sequenceMap.put(sequenceName, sequenceRepository.findByName(sequenceName));
//        }
//        optionalSequence = sequenceMap.get(sequenceName);


        log.warn("optionalSequence by name : {}", sequenceName);
        optionalSequence = Optional.ofNullable(sequenceCache.getIfPresent(sequenceName));
        if ( ! optionalSequence.isPresent() ) {
            optionalSequence = sequenceRepository.findByName(sequenceName);
            optionalSequence.ifPresent( sq-> {
                    sq.getMedias().stream()
                            .forEach(media ->  resourceService.getResourceByHash(media.getResource()).ifPresent(resource -> media.setResource(resource)));
                    sequenceCache.put(sequenceName,sq);
                }
            );

        }
        optionalSequence.ifPresent(sq -> sq.getMedias()
                .stream()
                .filter(media -> media.getResource().isVideo() &&  media.getResource().needsTrancode())
                .forEach(media -> transcodeService.transcode(media.getResource()))

        );
        return optionalSequence;
    }

    @Override
    public Collection<Sequence> getLoadedSequences() {
        return sequenceCache.asMap().values();
    }


    @PreDestroy
    public void preDestroy() {
        log.info("{} service destroying", getClass().getSimpleName());
    }
}
