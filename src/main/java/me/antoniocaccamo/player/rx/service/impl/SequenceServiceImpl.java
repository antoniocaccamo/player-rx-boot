package me.antoniocaccamo.player.rx.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.model.Model.Location;
import me.antoniocaccamo.player.rx.model.preference.LoadedSequence;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;
import me.antoniocaccamo.player.rx.service.ResourceService;
import me.antoniocaccamo.player.rx.service.SequenceService;
import me.antoniocaccamo.player.rx.service.TranscodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

@Service
@Slf4j
public class SequenceServiceImpl implements SequenceService {

    private final Cache<String, LoadedSequence> sequenceCache = CacheBuilder.newBuilder().recordStats().build();

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    // @Autowired
    // private SequenceRepository sequenceRepository;

    @Autowired
    private ResourceService resourceService;

    // @Autowired
    // private MediaService mediaService;

    @Autowired
    private TranscodeService transcodeService;

    @Override
    public Iterable<LoadedSequence> getLoadedSequences() {
        return sequenceCache.asMap().values();
    }

    @Override
    public Optional<LoadedSequence> getLoadedSequenceByName(String sequenceName) {
        return Optional.ofNullable(sequenceCache.getIfPresent(sequenceName));
    }

    @Override
    public Optional<LoadedSequence> loadFromSource(LoadedSequence loadedSequence) {

        Optional<LoadedSequence> ols = null;

        log.info("reading sequence from location {} and path : {} => exists ? : {}", loadedSequence.getPath(), loadedSequence.getPath().toFile().exists());


        if ( loadedSequence.getPath().toFile().exists() ) {
            try {
                Sequence sequence = mapper.readValue(loadedSequence.getPath().toFile(), Sequence.class);
                Observable.fromIterable(sequence.getMedias())
                        .subscribe(media -> {
                            media.setSequence(sequence);
                            resourceService.getResourceByHash(media.getResourceHash()).ifPresent(resource -> media.setResource(resource));
                        })
                ;
                loadedSequence.setSequence(sequence);
                ols = add(loadedSequence);
            } catch (IOException e) {
                log.error("error occurred", e);
                ols = Optional.empty();
            }
        }
        return ols;
    }

    @Override
    public Optional<LoadedSequence> add(LoadedSequence loadedSequence) {
        log.info("adding sequence to cache : {}", loadedSequence);
        sequenceCache.put(loadedSequence.getName(), loadedSequence);
        return Optional.of(sequenceCache.getIfPresent(loadedSequence.getName()));
    }

    @Override
    public LoadedSequence save(LoadedSequence loadedSequence) throws IOException {
        save(loadedSequence.getSequence(), loadedSequence.getPath());
        return loadedSequence;
    }

    @Override
    public Optional<Sequence> read(Path path) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Sequence> read(Location location, Path path) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LoadedSequence save(Sequence sequence, Path path) throws IOException {
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
        return null;
    }



/*

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
    public Optional<LoadedSequence> getSequenceByName(String sequenceName) {
        Optional<LoadedSequence> optionalSequence = Optional.empty();
//        if ( ! sequenceMap.containsKey(sequenceName)) {
//            log.info("loading optionalSequence : {}", sequenceName);
//            sequenceMap.put(sequenceName, sequenceRepository.findByName(sequenceName));
//        }
//        optionalSequence = sequenceMap.get(sequenceName);


        log.warn("sequence by name : {}", sequenceName);
        
//       optionalSequence = Optional.ofNullable(sequenceCache.getIfPresent(sequenceName).getSequence());
//       if ( ! optionalSequence.isPresent() ) {
//           optionalSequence = sequenceRepository.findByName(sequenceName);
//           optionalSequence.ifPresent( sq-> {
//                   sq.getMedias().stream()
//                           .forEach(media ->  resourceService.getResourceByHash(media.getResourceHash()).ifPresent(media::setResource));
//                   sequenceCache.put(sequenceName,sq);
//               }
//           );
//
//       }
//       optionalSequence.ifPresent(sq -> sq.getMedias()
//               .stream()
//               .filter(media -> media.getResource().isVideo() &&  media.getResource().needsTrancode())
//               .forEach(media -> transcodeService.transcode(media.getResource()))
//
//       );
//       return optionalSequence;
//       
        Optional<LoadedSequence> osl = Optional.ofNullable(sequenceCache.getIfPresent(sequenceName));
        if (osl.isPresent()){
            return Optional.of(osl.get().getSequence());
        }

        return optionalSequence;
        
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
    public Optional<LoadedSequence> load(LoadedSequence loadedSequence) throws IOException {
        return sequenceCache.getIfPresent(loadedSequence.getName());
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

    
*/
   
}
