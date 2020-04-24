package me.antoniocaccamo.player.rx.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.model.jackson.ResourceCollectionWrapprer;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import me.antoniocaccamo.player.rx.repository.ResourceRepository;
import me.antoniocaccamo.player.rx.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    @Value("${spring.application.res-library-file}")
    private File resLibraryFile;

    @Autowired
    private ResourceRepository resourceRepository;

    private Map<String, Resource> resourceMap;

    private Cache<String, Resource> resourceCache = null;

    private ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    @PostConstruct
    public void postConstruct() throws FileNotFoundException {

        /*
        Path path = Paths.get(resLibraryFile);
        log.info("loading resource library : file {} exists ? : {}" , path.toAbsolutePath(), path.toFile().exists());
        // @TODO loading resource library
        if ( path.toFile().exists() ) {
            log.warn("load resource library...");

          //  Iterable<Object> rss = new Yaml().loadAll( new FileInputStream(path.toFile()));
            // rss.forEach( rs ->          log.warn("rs : {}", rs));
        }
        */


        resourceCache =  CacheBuilder.newBuilder()
                .recordStats()
                .build();

        Observable.fromIterable(resourceRepository.findAll())
                .subscribe( resource -> resourceCache.put(resource.getHash(), resource));

    }

    public Optional<Resource> getResourceByHash(Resource resource) {
        Optional<Resource> optionalResource = Optional.ofNullable(resourceCache.getIfPresent(resource.getHash() ));
        return optionalResource;

    }

    @Override
    public void save(Resource resource) {
        resourceRepository.save(resource);
        resourceCache.put(resource.getHash(), resource);
    }

    public Map getResourceMap() {
        return resourceMap;
    }

    @Override
    public Iterable<Resource> getResources() {
        return resourceRepository.findAll();
    }

    @PreDestroy
    public void preDestroy() {

        log.info("saving resources file : {}", resLibraryFile.getAbsolutePath());
        try  {
            ResourceCollectionWrapprer wrapper = new ResourceCollectionWrapprer();
            wrapper.setCollection(resourceCache.asMap().values());
            mapper.writerWithDefaultPrettyPrinter().writeValue( this.resLibraryFile, wrapper);
        } catch (Exception e) {
            log.error("error occurred", e);
        }
    }
}
