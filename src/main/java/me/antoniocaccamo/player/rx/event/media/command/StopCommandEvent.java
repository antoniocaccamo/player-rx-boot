package me.antoniocaccamo.player.rx.event.media.command;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author antoniocaccamo on 20/02/2020
 */
public class StopCommandEvent extends CommandEvent {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .toString();
    }
}
