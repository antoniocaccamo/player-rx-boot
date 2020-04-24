package me.antoniocaccamo.player.rx.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import me.antoniocaccamo.player.rx.service.TranscodeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ws.schild.jave.DefaultFFMPEGLocator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author antoniocaccamo on 19/02/2020
 */
@Service
@Slf4j
public class HLSTrancodeServiceImpl implements TranscodeService {

    @Value("${spring.application.resource-prefix-path}")
    private String resourcePrefixPath;

    private final AtomicBoolean running = new AtomicBoolean(Boolean.FALSE);

    private BlockingQueue<Resource> toTrancodegQueue     = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> trancodingQueue  = new LinkedBlockingQueue<>();

    private ExecutorService executorService;

    private int transcoders = 5;

    @PostConstruct
    public void postConstruct(){

        running.set(Boolean.TRUE);
        executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < transcoders; i++  ) {
            executorService.submit( new TrancoderTask(toTrancodegQueue,trancodingQueue));
        }
        DefaultFFMPEGLocator locator= new  DefaultFFMPEGLocator();
        String exePath= locator.getFFMPEGExecutablePath();
        log.info("{} service started : ffmpeg location : {}", getClass().getSimpleName(), exePath);
    }

    @PreDestroy
    public void preDestroy() {
        log.info("{} service destroying", getClass().getSimpleName());
        running.set(Boolean.FALSE);
        executorService.shutdown();
        try {
            executorService.awaitTermination(2, TimeUnit.SECONDS);
            executorService.shutdownNow();

        } catch (InterruptedException ex) {
            log.error("error occurred", ex);
            executorService.shutdownNow();
        } finally {
            log.info("{} stopped" , getClass().getSimpleName());
        }
    }

    @Override
    public void transcode(Resource resource) {
        if ( Constants.Resource.Type.VIDEO.equals(resource.getType()) &&  resource.needsTrancode()){
            log.info("add video resource to trancode : {}", resource);
            toTrancodegQueue.offer(resource);
        }
    }


    private class TrancoderTask implements Runnable {

        private BlockingQueue<Resource> toTrancodegQueue;
        private final BlockingQueue<String> trancodingQueue;

        private TrancoderTask(BlockingQueue<Resource> toTrancodegQueue, BlockingQueue<String> trancodingQueue) {
            this.toTrancodegQueue = toTrancodegQueue;
            this.trancodingQueue = trancodingQueue;
        }

        @Override
        public void run() {
            log.info("{} ready to trancode..", Thread.currentThread().getName());
            Resource resource = null;
            while ( running.get() ){
                try {
                    resource = toTrancodegQueue.poll(5, TimeUnit.SECONDS);
                    if ( resource == null)
                        continue;
                    if (  ! trancodingQueue.contains(resource.getHash()) ) {
                        trancodingQueue.offer(resource.getHash());
                        log.info("{} : video resource to trancode : {}", Thread.currentThread().getName(), resource);

                         Thread.sleep(resource.getDuration().toMillis());

                        log.info("{} :  trancoded : {}", Thread.currentThread().getName(), resource);
                    }
                } catch (InterruptedException e) {
                    log.info("{} interrupted" , Thread.currentThread().getName());
                } catch (Exception e) {
                    if ( resource != null)
                        trancodingQueue.remove(resource.getHash());
                }
            }
        }
    }
}
