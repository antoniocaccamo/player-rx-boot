package me.antoniocaccamo.player.rx.model.sequence;

import java.time.LocalDateTime;

/**
 * @author antoniocaccamo on 03/04/2020
 */
public interface Playable {

    void prepareForPlay();

    boolean isPlayable(LocalDateTime now);

//  boolean isAvailable();
}
