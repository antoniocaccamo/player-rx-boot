package me.antoniocaccamo.player.rx.service;

import me.antoniocaccamo.player.rx.model.preference.Preference;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;

import java.io.IOException;
import java.util.Optional;

/**
 * @author antoniocaccamo on 07/04/2020
 */
public interface LegacyService {

    Preference loadLegacyPreference() throws IOException;


    Optional<Sequence> readLeagacySequence(String path);

}

