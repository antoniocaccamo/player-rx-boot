package me.antoniocaccamo.player.rx.event.media.progress;

import lombok.Getter;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author antoniocaccamo on 20/02/2020
 */

public class PercentageProgressMediaEvent extends MediaEvent {

    @Getter  private final long actual;
    @Getter  private final long total;

    public PercentageProgressMediaEvent(Media media, long actual, long total) {
        super(MediaEventType.PERCENTAGE_PROGRESS, media);
        this.actual = actual;
        this.total = total;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("actual", actual)
                .append("total" , total)
                .toString();
    }

}
