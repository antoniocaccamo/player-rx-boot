package me.antoniocaccamo.player.rx.model.resource;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Duration;

/**
 * @author antoniocaccamo on 18/02/2020
 */

@Getter
@Setter
@Entity @Slf4j
@DiscriminatorValue(Constants.Resource.Location.Remote)
public class RemoteResource extends Resource {

    public static RemoteResourceBuilder builder() {
        return new RemoteResourceBuilder();
    }

    @Column
    private Constants.Resource.Remote remote;

    public Constants.Resource.Remote getRemote() {
        return remote;
    }

    public void setRemote(Constants.Resource.Remote remote) {
        this.remote = remote;
    }

    @Override @Transient
    public Path getLocalPath() {
        return null;
    }

    @Override
    public boolean isLocal() {
        return false;
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


    public static final class RemoteResourceBuilder {
        private RemoteResource remoteResource;

        private RemoteResourceBuilder() {
            remoteResource = new RemoteResource();
        }

        public static RemoteResourceBuilder aLocalResource() {
            return new RemoteResourceBuilder();
        }

        public RemoteResourceBuilder withType(Constants.Resource.Type type) {
            remoteResource.setType(type);
            return this;
        }

        public RemoteResourceBuilder withRemote(Constants.Resource.Remote remote) {
            remoteResource.setRemote(remote);
            return this;
        }

//        public RemoteResourceBuilder withLocation(LOCATION location) {
//            localResource.setLocation(location);
//            return this;
//        }

        public RemoteResourceBuilder withPath(String path) {
            remoteResource.setPath(path);
            return this;
        }

        public RemoteResourceBuilder withDuration(Duration duration) {
            remoteResource.setDuration(duration);
            return this;
        }

        public RemoteResource build() {
            return remoteResource;
        }
    }

    @Override
    public String getHash() {
        return Constants.Resource.HASH_FUNCTION
                .hashString(String.format("REMOTE|%s|%s|s", String.valueOf(getType()), String.valueOf( getRemote()), getPath()), StandardCharsets.UTF_8)
                .toString();
    }
}
