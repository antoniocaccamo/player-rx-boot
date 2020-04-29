package me.antoniocaccamo.player.rx.task;

import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.ui.AbstractUI;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.TimerTask;

@Slf4j
public class ShowMediaTask extends TimerTask {


	private final LocalDateTime startedAt;
	private final LocalDateTime pausedAt;

	private Duration duration;

	private long start;
	private long paused;
	private long LocalDateTime = 0;

	private AbstractUI abstractUI;
	private java.time.LocalDateTime actual;

	public ShowMediaTask(AbstractUI abstractUI, Duration duration ) {
		this(abstractUI, java.time.LocalDateTime.now(), duration);
	}

	public ShowMediaTask(AbstractUI abstractUI, LocalDateTime pausedAt, Duration duration) {
		this.startedAt  = java.time.LocalDateTime.now();
		this.abstractUI = abstractUI;
		this.pausedAt   = pausedAt;
		this.duration   = duration;

		log.info("getIndex() [{}] - duration : {}", abstractUI.getMonitorUI().getIndex(),  duration);
	}
	
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



	@Override
	public void run() {

		actual = java.time.LocalDateTime.now();

		Duration between = Duration.between( startedAt, actual );

		if ( between.compareTo(duration) < 0 ) {
			long aa = between.toMillis();
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
	
	public LocalDateTime getActual() {
		return actual;
	}
	
}
