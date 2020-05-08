package me.antoniocaccamo.player.rx.event.media.command;


import lombok.Getter;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author antoniocaccamo on 20/02/2020
 */
public class PauseCommandEvent extends CommandEvent {

    @Getter
    private final Media media;

    public PauseCommandEvent(Media media) {
        super(CommandEventType.PAUSE);
        this.media = media;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("media", media)
                .toString();
    }
}
