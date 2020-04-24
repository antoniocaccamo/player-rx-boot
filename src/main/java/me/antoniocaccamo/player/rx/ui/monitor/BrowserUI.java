package me.antoniocaccamo.player.rx.ui.monitor;

import com.diffplug.common.swt.ColorPool;
import com.diffplug.common.swt.Layouts;
import com.diffplug.common.swt.SwtExec;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import me.antoniocaccamo.player.rx.ui.AbstractUI;
import me.antoniocaccamo.player.rx.ui.MonitorUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;

/**
 * @author antoniocaccamo on 19/02/2020
 */
@Slf4j
public class BrowserUI extends AbstractUI {

    @Getter
    protected final Browser browser;

    public BrowserUI(MonitorUI monitorUI, Composite wrapped) {
        super(monitorUI, wrapped);
        setBackground(ColorPool.forWidget(this).getSystemColor(SWT.COLOR_BLACK));

        browser = new Browser(this, SWT.NONE);

        Layouts.setGridData(browser)
                .grabAll()
        ;
    }

    @Override
    public void play() {
//        if ( getCurrent().isAvailable() ) {
//            SwtExec.async().guardOn(browser)
//                    .execute( () ->
//                            log.info("getIndex() [{}] - execute {} - result : {}"    ,
//                                    getMonitorUI().getIndex(),
//                                    String.format("browser.setUrl('%s')", getCurrent().getPath()),
//                                    browser.setUrl(getCurrent().getPath())
//                            )
//                    );
//        }
        super.play();
    }
}
