package me.antoniocaccamo.player.rx.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.model.preference.PreferenceModel;
import me.antoniocaccamo.player.rx.service.LegacyService;
import me.antoniocaccamo.player.rx.service.PreferenceService;
import me.antoniocaccamo.player.rx.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

/**
 * @author antoniocaccamo on 07/02/2020
 */
@Service
@Slf4j
public class PreferenceServiceImpl implements PreferenceService {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private LegacyService legacyPreferenceService;

    @Value("${spring.application.pref-file}") @NotNull
    private File prefFile;

    private PreferenceModel preferenceModel;

    private ObjectMapper mapper = new ObjectMapper(new YAMLFactory());



    @PostConstruct
    public void postConstruct() throws IOException {
        log.info("pref file : {} exists {}", prefFile.getAbsolutePath(), prefFile.exists());

        if ( ! prefFile.exists() ) {
            log.warn(" ==>> reading from legacy <<===");
            preferenceModel = legacyPreferenceService.loadPreferenceModel();
            save();
        } else {
            preferenceModel = mapper.readValue(prefFile, PreferenceModel.class);
        }
    }


    @Override
    public PreferenceModel read() {
        log.info("preferenceModel : {}", preferenceModel);
        return preferenceModel;
    }

    @Override @PreDestroy
    public void save() throws IOException {
        log.info("saving preferences to file : {}", prefFile.getAbsolutePath());
        mapper.writerWithDefaultPrettyPrinter().writeValue(prefFile, preferenceModel);
//        try ( FileWriter fw = new FileWriter(new File(prefFile)) ) {
//            yaml.dump(preferenceModel, fw);
//        } catch (Exception e) {
//            log.error("error occurred", e);
//        }


    }


}
