package me.antoniocaccamo.player.rx.model.resource;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

/**
 * @author antoniocaccamo on 18/02/2020
 */
@Getter @Slf4j
@Setter
@Entity @DiscriminatorValue(Constants.Resource.Location.Local)
public class LocalResource extends Resource {

    @Override
    public Path getLocalPath() {
//        Path localPath = null;
//        if ( ! isVideo() ) {
//            localPath = Paths.get(path);
//        } else {
//            localPath = Paths.get( Constants.Resource.getVideoHLS(this) );
//        }
//        return localPath;

        return Paths.get(path);
    }

    @Override
    public boolean isLocal() {
        return true;
    }

    public static LocalResourceBuilder builder() {
        return new LocalResourceBuilder();
    }


    public static final class LocalResourceBuilder {
        private LocalResource localResource;

        private LocalResourceBuilder() {
            localResource = new LocalResource();
        }

        public static LocalResourceBuilder aLocalResource() {
            return new LocalResourceBuilder();
        }

        public LocalResourceBuilder withType(Constants.Resource.Type type) {
            localResource.setType(type);
            return this;
        }

//        public LocalResourceBuilder withLocation(LOCATION location) {
//            localResource.setLocation(location);
//            return this;
//        }

        public LocalResourceBuilder withPath(String path) {
            localResource.setPath(path);
            return this;
        }

        public LocalResourceBuilder withDuration(Duration duration) {
            localResource.setDuration(duration);
            return this;
        }

        public LocalResource build() {
            return localResource;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean needsTrancode() {
        log.warn("{} : needsTrancode ", Constants.TODO);
        return isVideo();
    }

    @Override
    public String getHash() {
        return Constants.Resource.HASH_FUNCTION
                .hashString(String.format("LOCAL|%s|%s", String.valueOf(getType()), getPath()), StandardCharsets.UTF_8)
                .toString();
    }
}
