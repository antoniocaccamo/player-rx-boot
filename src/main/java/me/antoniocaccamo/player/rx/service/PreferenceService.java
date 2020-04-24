package me.antoniocaccamo.player.rx.service;

import me.antoniocaccamo.player.rx.model.preference.PreferenceModel;

import java.io.IOException;

/**
 * @author antoniocaccamo on 07/02/2020
 */
public interface PreferenceService {

    PreferenceModel read() ;

    void save() throws IOException;

}
