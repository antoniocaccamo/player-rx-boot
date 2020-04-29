package me.antoniocaccamo.player.rx.service;


import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.resource.LocalResource;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ws.schild.jave.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@SpringBootTest  @Slf4j
class TrancodeServiceTest  {

    @Value("${spring.application.resource-prefix-path}")
    private String resourcePrefixPath;

    @Autowired
    TranscodeService transcodeService;

    @Test
    public void standalone() throws InterruptedException, IOException, EncoderException {

//        Observable.just(1,2,3,4,5,6,7,8,9,10)
//                .subscribe( i ->
//                        transcodeService.transcode(
//                                LocalResource.builder()
//                                        .withDuration(Duration.ofSeconds(i))
//                                        .withType(Constants.Resource.Type.VIDEO)
//                                        .withPath("src/main/videos/at.video.mov")
//                                        .build()
//                        ));
//        Thread.sleep(20000);

        Resource localResource = createResource ();

        File source = new File(localResource.getPath());
        LocalDateTime sdt = LocalDateTime.ofInstant(Instant.ofEpochMilli(source.lastModified()), ZoneId.systemDefault());
        log.info("source : {} => exists : {} : last modified : {}",
                source.getAbsolutePath(),
                source.exists() ,
                source.exists() ? DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(sdt) : " - "
        );

        File target = new File(Constants.Resource.getVideoHLS(resourcePrefixPath, localResource));
        LocalDateTime tdt = LocalDateTime.ofInstant(Instant.ofEpochMilli(target.lastModified()), ZoneId.systemDefault());
        log.info("target : {} => exists : {} : last modified : {}",
                target.getAbsolutePath(),
                target.exists() ,
                target.exists() ? DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(tdt) : " - "
        );

        if ( target.exists() ){
            File targetParent = target.getParentFile();
            Stream.of(targetParent.listFiles())
                    .forEach(file -> log.info("deleting {} => {}", file, file.delete()));
            log.info("deleting {} => {}",targetParent.getAbsolutePath(), targetParent.delete());
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

        log.info("list of video enconder");
        Stream.of(encoder.getVideoEncoders())
                .forEach(enc -> log.info("\t{}", enc));

        encoder.encode(new MultimediaObject(source), target, encodingAttr, new EncoderProgressListener() {
            @Override
            public void sourceInfo(MultimediaInfo multimediaInfo) {
                log.info("multimediaInfo : {}", multimediaInfo);

                log.info("Duration.ofMillis( multimediaInfo.getDuration() : {}", Duration.ofMillis( multimediaInfo.getDuration()));
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

    }

    private static Resource createResource() {
        return  LocalResource.builder()
                .withType(Constants.Resource.Type.VIDEO)
                .withPath("src/main/resources/default/videos/at.video.mov")
                .build();
    }

    @Test
    public void transcode() throws InterruptedException {
        transcodeService.transcode(createResource());

        Thread.sleep(30* 1000);

    }
}
