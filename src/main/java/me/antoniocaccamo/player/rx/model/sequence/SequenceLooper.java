package me.antoniocaccamo.player.rx.model.sequence;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import me.antoniocaccamo.player.rx.model.preference.LoadedSequence;

@Slf4j
public class SequenceLooper {


    private final Lock MY_LOCK = new ReentrantLock();
    private  int _loop    = 0;
    private  int _current = 0;
    private  int _next    = -1;

    @Getter
    private Optional<LoadedSequence> optionalSequence = Optional.empty();



    public Optional<Media> next()   {
        return next(LocalDateTime.now());
    }

    public void setOptionalSequence(Optional<LoadedSequence> optioanlSequence){
        this.optionalSequence = optioanlSequence;
        reset();
    }

    private void reset() {
        _loop    =  0;
        _current =  0;
        _next    = -1;
    }

    private Optional<Media> next(final LocalDateTime now) {

        AtomicReference<Media> media = new AtomicReference<>();

        optionalSequence.ifPresent( sq ->{

            MY_LOCK.lock();
            boolean found = false;
            try {
                while ( ! found ) {
                    _next = ++ _next % sq.getSequence().getMedias().size();
                    media.set(sq.getSequence().getMedias().get(_next));
                    if (media.get().isPlayable(now)) {
                        found = true;
                    } else {
                        log.warn("@@ -- TODO --");
                        found = true;
                    }
                    _loop += _next == 0 ? 1 : 0;
                }
            } finally {
                MY_LOCK.unlock();
            }
        });

        return Optional.ofNullable(media.get());

    }

/*
    public SequenceLooper() {
        this( new LinkedList());
    }

    public Media next()   {
        return next(LocalDateTime.now());
    }

    private Media next(final LocalDateTime now) {
        MY_LOCK.lock();
        LocalDateTime saved = LocalDateTime.now();
        try {
            Media nextMedia2Play = null;
            Media v = (Media) getVideos().getVideo(this.nextIndex);
            // is the next playable ?
            logger.debug("now is : {} ", now);
            if (v.isPlayable(now)) {
                nextMedia2Play = v;
                this.nextIndex++;
                checkEnd();
            } else {
                this.nextIndex++;
                checkEnd();
                Media vv = null;
                boolean nextFound = false;
                while (!nextFound && v != vv) {
                    vv = (Media) getVideos().getVideo(this.nextIndex);
                    this.nextIndex++;
                    checkEnd();
                    logger.debug("saved is : {}  ", saved);
                    if (vv.isPlayable(saved)) {
                        nextFound = true;
                    }
                }
                if (nextFound) {
                    nextMedia2Play = vv;
                }
            }
            return nextMedia2Play;
        } finally {
            MY_LOCK.unlock();
        }
    }

 */
}
