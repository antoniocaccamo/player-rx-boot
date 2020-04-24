package me.antoniocaccamo.player.rx.model.preference;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.helper.SWTHelper;
import me.antoniocaccamo.player.rx.model.Model;
import me.antoniocaccamo.player.rx.model.PointSWT;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.swt.graphics.Point;

/**
 * @author antoniocaccamo on 07/02/2020
 */
@Slf4j
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @Builder
public class ScreenLocation extends Model implements PointSWT<ScreenLocation> {

    @JsonProperty("x")
    private int top;

    @JsonProperty("y")
    private int left;

    @Override
    public Point toPoint() {
        return SWTHelper.toPoint(left, top);
    }

    @Override
    public ScreenLocation fromPoint(Point point) {
        top  = point.y;
        left = point.x;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("top", top)
                .append("left", left)
                .toString();
    }
}
