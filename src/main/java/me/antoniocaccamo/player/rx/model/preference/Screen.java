package me.antoniocaccamo.player.rx.model.preference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.Model;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalTime;

/**
 * @author antoniocaccamo on 07/02/2020
 */
@Slf4j @Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Screen extends Model {

    private Constants.Screen.DefaultEnum defaultScreen;

    private ScreenSize size;

    private ScreenLocation location;

    private String name;

    private String sequence;

    private LocalTime from;

    private LocalTime to;

    private Constants.TimingEnum timing;



    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("defaultScreen", defaultScreen)
                .append("size", size)
                .append("location", location)
                .append("name", name)
                .append("sequence", sequence)
                .append("timing", timing)
                .append("from", from)
                .append("to", to)

                .toString();
    }

    @JsonIgnore
    public boolean isTimed(){
        return  Constants.TimingEnum.TIMED.equals(timing) && ( from != null &&  to != null );
    }
}
