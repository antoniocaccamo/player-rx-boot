package me.antoniocaccamo.player.rx.model;

import org.eclipse.swt.graphics.Point;

/**
 * @author antoniocaccamo on 18/02/2020
 */
public interface PointSWT<T> {

    Point toPoint();

    T fromPoint(Point point);
}
