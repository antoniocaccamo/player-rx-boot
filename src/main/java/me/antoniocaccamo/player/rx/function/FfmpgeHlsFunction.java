/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.antoniocaccamo.player.rx.function;

import io.reactivex.functions.Function;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderProgressListener;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.VideoAttributes;

/**
 *
 * @author ConsCaccamoAntonio
 */
@Slf4j
public class FfmpgeHlsFunction implements Function<Resource, Resource> {
    
    private final String resourcePrefixPath;

    public FfmpgeHlsFunction(String resourcePrefixPath) {
        this.resourcePrefixPath = resourcePrefixPath;
    } 
    
    @Override
    public Resource apply(Resource resource) throws Exception {
        File source = new File(resource.getPath());
        LocalDateTime sdt = LocalDateTime.ofInstant(Instant.ofEpochMilli(source.lastModified()), ZoneId.systemDefault());
        log.info("source : {} => exists : {} : last modified : {}", source.getAbsolutePath(), source.exists(), source.exists() ? DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(sdt) : " - ");
        File target = new File(Constants.Resource.getVideoHLS(resourcePrefixPath, resource));
        LocalDateTime tdt = LocalDateTime.ofInstant(Instant.ofEpochMilli(target.lastModified()), ZoneId.systemDefault());
        log.info("target : {} => exists : {} : last modified : {}", target.getAbsolutePath(), target.exists(), target.exists() ? DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(tdt) : " - ");
        if (target.exists()) {
            if (tdt.isAfter(sdt)) {
                log.info("not necessary to transcode resource : {}", resource);
                return resource;
            }
            File targetParent = target.getParentFile();
            Stream.of(targetParent.listFiles()).forEach((file) -> log.info("deleting {} => {}", file, file.delete()));
            log.info("deleting {} => {}", targetParent.getAbsolutePath(), targetParent.delete());
        }
        target.getParentFile().mkdirs();
        target.createNewFile();
        AudioAttributes audioAttr = new AudioAttributes();
        VideoAttributes videoAttr = new VideoAttributes();
        audioAttr.setChannels(2);
        audioAttr.setCodec("aac");
        audioAttr.setBitRate(128000);
        audioAttr.setSamplingRate(44100);
        videoAttr.setCodec("libx264");
        videoAttr.setBitRate(4000000);
        videoAttr.setX264Profile(VideoAttributes.X264_PROFILE.BASELINE);
        EncodingAttributes encodingAttr = new EncodingAttributes();
        encodingAttr.setFormat("hls");
        encodingAttr.setAudioAttributes(audioAttr);
        encodingAttr.setVideoAttributes(videoAttr);
        log.info("encoding attributes : {}", encodingAttr.toString());
        Encoder encoder = new Encoder();
        Resource finalResource = resource;
        encoder.encode(new MultimediaObject(source), target, encodingAttr, new EncoderProgressListener() {
            @Override
            public void sourceInfo(MultimediaInfo multimediaInfo) {
                log.info("multimediaInfo : {}", multimediaInfo);
                finalResource.setDuration(Duration.ofMillis(multimediaInfo.getDuration()));
                log.info("Duration.ofMillis( multimediaInfo.getDuration() : {}", finalResource.getDuration());
            }

            @Override
            public void progress(int i) {
                log.info("progress : {} / 1000", i);
            }

            @Override
            public void message(String s) {
                log.info("message : {}", s);
            }
        });
        log.info("{} :  trancoded : {}", Thread.currentThread().getName(), resource);
        return resource;
    }
    
}
