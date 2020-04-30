package me.antoniocaccamo.player.rx.helper;

import java.nio.file.Files;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;
import me.antoniocaccamo.player.rx.service.MediaService;
import me.antoniocaccamo.player.rx.service.ResourceService;
import me.antoniocaccamo.player.rx.service.SequenceService;

/**
 * @author antoniocaccamo on 10/03/2020
 */
@Component
@Slf4j
public class InitHelper {

    //@Autowired
    //private MediaService mediaService;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private ResourceService resourceService;

 
    public void getDefaultSquence() {

        Observable.fromIterable( resourceService.getResources() )
            .subscribe( resource -> log.info("resource {}", resource));
    }

/*
    public Sequence  getDefaultSquence(){

        Sequence sequence = null;

        sequence = sequenceService.getSequenceByName(Constants.Sequence.DefaultSequenceName)
                .orElseGet(() -> Constants.Sequence.DEFAULT_SEQUENCE )
        ;

        return sequence;
    }

    
    @Transactional
    private Sequence createDefaultSequence() {

        log.warn("creating default sequence ..");

        Sequence sequence = null;
        try {
        sequence = Constants.Sequence.DEFAULT_SEQUENCE;
        sequence.getMedias()
                .stream()
                .forEach(media -> {
                    Resource resource = media.getResource();
                    resourceService.save(resource);
                    //media.setResource(resource);
                    mediaService.save(media);
                });
        sequenceService.save(sequence, Files.createTempFile("xxx", ".syml"));
        ObjectMapper mapper = new ObjectMapper();
        log.info("default sequence saved : {} " , sequence);
		} catch (Exception e) {
            log.error("error occurred", e);
        }

        return sequence;

    }

*/

}
