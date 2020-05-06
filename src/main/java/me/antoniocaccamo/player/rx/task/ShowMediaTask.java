package me.antoniocaccamo.player.rx.task;

import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.ui.AbstractUI;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimerTask;

@Slf4j
public class ShowMediaTask extends TimerTask {


	private final LocalDateTime startedAt;
	//private final LocalDateTime pausedAt;

	private final Duration duration;
	private final Duration shown;

	// private long start;
	// private long paused;
	// private long LocalDateTime = 0;

	private final AbstractUI abstractUI;
	//private java.time.LocalDateTime actual;
	private Duration showing;

	/**
	 * 
	 * @param abstractUI
	 * @param duration
	 */
	public ShowMediaTask(AbstractUI abstractUI, Duration duration ) {
		this(abstractUI, Duration.ZERO, duration);
	}

	/**
	 * 
	 * @param abstractUI
	 * @param pausedAt
	 * @param duration
	 */
	public ShowMediaTask(AbstractUI abstractUI, Duration shown, Duration duration) {
		this.startedAt  = java.time.LocalDateTime.now();
		this.abstractUI = abstractUI;
		this.shown      = shown;
		this.duration   = duration;

		if ( log.isDebugEnabled())
			log.debug( "getIndex() [{}] => started @ [{}] paused @ [{}] duration [{}]", 					
					abstractUI.getMonitorUI().isPresent() ? abstractUI.getMonitorUI().get().getIndex() : Constants.Screen.COLOR_SEPARATOR,
				   	DateTimeFormatter.ISO_LOCAL_TIME.format(startedAt),
					shown, 
					duration
			);
		
	}
	
	/*
	public ShowMediaTask(AbstractUI abstractUI, long duration ) {
		this(abstractUI, java.time.LocalDateTime.now(), Duration.of( duration, ChronoUnit.MILLIS));
	}
	
	public ShowMediaTask(AbstractUI abstractUI, long paused, long duration) {
		this(
				abstractUI,
				java.time.LocalDateTime.ofInstant(Instant.ofEpochMilli(paused), ZoneId.systemDefault()),
				Duration.of( duration, ChronoUnit.MILLIS)
		);
	}
	*/



	@Override
	public void run() {

		showing = Duration.between(startedAt, LocalDateTime.now()).plus(shown);

		if ( log.isDebugEnabled())
			log.debug( "getIndex() [{}] - started @ [{}] shown @ [{}] showing [{}] duration [{}]", 
						abstractUI.getMonitorUI().isPresent() ? abstractUI.getMonitorUI().get().getIndex() : Constants.Screen.COLOR_SEPARATOR,
						DateTimeFormatter.ISO_LOCAL_TIME.format(startedAt),
						shown, 
						showing,
						duration
			);

		if ( showing.compareTo(duration) < 0 ) {
			long aa = showing.toMillis();
			long dd = duration.toMillis();
			abstractUI.updatePercentageProgess( aa, dd );
		} else {
			cancel();
			abstractUI.next();
		}
	}
	
//	public void setDuration( ) {
//		this.duration = duration;
//	}
	
	public Duration getShowing() {
		return showing;
	}
	
}
