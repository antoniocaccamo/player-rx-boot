package me.antoniocaccamo.player.rx.model.resource;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

//import javax.persistence.*;
import java.nio.file.Path;
import java.time.Duration;

/**
 * @author antoniocaccamo on 18/02/2020
 */
@Getter @Setter
//@Inheritance @DiscriminatorColumn(name = "LOCATION")
//@Entity
//@Table(name = "RESOURCE")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "resource-type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LocalResource.class, name = "local"),
        @JsonSubTypes.Type(value = RemoteResource.class, name = "remote")
}) @Slf4j
public abstract class Resource {

 //   @Id
 //   @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="RESOURCE_SEQ")
 //   @SequenceGenerator(name="RESOURCE_SEQ", sequenceName="RESOURCE_SEQ", allocationSize=1)
    protected Long id;

 //   @Column
    protected me.antoniocaccamo.player.rx.config.Constants.Resource.Type type;

    //@Column
    //protected LOCATION location;

 //   @Column
    protected String path;

//    @Column
    @JsonSerialize(using = DurationSerializer.class)
    @JsonDeserialize(using = DurationDeserializer.class)
    protected Duration duration;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public me.antoniocaccamo.player.rx.config.Constants.Resource.Type getType() {
        return type;
    }

    public void setType(me.antoniocaccamo.player.rx.config.Constants.Resource.Type type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

//    @Transient
    @JsonIgnore
    public abstract Path getLocalPath() ;

    //@Transient @JsonIgnore
    //public abstract Path getHLSPath() ;

//   @Transient
    @JsonIgnore
    public boolean isVideo(){
        return me.antoniocaccamo.player.rx.config.Constants.Resource.Type.VIDEO.equals(getType());
    }

//  @Transient
    @JsonIgnore
    public abstract boolean isLocal();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("type", type)
                .append("path", getPath())
                .append("duration", getDuration())
                .append("hash", getHash())
                .append("localpath", getLocalPath())
                .toString();
    }
//  @Transient
    @JsonIgnore
    public abstract boolean needsTrancode();

 //  @Transient
    @JsonIgnore
    public abstract String getHash();


    @JsonIgnore
    public boolean isAvailable() {
        log.warn("{} : isAvailable ", Constants.TODO);
        return true;
    }
}
