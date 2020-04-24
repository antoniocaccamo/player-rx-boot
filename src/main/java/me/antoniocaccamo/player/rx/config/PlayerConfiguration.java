package me.antoniocaccamo.player.rx.config;

import me.antoniocaccamo.player.rx.ApplicationUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author antoniocaccamo on 07/04/2020
 */

@Configuration
public class PlayerConfiguration {

    @Bean @Lazy
    public ApplicationUI mainUI(){
        return new ApplicationUI();
    }

}
