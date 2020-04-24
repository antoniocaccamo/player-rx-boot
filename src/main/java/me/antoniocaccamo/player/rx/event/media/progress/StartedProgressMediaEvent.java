package me.antoniocaccamo.player.rx.event.media.progress;

import me.antoniocaccamo.player.rx.model.sequence.Media;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author antoniocaccamo on 20/02/2020
 */
public class StartedProgressMediaEvent extends MediaEvent {
    public StartedProgressMediaEvent(Media media) {
        super(media);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .toString();
    }
}
