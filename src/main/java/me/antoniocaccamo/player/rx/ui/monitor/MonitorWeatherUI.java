package me.antoniocaccamo.player.rx.ui.monitor;

import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.ui.ScreenUI;
import org.eclipse.swt.widgets.Composite;

/**
 * @author antoniocaccamo on 19/02/2020
 */
@Slf4j
public class MonitorWeatherUI extends MonitorBrowserUI {
    public MonitorWeatherUI(ScreenUI screenUI, Composite wrapped) {
        super(screenUI, wrapped, ShowEnum.WEATHER);

    }
}
