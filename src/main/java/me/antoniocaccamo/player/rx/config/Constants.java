package me.antoniocaccamo.player.rx.config;


import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.model.Model;
import me.antoniocaccamo.player.rx.model.jackson.ResourceCollectionWrapprer;
import me.antoniocaccamo.player.rx.model.resource.LocalResource;
import me.antoniocaccamo.player.rx.model.resource.RemoteResource;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.Arrays;


@Slf4j
public class Constants {

    public static final String TODO = "===>> @@ TODO @@ <<===";

    public interface Preference {

        String COMPUTER = "!!! must change me !!!";

        int WIDTH = 500;

        int HEIGHT = 400;

        int TOP    = 100;

        int LEFT   = 100;

        String DEFAULT_WEATHER_LATLNG = "45.08,7.40";

        String APP_COMPUTER = "app.computer";

        String APP_SEND_ALL_MAIL = "app.send.all.mail";

        String APP_ENABLED_LOG = "app.enabled.log";

        String APP_LOCALE = "app.locale";

        String APP_VERSION = "app.version";
        String APP_VERSION_DATE = "app.version.date";
        String APP_VERSION_INFO = "app.last.update.info";

        String APP_FTP_REFRESH = "app.ftp.refresh";

        String APP_SIZE_WIDTH = "app.size.width";

        String APP_SIZE_HEIGHT = "app.size.height";

        String APP_LOCATION_X = "app.location.x";

        String APP_LOCATION_Y = "app.location.y";

        String APP_MPLAYER_MODE = "app.mplayer.mode";
        String APP_MPLAYER_PATH = "app.mplayer.path";
        String APP_MPLAYER_OPTIONS = "app.mplayer.options";
        String APP_MPLAYER_WAIT_VIDEO_START = "app.mplayer.wait.video.start";


        String APP_TIME_LABEL_RATIO = "app.time.label.ratio";
        String APP_DATE_LABEL_RATIO = "app.date.label.ratio";

        String APP_PLAYER_VIDEO_WINDOWS_NUMBER = "app.player.video.windows.number";

        String APP_UPDATE_SITE = "app.update.site";

        String APP_PLAYER_WEATHER_LATLNG = "app.player.weather.latlng";
        String APP_PLAYER_WEATHER_REFRESH = "app.player.weather.refresh";


    }

    public interface Screen {

        public enum DefaultEnum{ N, Y}

        int WIDTH = 500;

        int HEIGHT = 400;

        int TOP    = 100;

        int LEFT   = 100;

        boolean ALL_DAY_DEFAULT = false;

        boolean LOCK_DEFAULT = false;

        boolean AUTOPLAY_DEFAULT = false;

        boolean VIEW_SCREEN_DEFAULT = true;


        int DEFAULT_ALPHA = 255;


        int DEFAULT_NUMBER_LOOP = 0;


        int DEFAULT_MONITOR = 1;


        MessageFormat APP_PLAYER_I_SIZE_WIDTH = new MessageFormat("app.player.{0}.size.width");

        MessageFormat APP_PLAYER_I_SIZE_HEIGHT = new MessageFormat("app.player.{0,number,#}.size.height");

        MessageFormat APP_PLAYER_I_LOCATION_X = new MessageFormat("app.player.{0,number,#}.location.x");

        MessageFormat APP_PLAYER_I_LOCATION_Y = new MessageFormat("app.player.{0,number,#}.location.y");

        MessageFormat APP_PLAYER_I_SEQUENCE_FILE = new MessageFormat("app.player.{0,number,#}.sequence.file");

        MessageFormat APP_PLAYER_I_SCREEN_LOCK = new MessageFormat("app.player.{0,number,#}.screen.lock");

        MessageFormat APP_PLAYER_I_SCREEN_FADE = new MessageFormat("app.player.{0,number,#}.screen.fade");

        MessageFormat APP_PLAYER_I_SCREEN_ALPHA = new MessageFormat("app.player.{0,number,#}.screen.alpha");

        MessageFormat APP_PLAYER_I_SCREEN_VIEW = new MessageFormat("app.player.{0,number,#}.screen.view");

        MessageFormat APP_PLAYER_I_SCREEN_FONT_TIME = new MessageFormat("app.player.{0,number,#}.screen.font.time");

        MessageFormat APP_PLAYER_I_SCREEN_FONT_DATE = new MessageFormat("app.player.{0,number,#}.screen.font.date");

        MessageFormat APP_PLAYER_I_ALL_DAY = new MessageFormat("app.player.{0,number,#}.activation.allDay");

        MessageFormat APP_PLAYER_I_TIMED = new MessageFormat("app.player.{0,number,#}.activation.timed");

        MessageFormat APP_PLAYER_I_TIMED_FROM = new MessageFormat("app.player.{0,number,#}.activation.timed.from");

        MessageFormat APP_PLAYER_I_TIMED_TO = new MessageFormat("app.player.{0,number,#}.activation.timed.to");

        MessageFormat APP_PLAYER_I_WHEN_NOT_ACTIVE_WATCH = new MessageFormat("app.player.{0,number,#}.not.active.watch");

        MessageFormat APP_PLAYER_I_WHEN_NOT_ACTIVE_BLACK = new MessageFormat("app.player.{0,number,#}.not.active.black");

        MessageFormat APP_PLAYER_I_WHEN_NOT_ACTIVE_IMAGE = new MessageFormat("app.player.{0,number,#}.not.active.image");

        MessageFormat APP_PLAYER_I_WATCH_IMAGE_FILE = new MessageFormat("app.player.{0,number,#}.watch.image.file");

        MessageFormat APP_PLAYER_I_SCREEN_COLOR_TIME = new MessageFormat("app.player.{0,number,#}.screen.color.time");

        MessageFormat APP_PLAYER_I_SCREEN_COLOR_DATE = new MessageFormat("app.player.{0,number,#}.screen.color.date");

        MessageFormat APP_PLAYER_I_SCREEN_MONITOR = new MessageFormat("app.player.{0,number,#}.screen.monitor");

        MessageFormat APP_PLAYER_I_WEATHER_LATLNG = new MessageFormat("app.player.{0,number,#}.weather.latlng");

        MessageFormat APP_PLAYER_I_LOOP_NUMBER = new MessageFormat("app.player.{0,number,#}.loop.number");

        String COLOR_SEPARATOR = "|";

    }


    public enum TimingEnum {
        TIMED,
        ALL_DAY
    }

    public interface Resource {

        //
        static String getResourceLocationTypeParentPath(String prefixPath, me.antoniocaccamo.player.rx.model.resource.Resource resource) {
            String hls = String.format("%s%s%s%s%s%s%s", prefixPath,
                    File.separator, resource instanceof LocalResource ? Location.Local: Location.Remote,
                    File.separator, resource.getType(),
                    File.separator, resource.getHash()
            );
            log.warn("{} : getResourceLocationTypeParentPath : {}", TODO, hls);
            return hls;
        }

        // @TODO
        static String getVideoHLS(String prefixPath, me.antoniocaccamo.player.rx.model.resource.Resource resource) {
            String hls = String.format("%s%s%s%s%s", getResourceLocationTypeParentPath(prefixPath, resource), File.separator, "hls", File.separator, "index.m3u8");
            log.warn("{} : getVideoHLS : {}", TODO, hls);
            return hls;
        }

        enum Type {
            BLACK,
            BROWSER,
            HIDDEN,
            WATCH,
            WEATHER,
            PHOTO,
            VIDEO;
        }

        interface Location {
            String Local  = "LOCAL";
            String Remote = "REMOTE";
        }

        enum Remote {
            FTP,
            S3
        }

         HashFunction HASH_FUNCTION  = Hashing.crc32();

        me.antoniocaccamo.player.rx.model.resource.Resource DefaultRemoteResourcetWeather = RemoteResource.builder()
                .withType(Constants.Resource.Type.WEATHER)
                .build();

        me.antoniocaccamo.player.rx.model.resource.Resource DefaultLocalResourcetImage =
              LocalResource.builder()
                    .withType(Type.PHOTO)
                    .withPath("default/images/at.image.jpg")
                    .build();

        me.antoniocaccamo.player.rx.model.resource.Resource DefaultLocalResourcetVideo =
                LocalResource.builder()
                        .withType(Constants.Resource.Type.VIDEO)
                        .withPath("default/videos/at.video.mov")
                        .build();

        me.antoniocaccamo.player.rx.model.jackson.ResourceCollectionWrapprer DefaultResourceCollectionWrapprer = ResourceCollectionWrapprer
                .builder()
                .item(Resource.DefaultLocalResourcetImage)
                .item(Resource.DefaultLocalResourcetVideo)
                .item(Resource.DefaultRemoteResourcetWeather)
                .build();

    }

    public interface Sequence {
        String DefaultSequenceName = "DEFAULT_SEQUENCE";
        String Extension = ".seq.yaml";
        Path DefaultSequenceNamePath = Paths.get( String.format("%s%s", DefaultSequenceName, Extension) );

        me.antoniocaccamo.player.rx.model.sequence.Sequence  DEFAULT_SEQUENCE =  me.antoniocaccamo.player.rx.model.sequence.Sequence.builder()
                .name(DefaultSequenceName)
                .location(Model.Location.LOCAL)
                .medias(Arrays.asList(
                        Media.builder()
                                .duration(Duration.ofSeconds(10))
                                .resourceHash( Resource.DefaultLocalResourcetImage.getHash())
                                .resource(Resource.DefaultLocalResourcetImage)
                                .build(),
                        Media.builder()
                                .duration(Duration.ofSeconds(7))
                                .resourceHash(Resource.DefaultRemoteResourcetWeather.getHash())
                                .resource(Resource.DefaultRemoteResourcetWeather)
                                .build(),
                        Media.builder()
                                .duration(Duration.ofSeconds(8))
                                .resourceHash(Resource.DefaultLocalResourcetVideo.getHash())
                                .resource(Resource.DefaultLocalResourcetVideo)
                                .build(),
                        Media.builder()
                                .duration(Duration.ofSeconds(9))
                                .resourceHash(Resource.DefaultRemoteResourcetWeather.getHash())
                                .resource(Resource.DefaultRemoteResourcetWeather)
                                .build()
                ))
                .build();
    }

}
