package me.antoniocaccamo.player.rx.ui;

import com.diffplug.common.swt.ColorPool;
import com.diffplug.common.swt.Layouts;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import me.antoniocaccamo.player.rx.task.ShowMediaTask;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Timer;

/**
 * @author antoniocaccamo on 19/02/2020
 */
@Slf4j
public abstract class AbstractUI extends Composite {

    private final Optional<MonitorUI> monitorUI;
    private ShowMediaTask durationTask;

    public AbstractUI(MonitorUI monitorUI, Composite parent) {
        super(parent, SWT.NONE);
        Layouts.setGrid(this).margin(0).spacing(0).numColumns(1).columnsEqualWidth(true);
        Layouts.setGridData(this).grabAll();
        setBackground(ColorPool.forWidget(this).getSystemColor(SWT.COLOR_BLACK));

        this.monitorUI = Optional.ofNullable(monitorUI);
    }

    protected long startInMillis;

    protected LocalDateTime pausedAt;

    protected Timer durationTimer = new Timer();
    protected Timer alphaTimer = new Timer();

    protected Media current;

    protected boolean alphaEnabled;

    protected Composite composite = null;
    private Duration shown;

    public void play()  {
        if ( ! current.isAvailable() ) {
            next();
        }
        log.debug("playyyyyyyy");
        shown = Duration.ZERO;
        this.startInMillis = LocalDateTime.now().getNano();
        durationTask = new ShowMediaTask(this, current.getDuration() );
        durationTimer.schedule( durationTask, 0, 200 );
//		if ( playerMaster.getScreenManager().getPlayerSetting().isFade()  ) {
//			logger.debug("init alpha timer task");
//			alphaTask = new IPlayerAlphaTimerTask(this);
//			alphaTimer.schedule( alphaTask , 0 , 30);
//			alphaEnabled = true;
//		}

    }

    public void next() {
        durationTimer.purge();
        monitorUI.ifPresent( ui -> ui.next());
    }

    public void stop() {
        if ( durationTask != null )
            this.durationTask.cancel();
        durationTimer.purge();
    }

    public Media getCurrent(){
        return current;
    }

    public void setCurrent(Media media) {
        this.current = media ;
    }

    public void pause() {
        this.shown = durationTask.getShowing();
        durationTask.cancel();
        durationTimer.purge();
    }

    public void resume() {
        durationTask = new ShowMediaTask(this, shown, current.getDuration());
        durationTimer.schedule(durationTask, 0, 200);
    }

    public void errorOnPlay(Throwable throwable) {
        monitorUI.ifPresent( ui -> ui.errorOnPlay(throwable));
    }

    public  void updatePercentageProgess( long actual, long total) {
        log.debug("getIndex() [{}] - progress bar : actual {}  total {}", getMonitorUI().isPresent() ? getMonitorUI().get().getIndex() : Constants.Screen.COLOR_SEPARATOR, actual, total );
        monitorUI.ifPresent( ui -> ui.updatePercentageProgess( actual, total  ));
    }

    public Optional<MonitorUI> getMonitorUI() {
        return monitorUI;
    }
}
