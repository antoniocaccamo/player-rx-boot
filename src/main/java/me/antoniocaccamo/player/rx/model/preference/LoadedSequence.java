package me.antoniocaccamo.player.rx.model.preference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.nio.file.Path;

/**
 * @author antoniocaccamo on 30/04/2020
 */

@Slf4j
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadedSequence {

    private String name;

    private Path   path;

    @JsonIgnore
    private Sequence sequence;

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
        sequence.setLoadedSequence(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("name", name)
                .append("path", path)
                .append("sequence", sequence)
                .toString();
    }
}
