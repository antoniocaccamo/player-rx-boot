package me.antoniocaccamo.player.rx.ui.monitor;

import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.ui.MonitorUI;
import org.eclipse.swt.widgets.Composite;

/**
 * @author antoniocaccamo on 19/02/2020
 */
@Slf4j
public class VideoUI extends BrowserUI {

    public VideoUI(MonitorUI monitorUI, Composite wrapped) {
        super(monitorUI, wrapped);
    }

}
