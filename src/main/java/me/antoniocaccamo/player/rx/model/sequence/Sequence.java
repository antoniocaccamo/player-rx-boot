package me.antoniocaccamo.player.rx.model.sequence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.model.Model;
import me.antoniocaccamo.player.rx.model.preference.LoadedSequence;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;

import javax.annotation.PostConstruct;
//import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@NoArgsConstructor @AllArgsConstructor
@Builder @Slf4j @Getter @Setter
//@Entity
//@Table(name = "SEQUENCE")
public class Sequence implements Cloneable, Playable {

//    @Id
//    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="SEQUENCE_SEQ")
//    @SequenceGenerator(name="SEQUENCE_SEQ", sequenceName="SEQUENCE_SEQ", allocationSize=1)
    protected Long id;

//    @Column(nullable = false, unique = true)
    private String name;

//    @Transient
    private Model.Location location;

//    @OneToMany(fetch = FetchType.EAGER)
//    @Fetch(FetchMode.SUBSELECT)
//    @JoinTable(
//            name = "SEQUENCE_MEDIA",
//            joinColumns        = { @JoinColumn(name = "SEQUENCE_ID", referencedColumnName = "ID")},
//            inverseJoinColumns = { @JoinColumn(name = "MEDIA_ID"   , referencedColumnName = "ID")}
//    )
    @Singular
    private List<Media> medias;

    @JsonIgnore
    private LoadedSequence loadedSequence;

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct ..");
        prepareForPlay();
    }
/*
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Model.Location getLocation() {
        return location;
    }

    public void setLocation(Model.Location location) {
        this.location = location;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }
*/

    @Override
    public Sequence clone() {
        Sequence sequence = null;
        try {
            sequence = this.clone();
            return sequence;
        } catch (Exception e) {
            log.error("error occurred", e);
            sequence = Sequence.builder()
                    .name("errorrr")
                    .medias(Collections.emptyList())
                    .build();
        } finally {
            return sequence;
        }
    }

//    @JsonIgnore @Builder.Default @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) @Transient
//    private final Lock MY_LOCK = new ReentrantLock();
//    @JsonIgnore @Builder.Default @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) @Transient
//    private  int _loop    = 0;
//    @JsonIgnore @Builder.Default @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) @Transient
//    private  int _current = 0;
//    @JsonIgnore @Builder.Default @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) @Transient
//    private  int _next    = -1;

//    public Media next()   {
//        return next(LocalDateTime.now());
//    }
//
//    private Media next(final LocalDateTime now) {
//        boolean found = false;
//        Media media = null;
//        MY_LOCK.lock();
//        try {
//            while ( ! found ) {
//                _next = ++ _next % getMedias().size();
//                media = getMedias().get(_next);
//                if (media.isPlayable(now)) {
//                    found = true;
//                } else {
//                    log.warn("@@ -- TODO --");
//                    found = true;
//                }
//                _loop += _next == 0 ? 1 : 0;
//            }
//        } finally {
//            MY_LOCK.unlock();
//        }
//        return media;
//    }

    @Override
    public void prepareForPlay() { }

    public boolean isPlayable(LocalDateTime now) {
        log.warn("--> to udpate ...");
        return getMedias() != null ;
    }

    @JsonIgnore
    public boolean isAvailable() {
        log.warn("--> to udpate ...");
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("hashCode", hashCode())
                .append("id", id)
                .append("name", name)
                .append("location", location)
                .append("medias", medias)
                .toString();
    }
}
