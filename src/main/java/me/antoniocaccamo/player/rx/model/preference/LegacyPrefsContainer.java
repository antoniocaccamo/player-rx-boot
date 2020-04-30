package me.antoniocaccamo.player.rx.model.preference;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author antoniocaccamo on 05/03/2020
 */
@Getter @Setter
public class LegacyPrefsContainer {

    @JsonProperty("app")
    private Preference preference;

}
