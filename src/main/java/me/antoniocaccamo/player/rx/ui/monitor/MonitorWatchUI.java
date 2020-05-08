package me.antoniocaccamo.player.rx.ui.monitor;

import com.diffplug.common.swt.Layouts;
import me.antoniocaccamo.player.rx.ui.ScreenUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * @author antoniocaccamo on 19/02/2020
 */
public class MonitorWatchUI extends AbstractMonitorUI {
    public MonitorWatchUI(ScreenUI screenUI, Composite wrapped) {
        super(screenUI, wrapped);

        Label label = new Label(this, SWT.NONE);
        label.setText(getClass().getSimpleName());
        Layouts.setGridData(label).grabAll();
    }
}
