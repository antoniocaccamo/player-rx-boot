package me.antoniocaccamo.player.rx.service;

import io.reactivex.functions.Consumer;
import me.antoniocaccamo.player.rx.model.resource.Resource;

/**
 * @author antoniocaccamo on 19/02/2020
 */
public interface TranscodeService {

    void transcode(Resource resource);

}
