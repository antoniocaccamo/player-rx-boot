package me.antoniocaccamo.player.rx.event.media.progress;

import lombok.Getter;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
 * @author antoniocaccamo on 20/02/2020
 */
public abstract class MediaEvent {

    private final Media media;

    @Getter
    protected final MediaEventType type;

    @Getter
    protected final LocalDateTime when;

    public MediaEvent(MediaEventType type, Media media) {
        this.type = type;
        this.media = media;
        when = LocalDateTime.now();
    }


    public enum MediaEventType {
        STARTED,
        ENDED,
        ERROR,
        PERCENTAGE_PROGRESS
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("media", media)
                .append("type", type)
                .append("when", when)
                .toString();
    }
}
