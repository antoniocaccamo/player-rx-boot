package me.antoniocaccamo.player.rx.model.preference;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.model.Model;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Singular;

/**
 * @author antoniocaccamo on 07/02/2020
 */
@Slf4j
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Preference extends Model {

    private String computer;

    private ScreenSize size;

    private ScreenLocation location;

    private String sendAllEmail;

    private String weatherLatlng;

    @Singular
    private Set<LoadedSequence> loadedSequences;

    @Singular
    private List<Screen> screens;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("computer", computer)
                .append("size", size)
                .append("location", location)
                .append("sendAllEmail", sendAllEmail)
                .append("weatherLatlng", weatherLatlng)
                .append("screens", screens)
                .append("loadedSequences", loadedSequences)
                .toString();
    }

    public void addScreen(Screen screen){
        if (screens == null) screens = Collections.EMPTY_LIST;
        log.info( "screen added ? : {}", screens.add(screen));
    }

    public void addLoadedSequence(LoadedSequence loadedSequence) {
        if (loadedSequences == null) loadedSequences = Collections.EMPTY_SET;
        loadedSequences.add(loadedSequence);
    }

    public void removeScreen(Screen screen){
        if ( screens != null) {
            log.info( "screen removed ? : {}", screens.remove(screen));            
        }
    }
}
