package me.antoniocaccamo.player.rx.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.jackson.ResourceCollectionWrapprer;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import me.antoniocaccamo.player.rx.service.ResourceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    @Value("${spring.application.res-library-file}")
    private File resLibraryFile;

//    @Autowired
//    private ResourceRepository resourceRepository;

    private Map<String, Resource> resourceMap;

    private Cache<String, Resource> resourceCache = null;

    private ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    @PostConstruct
    public void postConstruct() throws IOException {

        log.info("loading resource library : file {} exists ? : {}" , resLibraryFile.getAbsolutePath(), resLibraryFile.exists());
        ResourceCollectionWrapprer wrapper = null;
        if ( ! resLibraryFile.exists() ) {
            log.warn("default resources collection");
            wrapper = Constants.Resource.DefaultResourceCollectionWrapprer;
        } else {
            wrapper = mapper.readValue(resLibraryFile, ResourceCollectionWrapprer.class);
        }
        resourceCache =  CacheBuilder.newBuilder()
                .recordStats()
                .build();

        wrapper.getCollection()
                .stream()
                .forEach(resource -> resourceCache.put(resource.getHash(), resource) )
        ;

        Constants.Sequence.DEFAULT_SEQUENCE.getMedias().stream()
                .forEach( media -> media.setResource(getResourceByHash(media.getResourceHash()).orElse(save(media.getResource()))));

        log.info("resourceCache stats : " + resourceCache.stats());

    }

    public Optional<Resource> getResourceByHash(String resourceHash) {
        Resource resource = resourceCache.getIfPresent(resourceHash);
        Optional<Resource> or = Optional.ofNullable(resource);
        log.info("resource by hash : {} - isPresent: {}" , resourceHash, or.isPresent());
        return or;
    }

    @Override
    public Resource save(Resource resource) {
   //   resourceRepository.save(resource);
        resourceCache.put(resource.getHash(), resource);
        log.info("resourceCache stats : " + resourceCache.stats());
        return resource;
    }

    public Map getResourceMap() {
        return resourceMap;
    }

    @Override
    public Iterable<Resource> getResources() {
        return // resourceRepository.findAll()
                resourceCache.asMap().values()
                ;

    }

    @PreDestroy
    public void preDestroy() {

        log.info("saving resources file : {}", resLibraryFile.getAbsolutePath());
        try  {
            ResourceCollectionWrapprer wrapper = ResourceCollectionWrapprer
                    .builder()
                    .collection(resourceCache.asMap().values())
                    .build();
            mapper.writerWithDefaultPrettyPrinter().writeValue( this.resLibraryFile, wrapper);
        } catch (Exception e) {
            log.error("error occurred", e);
        }
    }
}
