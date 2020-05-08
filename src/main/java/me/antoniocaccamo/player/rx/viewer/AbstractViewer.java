package me.antoniocaccamo.player.rx.viewer;

import lombok.Getter;
import lombok.Setter;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import me.antoniocaccamo.player.rx.ui.monitor.AbstractMonitorUI;

/**
 * @author antoniocaccamo on 19/02/2020
 */
@Getter @Setter
public abstract class AbstractViewer {


    protected final AbstractMonitorUI ui;

    protected Media current;


    public AbstractViewer(AbstractMonitorUI ui) {
        this.ui = ui;
    }


    abstract void play();

    abstract void pause();

    abstract void stop();

    abstract void next();

}
