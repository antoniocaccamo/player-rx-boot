package me.antoniocaccamo.player.rx.service.impl;

import me.antoniocaccamo.player.rx.model.sequence.Media;
import me.antoniocaccamo.player.rx.repository.MediaRepository;
import me.antoniocaccamo.player.rx.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author antoniocaccamo on 22/04/2020
 */
@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;


    @Override
    public void save(Media media) {
        mediaRepository.save(media);
    }
}
