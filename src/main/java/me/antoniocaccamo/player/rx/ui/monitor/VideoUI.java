package me.antoniocaccamo.player.rx.ui.monitor;

import com.diffplug.common.swt.SwtExec;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.Application;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import me.antoniocaccamo.player.rx.ui.MonitorUI;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.widgets.Composite;

/**
 * @author antoniocaccamo on 19/02/2020
 */
@Slf4j
public class VideoUI extends BrowserUI {

    public VideoUI(MonitorUI monitorUI, Composite wrapped) {
        super(monitorUI, wrapped, ShowEnum.VIDEO);
    }


    @Override
    public void setCurrent(Media media) {

        super.setCurrent(media);
    }
}
