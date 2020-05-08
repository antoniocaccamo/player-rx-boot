package me.antoniocaccamo.player.rx.event.media.progress;

import lombok.Getter;
import me.antoniocaccamo.player.rx.model.sequence.Media;

/**
 * @author antoniocaccamo on 20/02/2020
 */
public class ErrorProgressMediaEvent extends MediaEvent {

    @Getter
    private final Throwable throwable;

    public ErrorProgressMediaEvent(Media media, Throwable throwable) {
        super(MediaEventType.ERROR, media);
        this.throwable = throwable;
    }

}
