package me.antoniocaccamo.player.rx.event.media.progress;

import me.antoniocaccamo.player.rx.model.sequence.Media;

/**
 * @author antoniocaccamo on 20/02/2020
 */
public class EndedProgressMediaEvent extends MediaEvent {

    public EndedProgressMediaEvent(Media media) {
        super(MediaEventType.ENDED, media);

    }

}
