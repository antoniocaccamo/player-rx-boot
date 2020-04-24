package me.antoniocaccamo.player.rx.ui.monitor;

import com.diffplug.common.swt.ColorPool;
import com.diffplug.common.swt.Layouts;
import me.antoniocaccamo.player.rx.ui.AbstractUI;
import me.antoniocaccamo.player.rx.ui.MonitorUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * @author antoniocaccamo on 19/02/2020
 */
public class WatchUI extends AbstractUI {
    public WatchUI(MonitorUI monitorUI, Composite wrapped) {
        super(monitorUI, wrapped);

        Label label = new Label(this, SWT.NONE);
        label.setText(getClass().getSimpleName());
        Layouts.setGridData(label).grabAll();
    }
}
