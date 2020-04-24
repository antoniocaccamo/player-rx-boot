package me.antoniocaccamo.player.rx.model.sequence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.Model;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonPropertyOrder({
        "id",
        "location",
        "type",
        "path",
        "daysOfWeek",
        "limited",
        "start",
        "end",
        "from",
        "to",
        "resource",
        "duration"
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MEDIA") @Slf4j
public class Media implements Playable, Cloneable{

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="MEDIA_SEQ")
    @SequenceGenerator(name="MEDIA_SEQ", sequenceName="MEDIA_SEQ", allocationSize=1)
    protected Long id;

    @Transient
    private Model.Location location;

    @Transient
    private Model.Type         type;

    @Transient
    private String             path;

    @Column
    @JsonSerialize(using = DurationSerializer.class)
    @JsonDeserialize(using = DurationDeserializer.class)
    private Duration       duration;

    @Column(name = "DAYS_OF_WEEK", length = 7)
    private String daysOfWeek;

    @Column(name = "LIMITED")
    private Integer limited;

    @Column(name = "DATE_START")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate start;

    @Column(name = "DATE_END")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate end;

    @Column(name = "TIME_FROM")
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime from;

    @Column(name = "TIME_TO")
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime to;

    @ManyToOne
    @JoinColumn(name="RESOURCE_ID")
    private Resource resource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Model.Location getLocation() {
        return location;
    }

    public void setLocation(Model.Location location) {
        this.location = location;
    }

    public Model.Type getType() {
        return type;
    }

    public void setType(Model.Type type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getLimited() {
        return limited;
    }

    public void setLimited(Integer limited) {
        this.limited = limited;
    }

    public Duration getDuration() {
        return duration == null ? resource.getDuration() : duration;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public LocalTime getFrom() {
        return LocalTime.of(9,0);
    }

    public void setFrom(LocalTime from) {
        this.from = from;
    }

    public LocalTime getTo(){
        return LocalTime.of(20,0);
    }

    public void setTo(LocalTime to) {
        this.to = to;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
        if ( getResource().isVideo())
            getResource().setDuration(duration);
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }


    //    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Model.Location getLocation() {
//        return location;
//    }
//
//    public void setLocation(Model.Location location) {
//        this.location = location;
//    }
//
//    public Model.Type getType() {
//        return type;
//    }
//
//    public void setType(Model.Type type) {
//        this.type = type;
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public Duration getDuration() {
//        return duration;
//    }
//
//    public void setDuration(Duration duration) {
//        this.duration = duration;
//    }
//
//    public Resource getResource() {
//        return resource;
//    }
//
//    public void setResource(Resource resource) {
//        this.resource = resource;
//    }



    @Override
    public void prepareForPlay() {
        log.warn("{} : prepareForPlay ", Constants.TODO);
    }


    public boolean isPlayable(LocalDateTime now) {
        log.warn("{} : isPlayable ", Constants.TODO);
        return true;
    }

    @JsonIgnore
    public boolean isAvailable() {
        log.warn("{} : isAvailable ", Constants.TODO);
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("location", location)
                .append("type", type)
                .append("path", path)
                .append("duration", duration)
                .append("daysOfWeek", daysOfWeek)
                .append("limited", limited)
                .append("start", start)
                .append("end", end)
                .append("from", from)
                .append("to", to)
                .append("resource", resource)
                .toString();
    }
}
