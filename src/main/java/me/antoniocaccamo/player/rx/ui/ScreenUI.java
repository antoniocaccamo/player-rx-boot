package me.antoniocaccamo.player.rx.ui;

import com.diffplug.common.swt.CoatMux;
import com.diffplug.common.swt.Layouts;
import com.diffplug.common.swt.SwtExec;
import io.reactivex.subjects.PublishSubject;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.event.media.command.*;
import me.antoniocaccamo.player.rx.event.media.progress.*;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import me.antoniocaccamo.player.rx.ui.monitor.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import java.util.HashMap;
import java.util.Map;

/**
 * @author antoniocaccamo on 20/02/2020
 */
@Slf4j
public class ScreenUI extends CoatMux {

    private final PublishSubject<CommandEvent> commandEventSubject;
    private final PublishSubject<MediaEvent>   mediaEventSubject;

    private final Map<Constants.Resource.Type, CoatMux.Layer<AbstractMonitorUI> > layerMap = new HashMap<>();
    private final int index;

    private  CoatMux.Layer<AbstractMonitorUI> currenLayer;

    public ScreenUI(Composite wrapped, int index, PublishSubject<CommandEvent> commandEventSubject, PublishSubject<MediaEvent> mediaEventSubject) {
        super(wrapped, SWT.NONE);
        this.index = index;
        log.info("monitor # {}", getIndex() );
        this.commandEventSubject = commandEventSubject;
        this.mediaEventSubject   = mediaEventSubject;
        this.commandEventSubject
                .observeOn(  SwtExec.async().getRxExecutor().scheduler())
                .subscribe(this::manageCommandEvent);
        createSubMonitor();

    }

    public int getIndex() {
        return index;
    }

    private void createSubMonitor() {
        // Constants.Resource.Type.BLACK
//        layerMap.putIfAbsent(Constants.Resource.Type.BLACK, addCoat( composite -> {
//            Layouts.setGrid(composite)
//                    .numColumns(1)
//                    .columnsEqualWidth(true)
//                    .horizontalSpacing(0)
//                    .verticalSpacing(0)
//                    .spacing(0)
//                    .margin(0)
//            ;
//            return new BlackUI(this, composite);
//
//        }));

        // Constants.Resource.Type.BLACK
        layerMap.putIfAbsent(Constants.Resource.Type.BROWSER, addCoat( composite -> {
            Layouts.setGrid(composite)
                    .numColumns(1)
                    .columnsEqualWidth(true)
                    .horizontalSpacing(0)
                    .verticalSpacing(0)
                    .spacing(0)
                    .margin(0)
            ;
            return new MonitorBrowserUI(this, composite);

        }));

        // Constants.Resource.Type.WATCH
//        layerMap.putIfAbsent(Constants.Resource.Type.WATCH, addCoat( composite -> {
//            Layouts.setGrid(composite)
//                    .numColumns(1)
//                    .columnsEqualWidth(true)
//                    .horizontalSpacing(0)
//                    .verticalSpacing(0)
//                    .spacing(0)
//                    .margin(0)
//            ;
//            return new WatchUI(this, composite);
//
//        }));

        // Constants.Resource.Type.WEATHER
//        layerMap.putIfAbsent(Constants.Resource.Type.WEATHER, addCoat( composite -> {
//            Layouts.setGrid(composite)
//                    .numColumns(1)
//                    .columnsEqualWidth(true)
//                    .horizontalSpacing(0)
//                    .verticalSpacing(0)
//                    .spacing(0)
//                    .margin(0)
//            ;
//            return new WeatherUI(this, composite);
//        }));

        // Constants.Resource.Type.PHOTO
//        layerMap.putIfAbsent(Constants.Resource.Type.PHOTO, addCoat( composite -> {
//            Layouts.setGrid(composite)
//                    .numColumns(1)
//                    .columnsEqualWidth(true)
//                    .horizontalSpacing(0)
//                    .verticalSpacing(0)
//                    .spacing(0)
//                    .margin(0)
//            ;
//            return new PhotoUI(this, composite);
//        }));

        // Constants.Resource.Type.VIDEO
//        layerMap.putIfAbsent(Constants.Resource.Type.VIDEO, addCoat( composite -> {
//            Layouts.setGrid(composite)
//                    .numColumns(1)
//                    .columnsEqualWidth(true)
//                    .horizontalSpacing(0)
//                    .verticalSpacing(0)
//                    .spacing(0)
//                    .margin(0)
//            ;
//            return new VideoUI(this, composite);
//        }));

    }

    private void manageCommandEvent(CommandEvent evt) throws InterruptedException {
        log.debug("getIndex() [{}] - event received : {}", getIndex(), evt);

        switch (evt.getType()) {

            case START:
            case DEATIVATE:
                break;

            case PLAY:
                PlayCommandEvent playCommandEvent = (PlayCommandEvent) evt;
                play(  playCommandEvent.getMedia() );
                break;

            case PAUSE:
                pause();
                break;

            case RESUME:
                resume();
                break;

            case STOP:
                stop();
        }

    }



    protected void play( Media media ) {
        log.info("getIndex() [{}] - playing media => {}  resource: {}", getIndex(), media.getDuration(),  media.getResource());
        // currenLayer  = this.layerMap.get(  media.getResource().getType() );
        currenLayer = currenLayer  == null ? this.layerMap.get(  Constants.Resource.Type.BROWSER ) : currenLayer;
        currenLayer.getHandle().setCurrent(media);
        currenLayer.getHandle().play();
        currenLayer.bringToTop();
        //log.debug("getIndex() [{}] - showing : {}", getIndex(), currenLayer.getHandle().getClass().getSimpleName());
        mediaEventSubject.onNext( new StartedProgressMediaEvent(media));
    }

    protected void pause() {
        currenLayer.getHandle().pause();
    }

    protected void resume() {
        currenLayer.getHandle().resume();
    }


    public void errorOnPlay(Throwable throwable){
        this.mediaEventSubject.onNext(
                new ErrorProgressMediaEvent(currenLayer.getHandle().getCurrent(), throwable)
        );
    }

    public void next() {
        this.mediaEventSubject.onNext(
                new EndedProgressMediaEvent(currenLayer.getHandle().getCurrent())
        );
    }

    protected void stop() {
        currenLayer.getHandle().stop();
    }

    public void updatePercentageProgess(long actual, long total){
        mediaEventSubject
                .onNext( new PercentageProgressMediaEvent(currenLayer.getHandle().getCurrent(), actual, total));
    }

    @Override
    public void dispose() {
        stop();
        log.info("getIndex() [{}] - dispose on monitor", getIndex());
    }
}
