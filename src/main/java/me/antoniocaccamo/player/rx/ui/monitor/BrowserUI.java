package me.antoniocaccamo.player.rx.ui.monitor;

import com.diffplug.common.swt.ColorPool;
import com.diffplug.common.swt.Layouts;
import com.diffplug.common.swt.SwtExec;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.Application;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import me.antoniocaccamo.player.rx.ui.AbstractUI;
import me.antoniocaccamo.player.rx.ui.MonitorUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.widgets.Composite;

/**
 * @author antoniocaccamo on 19/02/2020
 */
@Slf4j
public class BrowserUI extends AbstractUI {

    enum ShowEnum {
        BROWSER("browser"),
        PHOTO ("photo")   ,
        VIDEO("video")    ,
        WEATHER("weather");

        ;
        private final  String show;

        ShowEnum(String show) {
            this.show = show;
        }

        @Override
        public String toString() {
            return show;
        }
    }

    @Getter
    protected final Browser browser;

    protected final ShowEnum show;

    public BrowserUI(MonitorUI monitorUI, Composite wrapped){
        this(monitorUI, wrapped, ShowEnum.BROWSER);
    }

    public BrowserUI(MonitorUI monitorUI, Composite wrapped, ShowEnum show) {
        super(monitorUI, wrapped);
        setBackground(ColorPool.forWidget(this).getSystemColor(SWT.COLOR_BLACK));

        browser = new Browser(this, SWT.NONE);
        this.show = show;

        Layouts.setGridData(browser)
                .grabAll()
        ;

        // final String url = String.format("http://localhost:%s/html/ui/%s/index.html", Application.SERVER_PORT, show);
        final String url = String.format("http://localhost:%s/html/ui/index.html", Application.SERVER_PORT);
        log.info("getIndex() [{}] - url  : {}", getMonitorUI().getIndex(), url);
        browser.setUrl( url );
//        browser.addProgressListener(
//                new ProgressAdapter() {
//                    @Override
//                    public void completed(ProgressEvent event) {
//                        final String execute = String.format("data.ui = '%s'", show);
//                        SwtExec.async().guardOn(browser).execute(
//                                () -> log.info("browser.execute({}) : {}", execute, browser.execute(execute))
//                        );
//                    }
//                }
//        );
    }



    @Override
    public void setCurrent(Media media) {
        ShowEnum ui = ShowEnum.BROWSER;
        switch (media.getResource().getType()){
            case WEATHER:
                ui = ShowEnum.WEATHER;
                break;
            case VIDEO:
                ui = ShowEnum.VIDEO;
                break;
            case PHOTO:
                ui  = ShowEnum.PHOTO;
        }
        final String execute = String.format("data.ui = '%s'", ui);
        SwtExec.async().guardOn(browser).execute(
                () -> log.info("browser.execute({}) : {}", execute, browser.execute(execute))
        );
        super.setCurrent(media);
    }
}
