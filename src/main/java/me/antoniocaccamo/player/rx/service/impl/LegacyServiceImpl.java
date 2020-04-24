
package me.antoniocaccamo.player.rx.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.legacy.sequences.SequenceType;
import me.antoniocaccamo.player.rx.model.legacy.sequences.VideoType;
import me.antoniocaccamo.player.rx.model.preference.PreferenceModel;
import me.antoniocaccamo.player.rx.model.preference.Screen;
import me.antoniocaccamo.player.rx.model.preference.ScreenLocation;
import me.antoniocaccamo.player.rx.model.preference.ScreenSize;
import me.antoniocaccamo.player.rx.model.resource.LocalResource;
import me.antoniocaccamo.player.rx.model.resource.RemoteResource;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;
import me.antoniocaccamo.player.rx.service.LegacyService;
import me.antoniocaccamo.player.rx.service.ResourceService;
import me.antoniocaccamo.player.rx.service.SequenceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.stream.XMLInputFactory;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author antoniocaccamo on 07/04/2020
 */
@Service
@Slf4j
public class LegacyServiceImpl implements LegacyService {

    @Value("${spring.application.legacy-pref-file}")
    @NotNull
    private String prefFile;

//    @Autowired
//    private SequenceRepository sequenceRepository;
//
//    @Autowired
//    private MediaRepository mediaRepository;
//
//    @Autowired
//    private ResourceRepository resourceRepository;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private ResourceService resourceService;

    @Override
    public PreferenceModel loadPreferenceModel() throws IOException {

        PreferenceModel preferenceModel = null;


        Properties props = new Properties();
        props.load(new FileReader(prefFile));

        preferenceModel = new PreferenceModel();

        String cn = props.getProperty(Constants.Player.APP_COMPUTER);
        if (StringUtils.isNotEmpty(cn)) {
            preferenceModel.setComputer(cn);
        } else {
            try {
                preferenceModel.setComputer(InetAddress.getLocalHost().getHostName());
            } catch (Exception e) {
                e.printStackTrace(System.err);
                preferenceModel.setComputer(System.getenv("COMPUTERNAME"));
            }
        }
/*
            Player.APP_SEND_ALL_MAIL = preferenceStore.getBoolean(Constants.Player.APP_SEND_ALL_MAIL);

            boolean enabledLog = preferenceStore.getBoolean(Constants.Player.APP_ENABLED_LOG);

            enableLog(enabledLog);

            log.info("AT ADV Player ");
            log.info("version @ " + Player.VERSION + " " + VERSION_DATE);

            String locale = preferenceStore.getString(Constants.Player.APP_LOCALE);
            if (StringUtils.isEmpty(locale)) {
                LocaleManager.changeLocale(Locale.getDefault().toString());
            } else {
                LocaleManager.changeLocale(locale);
            }

            String updateSite = preferenceStore.getString(Constants.Player.APP_UPDATE_SITE);
            if (!Utils.isAnEmptyString(updateSite)) {
                Player.UPDATE_SITE = updateSite;
            } else {
                Player.UPDATE_SITE = "http://www.arttechonline.com/atadvplayer/jupdate/update.php";
            }

            double ftpRefresh = preferenceStore.getDouble(Constants.Player.APP_FTP_REFRESH);
            if (ftpRefresh == 0) {
                ftpRefresh = Constants.Player.FTP_REFRESH_DEFAULT;
            }
            Player.APP_FTP_REFRESH = ftpRefresh;

            String versionInfo = preferenceStore.getString(Constants.Player.APP_VERSION_INFO);
            if (!Utils.isAnEmptyString(versionInfo)) {
                Player.VERSION_INFO = versionInfo;
            }

            String mplayer = preferenceStore.getString(Constants.Player.APP_MPLAYER_PATH);

            if (!Utils.isAnEmptyString(mplayer)) {
                //				File file = new File(  System.getProperty("user.dir") + "/" + mplayer);
                File file = new File(mplayer);
                if (file.exists() && file.isFile() && file.canExecute()) {
                    Player.MPLAYER_PATH = mplayer;
                }
            }

            String options = preferenceStore.getString(Constants.Player.APP_MPLAYER_OPTIONS);
            if (!Utils.isAnEmptyString(options)) {
                Player.MPLAYER_OPTIONS = options;
            }

            String mplayerMode = StringUtils.isNotEmpty(preferenceStore.getString(Constants.Player.APP_MPLAYER_MODE)) ?
                    preferenceStore.getString(Constants.Player.APP_MPLAYER_MODE) : MPlayerMode.IDLE.name();
            for ( MPlayerMode mm : MPlayerMode.values() ) {
                if ( StringUtils.endsWithIgnoreCase(mm.name(), mplayerMode))
                    Player.MPLAYER_MODE =  mm;
            };

            log.info("Player.MPLAYER_MODE = {}" , Player.MPLAYER_MODE);

            Player.APP_MPLAYER_WAIT_VIDEO_START = preferenceStore.getInt(Constants.Player.APP_MPLAYER_WAIT_VIDEO_START);
            if ( Player.APP_MPLAYER_WAIT_VIDEO_START == 0 ){
                Player.APP_MPLAYER_WAIT_VIDEO_START = 2;
            }

            Player.APP_TIME_LABEL_RATIO = preferenceStore.getInt(Constants.Player.APP_TIME_LABEL_RATIO);
            Player.APP_DATE_LABEL_RATIO = preferenceStore.getInt(Constants.Player.APP_DATE_LABEL_RATIO);
*/
        int width = Integer.valueOf(props.getProperty(Constants.Player.APP_SIZE_WIDTH, String.valueOf(Constants.Player.WIDTH)));
        int heigth = Integer.valueOf(props.getProperty(Constants.Player.APP_SIZE_HEIGHT, String.valueOf(Constants.Player.HEIGHT)));

        preferenceModel.setSize(new ScreenSize(width, heigth));


        int x = Integer.valueOf(props.getProperty(Constants.Player.APP_LOCATION_X, String.valueOf(Constants.Player.TOP)));
        int y = Integer.valueOf(props.getProperty(Constants.Player.APP_LOCATION_Y, String.valueOf(Constants.Player.LEFT)));

        preferenceModel.setLocation(new ScreenLocation(x, y));

        preferenceModel.setWeatherLatlng(props.getProperty(Constants.Player.APP_PLAYER_WEATHER_LATLNG, Constants.Player.DEFAULT_WEATHER_LATLNG));
/*
            weatherLatlng = preferenceStore.getString(Constants.Player.APP_PLAYER_WEATHER_LATLNG);
            if ( StringUtils.isEmpty(weatherLatlng)){
                weatherLatlng = " 45.08,7.40";
            }

            // minutes
            weatherRefresh = preferenceStore.getInt(Constants.Player.APP_PLAYER_WEATHER_REFRESH);
            if ( weatherRefresh == 0 ) {
                weatherRefresh = 180;
            }
*/
        List<Screen> monitorModels = new ArrayList<>();
        int numOfWindows = Integer.valueOf(props.getProperty(Constants.Player.APP_PLAYER_VIDEO_WINDOWS_NUMBER, String.valueOf(1)));

        if (numOfWindows > 0) {

            for (int i = 1; i <= numOfWindows; i++) {
                Screen monitorModel = new Screen();
                Integer[] idx = new Integer[1];
                idx[0] = new Integer(i);


                int pw = Integer.valueOf(props.getProperty(Constants.Screen.APP_PLAYER_I_SIZE_WIDTH.format(idx), String.valueOf(Constants.Screen.WIDTH)));
                int ph = Integer.valueOf(props.getProperty(Constants.Screen.APP_PLAYER_I_SIZE_HEIGHT.format(idx), String.valueOf(Constants.Screen.HEIGHT)));
                monitorModel.setSize(new ScreenSize(pw, ph));

                int pt = Integer.valueOf(props.getProperty(Constants.Screen.APP_PLAYER_I_LOCATION_X.format(idx), String.valueOf(Constants.Screen.TOP)));
                int pl = Integer.valueOf(props.getProperty(Constants.Screen.APP_PLAYER_I_LOCATION_Y.format(idx), String.valueOf(Constants.Screen.LEFT)));

                monitorModel.setLocation(new ScreenLocation(pt, pl));

/*
                    int loop = preferenceStore.getInt(Constants.Screen.APP_PLAYER_I_LOOP_NUMBER.format(idx));
                    monitorModel.setLoopNumber(loop);

                    boolean lock = preferenceStore.getBoolean(Constants.Screen.APP_PLAYER_I_SCREEN_LOCK.format(idx));
                    monitorModel.setLock(lock);

                    boolean fade = preferenceStore.getBoolean(Constants.Screen.APP_PLAYER_I_SCREEN_FADE.format(idx));
                    monitorModel.setFade(fade);

                    int alpha = preferenceStore.getInt(Constants.Screen.APP_PLAYER_I_SCREEN_ALPHA.format(idx));
                    monitorModel.setAlpha(alpha);

                    boolean view = preferenceStore.getBoolean(Constants.Screen.APP_PLAYER_I_SCREEN_VIEW.format(idx));
                    monitorModel.setViewScreen(view);

                    */

                log.warn(" to continue ...");

//                    boolean allDay = preferenceStore.getBoolean(Constants.Screen.APP_PLAYER_I_ALL_DAY.format(idx));
//                    monitorModel.setAllDay(allDay);
//
//                    boolean timed = preferenceStore.getBoolean(Constants.Screen.APP_PLAYER_I_TIMED.format(idx));
//                    monitorModel.setTimed(timed);

                monitorModel.setTiming(Constants.TimingEnum.ALL_DAY);
//
//                    if (timed) {
//                        String from = preferenceStore.getString(Constants.Screen.APP_PLAYER_I_TIMED_FROM.format(idx));
//                        String to = preferenceStore.getString(Constants.Screen.APP_PLAYER_I_TIMED_TO.format(idx));
//                        LocalTime df = null;
//                        LocalTime dt = null;
//                        try {
//                            df = ISODateTimeFormat.hourMinute().parseLocalTime(from);
//
//                            dt = ISODateTimeFormat.hourMinute().parseLocalTime(to);
//
//                        } catch (Exception e) {
//                            df = ISODateTimeFormat.hourMinuteSecond().parseLocalTime(from);
//                            dt = ISODateTimeFormat.hourMinuteSecond().parseLocalTime(to);
//                        }
//
//                        if (df != null && dt != null) {
//                            df = df.withSecondOfMinute(0);
//                            dt = dt.withSecondOfMinute(0);
//                            log.debug("timed : from [{}] to [{}]", df, dt);
//                            monitorModel.setFrom(df);
//                            monitorModel.setTo(dt);
//                        }
//                    }
//
//                    boolean black = preferenceStore.getBoolean(Constants.Screen.APP_PLAYER_I_WHEN_NOT_ACTIVE_BLACK.format(idx));
//                    monitorModel.setWhenNotActiveBlack(black);
//
//                    boolean watch = preferenceStore.getBoolean(Constants.Screen.APP_PLAYER_I_WHEN_NOT_ACTIVE_WATCH.format(idx));
//                    monitorModel.setWhenNotActiveWatch(watch);
//
//                    boolean img = preferenceStore.getBoolean(Constants.Screen.APP_PLAYER_I_WHEN_NOT_ACTIVE_IMAGE.format(idx));
//                    monitorModel.setWhenNotActiveImage(img);
//
//                    String timeFontDataString
//                            = preferenceStore.getString(Constants.Screen.APP_PLAYER_I_SCREEN_FONT_TIME.format(idx));
//                    if (!Utils.isAnEmptyString(timeFontDataString)) {
//                        try {
//                            FontData timeFontData = new FontData(timeFontDataString);
//                            monitorModel.setTimeLabelFontData(timeFontData);
//                        } catch (Exception e) {
//                            log.error("time font date error", e);
//                        }
//                    }
//
//                    String dateFontDataString
//                            = preferenceStore.getString(Constants.Screen.APP_PLAYER_I_SCREEN_FONT_DATE.format(idx));
//                    if (!Utils.isAnEmptyString(dateFontDataString)) {
//                        try {
//                            FontData dateFontData = new FontData(dateFontDataString);
//                            monitorModel.setDateLabelFontData(dateFontData);
//                        } catch (Exception e) {
//                            log.error("date font date error", e);
//                        }
//                    }
//
//                    String watchImageString
//                            = preferenceStore.getString(Constants.Screen.APP_PLAYER_I_WATCH_IMAGE_FILE.format(idx));
//                    if (!Utils.isAnEmptyString(watchImageString)) {
//                        File watchImageFile = new File(watchImageString);
//                        if (watchImageFile.exists() && watchImageFile.isFile() && watchImageFile.canRead()) {
//                            monitorModel.setWatchImageFile(watchImageFile);
//                        }
//                    }
//
//                    String timeColor
//                            = preferenceStore.getString(Constants.Screen.APP_PLAYER_I_SCREEN_COLOR_TIME.format(idx));
//                    if (!Utils.isAnEmptyString(timeColor)) {
//                        StringTokenizer st = new StringTokenizer(timeColor, Constants.Screen.COLOR_SEPARATOR);
//                        if (st.countTokens() == 3) {
//                            try {
//                                String rs = st.nextToken();
//                                String gs = st.nextToken();
//                                String bs = st.nextToken();
//
//                                int red = Integer.parseInt(rs);
//                                int green = Integer.parseInt(gs);
//                                int blue = Integer.parseInt(bs);
//
//                                RGB rgb = new RGB(red, green, blue);
//                                monitorModel.setTimeLabelFontColor(rgb);
//                            } catch (NumberFormatException e) {
//                                log.error("time color error", e);
//                            }
//                        }
//                    }
//
//                    String dateColor
//                            = preferenceStore.getString(Constants.Screen.APP_PLAYER_I_SCREEN_COLOR_DATE.format(idx));
//                    if (!Utils.isAnEmptyString(dateColor)) {
//                        StringTokenizer st = new StringTokenizer(dateColor, Constants.Screen.COLOR_SEPARATOR);
//                        if (st.countTokens() == 3) {
//                            try {
//                                String rs = st.nextToken();
//                                String gs = st.nextToken();
//                                String bs = st.nextToken();
//
//                                int red = Integer.parseInt(rs);
//                                int green = Integer.parseInt(gs);
//                                int blue = Integer.parseInt(bs);
//
//                                RGB rgb = new RGB(red, green, blue);
//                                monitorModel.setDateLabelFontColor(rgb);
//                            } catch (NumberFormatException e) {
//                                log.error("time color error", e);
//                            }
//                        }
//                    }
//
//                    int monitor = preferenceStore.getInt(Constants.Screen.APP_PLAYER_I_SCREEN_MONITOR.format(idx));
//                    monitorModel.setMonitor(monitor == 0 ? ScreenSetting.DEFAULT_MONITOR: monitor);
//
//                    this.playerSetting.add(monitorModel);
                String spath = props.getProperty(Constants.Screen.APP_PLAYER_I_SEQUENCE_FILE.format(idx));
                Optional<Sequence> sequence = readSequence(spath);
                if ( sequence.isPresent() ) {
                    monitorModel.setSequence(sequence.get().getName());
                }
                monitorModels.add(monitorModel);
            }
        } else {
            Screen monitorModelDefault = new Screen();

            monitorModels.add(monitorModelDefault);
        }

        preferenceModel.setScreens(monitorModels);

        return preferenceModel;
    }


    public Optional<Sequence> readSequence(String path)  {
        Sequence sequence = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SequenceType.class);


            JAXBElement<SequenceType> sequenceType = (JAXBElement<SequenceType>) jaxbContext.createUnmarshaller()
                .unmarshal(
                        XMLInputFactory.newInstance().createXMLEventReader(new FileReader(path)),
                        SequenceType.class
                );

            Optional<Sequence> os =  sequenceService.getSequenceByName(sequenceType.getValue().getName());

            if ( os.isPresent() ) {
                sequence = os.get();
            } else {

                List<Media> medias = sequenceType.getValue().getVideos().getVideo()
                        .stream()
                        .map(this::video2media)
//                    .map(media -> {
//                        Resource resource = media.getResource();
//                        resourceRepository.save(resource);
//                        media.setResource(resource);
//                        return  mediaRepository.save(media);
//                    })
                        .collect(Collectors.toList());


                sequence = new Sequence();
                sequence.setName(sequenceType.getValue().getName());
                sequence.setMedias(medias);

                sequence = sequenceService.save(sequence, null);
            }

        } catch (Exception e) {
            log.error("error reading from file : {}", path , e);
        }

        return Optional.ofNullable(sequence);
    }

    private  Media video2media(VideoType videoType) {
        Media media = new Media();
        Resource resource = null;

        log.warn(Constants.TODO);

        media.setDaysOfWeek(videoType.getDaysOfWeek());
        media.setLimited(videoType.getLimited());

        if (StringUtils.isNotEmpty(videoType.getStart()))
            media.setStart(LocalDate.from(DateTimeFormatter.ISO_DATE.parse(videoType.getStart())));

        if (StringUtils.isNotEmpty(videoType.getEnd()))
            media.setEnd(LocalDate.from(DateTimeFormatter.ISO_DATE.parse(videoType.getEnd())));

        if (StringUtils.isNotEmpty(videoType.getFrom()))
            media.setFrom(LocalTime.from(DateTimeFormatter.ISO_TIME.parse(videoType.getFrom())));

        if (StringUtils.isNotEmpty(videoType.getTo()))
            media.setTo(LocalTime.from(DateTimeFormatter.ISO_TIME.parse(videoType.getTo())));

        switch (videoType.getType()) {
            case BLACK_WINDOW:
                resource = LocalResource.builder()
                        .withType(Constants.Resource.Type.BLACK)
                        .build();
                break;

            case BROWSER:

                resource = LocalResource.builder()
                        .withType(Constants.Resource.Type.BROWSER)
                        .build();
                break;

            case FTP_IMAGE:

                resource = RemoteResource.builder()
                        .withPath(videoType.getPath())
                        .withRemote(Constants.Resource.Remote.FTP)
                        .withType(Constants.Resource.Type.PHOTO)
                        .build();
                break;

            case FTP_VIDEO:
                RemoteResource.builder()
                        .withPath(videoType.getPath())
                        .withRemote(Constants.Resource.Remote.FTP)
                        .withType(Constants.Resource.Type.VIDEO)
                        .build();
                break;

            case HIDDEN_WINDOW:
                break;

            case PHOTO:
                resource =  LocalResource.builder()
                        .withPath(videoType.getPath())
                        .withType(Constants.Resource.Type.PHOTO)
                        .build();
                break;

            case VIDEO:
                resource = LocalResource.builder()
                        .withPath(videoType.getPath())
                        .withType(Constants.Resource.Type.VIDEO)
                        .build();
                break;

            case WATCH:
                resource = LocalResource.builder()
                        .withType(Constants.Resource.Type.WATCH)
                        .build();
                break;

            case WEATHER:
                resource = LocalResource.builder()
                        .withType(Constants.Resource.Type.WEATHER)
                        .build();
                break;

            default:
        }

        Optional<Resource> or = this.resourceService.getResourceByHash(resource);
        if ( or.isPresent() )
            resource = or.get();

        media.setResource(resource);

        if (videoType.getDuration() != null && !videoType.getDuration().isNaN()) {
            log.info("videoType.getDuration() : {}", videoType.getDuration());
            media.setDuration(Duration.of(videoType.getDuration().longValue(), ChronoUnit.SECONDS));
        } else {
            log.warn("...");
            media.setDuration(Duration.ofSeconds(5));
        }

        return media;
    }

}
