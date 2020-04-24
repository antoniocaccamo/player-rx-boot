package me.antoniocaccamo.player.rx.model.preference;

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
@Getter @Setter @NoArgsConstructor @Builder
@AllArgsConstructor
public class ScreenSize extends Model implements PointSWT<ScreenSize> {

    private int width;

    private int height;

    @Override
    public Point toPoint() {
        return SWTHelper.toPoint(width, height);
    }

    @Override
    public ScreenSize fromPoint(Point point) {
        width  = point.x;
        height = point.y;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("width", width)
                .append("height", height)
                .toString();
    }
}
