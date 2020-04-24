package me.antoniocaccamo.player.rx.ui.monitor;

import com.diffplug.common.swt.Layouts;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.Application;
import me.antoniocaccamo.player.rx.ui.MonitorUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * @author antoniocaccamo on 19/02/2020
 */
@Slf4j
public class PhotoUI extends BrowserUI {

    public PhotoUI(MonitorUI monitorUI, Composite wrapped) {
        super(monitorUI, wrapped);

        Label label = new Label(this, SWT.NONE);
        label.setText(getClass().getSimpleName());
        Layouts.setGridData(label).grabAll();
        final String url = String.format("http://localhost:%s/html/ui/photo/", Application.SERVER_PORT);
        log.info("getIndex() [{}] - url  : {}", this.getMonitorUI().getIndex(), url);
        browser.setUrl( url );
    }

    @Override
    public void play() {
//        if ( getCurrent().isAvailable() ) {
//            final Path path = getCurrent().getResource().getLocalPath();
//            log.info("getIndex() [{}] - show photo  : {}", this.getMonitorUI().getIndex(), path);
//            SwtExec.async().guardOn(browser)
//                    .execute(() -> {try {
//                                log.info("getIndex() [{}] - execute {} - result : {}"    ,
//                                        getMonitorUI().getIndex(),
//                                        String.format("load(%s?%s)", path, startInMillis),
//                                        browser.execute(String.format("load(%s?%s)", path, startInMillis))
//                                );
//                            } catch (Exception e) {
//                                log.error("error occurred", e);
//
//                            }}
//                    );
//        }
        super.play();
    }
}
