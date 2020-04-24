package me.antoniocaccamo.player.rx.event.media.progress;

import me.antoniocaccamo.player.rx.model.sequence.Media;

/**
 * @author antoniocaccamo on 20/02/2020
 */
public abstract class MediaEvent {

    private final Media media;

    public MediaEvent(Media media) {
        this.media = media;
    }
}
