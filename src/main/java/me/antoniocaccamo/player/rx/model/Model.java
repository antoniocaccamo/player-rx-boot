package me.antoniocaccamo.player.rx.model;


/**
 * @author antoniocaccamo on 07/02/2020
 */
public abstract class Model {



    public enum Location {
        LOCAL,
        REMOTE
    }

    public enum Type {
        VIDEO,
        PHOTO
    }
}
