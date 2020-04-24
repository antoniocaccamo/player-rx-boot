package me.antoniocaccamo.player.rx.model.preference;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.model.Model;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.UUID;

/**
 * @author antoniocaccamo on 07/02/2020
 */
@Slf4j
@Getter @Setter
public class PreferenceModel extends Model {

    private String uuid = UUID.randomUUID().toString();

    private String computer;

    private ScreenSize size;

    private ScreenLocation location;

    private String sendAllEmail;

    private String weatherLatlng;

    private List<Screen> screens;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("uuid", uuid)
                .append("computer", computer)
                .append("size", size)
                .append("location", location)
                .append("sendAllEmail", sendAllEmail)
                .append("weatherLatlng", weatherLatlng)
                .append("screens", screens)
                .toString();
    }
}
