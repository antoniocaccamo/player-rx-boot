package me.antoniocaccamo.player.rx.event.media.progress;

import me.antoniocaccamo.player.rx.model.sequence.Media;

/**
 * @author antoniocaccamo on 20/02/2020
 */
public class StartedProgressMediaEvent extends MediaEvent {

    public StartedProgressMediaEvent(Media media) {
        super(MediaEventType.STARTED, media);
    }

}
