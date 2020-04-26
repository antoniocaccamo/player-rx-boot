package me.antoniocaccamo.player.rx.repository;

import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//import lombok.extern.slf4j.Slf4j;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.Model;
import me.antoniocaccamo.player.rx.model.resource.LocalResource;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;
import me.antoniocaccamo.player.rx.service.ResourceService;

@SpringBootTest
//@Slf4j
public class RepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryTest.class);

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private ResourceService resourceService;


    @Test @Order(1)
    public void saveAndReadResource() {

        LocalResource blackResource = LocalResource.builder()
                .withType(Constants.Resource.Type.BLACK)
                .build()
        ;

        LocalResource hiddenResource = LocalResource.builder()
                .withType(Constants.Resource.Type.HIDDEN)
                .build()
        ;

        LocalResource watchResource = LocalResource.builder()
                .withType(Constants.Resource.Type.WATCH)
                .build()
        ;

        LocalResource weatherResource = LocalResource.builder()
                .withType(Constants.Resource.Type.WEATHER)
                .build()
        ;

        LocalResource logo = LocalResource.builder()
                .withPath("images/logo.jpg")
                .withType(Constants.Resource.Type.PHOTO)
                .build()
        ;

        Observable.just(blackResource, hiddenResource, watchResource, weatherResource, logo)
                .subscribe(new Observer<LocalResource>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logger.info("begin saving localResources");
                    }

                    @Override
                    public void onNext(LocalResource localResource) {
                        logger.info("saving localResource : {}", localResource);
                        LocalResource lr = resourceRepository.save(localResource);
                        logger.info("saved localResource : {}", lr);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.error("error occurred", e);
                    }

                    @Override
                    public void onComplete() {
                        logger.info("saved  localResources");
                    }
                });

        resourceRepository.findAll()
                .forEach(resource -> logger.info("resource read  : {}", resource));


        Resource resource;

        resource  = resourceRepository.findByType(Constants.Resource.Type.PHOTO);

        Media photohMedia = Media.builder()
                .duration(Duration.ofSeconds(5))
                .resource(resource)
                .build();

        resource  = resourceRepository.findByType(Constants.Resource.Type.WATCH);

        Media watchMedia = Media.builder()
                .duration(Duration.ofSeconds(5))
                .resource(resource)
                .build();

        resource = resourceRepository.findByType(Constants.Resource.Type.WEATHER);
        Media weatherMedia = Media.builder()
                .duration(Duration.ofSeconds(10))
                .resource(resource)
                .build();

        Observable.just(photohMedia, watchMedia, weatherMedia)
                .subscribe(media -> logger.info("media saved : {}", mediaRepository.save(media)));

        mediaRepository.findAll()
                .forEach( media -> logger.info("media read : {}", media))
        ;


        Sequence sequence = Sequence.builder()
                .name("test sequence")
                .location(Model.Location.LOCAL)
                .medias(Arrays.asList(photohMedia, watchMedia, weatherMedia))
                .build();

        logger.info("sequence saved : {}", sequenceRepository.save(sequence));

    }
}
