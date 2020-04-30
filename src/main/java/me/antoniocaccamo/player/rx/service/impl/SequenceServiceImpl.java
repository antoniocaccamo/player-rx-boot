package me.antoniocaccamo.player.rx.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.model.Model;
import me.antoniocaccamo.player.rx.model.preference.LoadedSequence;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import me.antoniocaccamo.player.rx.repository.SequenceRepository;
import me.antoniocaccamo.player.rx.service.MediaService;
import me.antoniocaccamo.player.rx.service.ResourceService;
import me.antoniocaccamo.player.rx.service.SequenceService;
import me.antoniocaccamo.player.rx.service.TranscodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class SequenceServiceImpl implements SequenceService {

    private final Cache<String, LoadedSequence> sequenceCache = CacheBuilder.newBuilder()
            .recordStats()
            .build()
            ;

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

//    @Autowired
//    private SequenceRepository sequenceRepository;

    @Autowired
    private ResourceService resourceService;

//    @Autowired
//    private MediaService mediaService;

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
////        return sequence;
        File file = path.toFile().getAbsoluteFile();
        log.info("saving seguence {} to file : {}", sequence.getName(), file.getAbsolutePath());
        if ( ! file.getParentFile().exists() ) {
            log.info(" crating dirs {} - result {}", file.getParentFile().getAbsolutePath(),
            file.getParentFile().mkdirs());
        }
        if ( ! file.exists() ) {
            log.info("creating file {} - result {}",
            file.getAbsolutePath(),
            file.createNewFile());
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, sequence);
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


        log.warn("sequence by name : {}", sequenceName);
        /*
        optionalSequence = Optional.ofNullable(sequenceCache.getIfPresent(sequenceName).getSequence());
        if ( ! optionalSequence.isPresent() ) {
            optionalSequence = sequenceRepository.findByName(sequenceName);
            optionalSequence.ifPresent( sq-> {
                    sq.getMedias().stream()
                            .forEach(media ->  resourceService.getResourceByHash(media.getResourceHash()).ifPresent(media::setResource));
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
        */
        Optional<LoadedSequence> osl = Optional.ofNullable(sequenceCache.getIfPresent(sequenceName));
        if (osl.isPresent()){
            return Optional.of(osl.get().getSequence());
        }

        return optionalSequence;
    }

    @Override
    public Sequence save(LoadedSequence loadedSequence) throws IOException {
        return save(loadedSequence.getSequence(), loadedSequence.getPath());
    }

    @Override
    public void addLoadedSequence(LoadedSequence loadedSequence){
        sequenceCache.put(loadedSequence.getName(), loadedSequence);
    }

    @Override
    public Collection<LoadedSequence> getLoadedSequences() {
        return sequenceCache.asMap().values();
    }


    @PreDestroy
    public void preDestroy() {
        log.info("{} service destroying", getClass().getSimpleName());
    }
}
