package me.antoniocaccamo.player.rx.ui.library;

import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author antoniocaccamo on 05/05/2020
 */
public class SequenceLibraryUITableLabelProvider implements ITableLabelProvider {
    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
        Media media = (Media) element;

        switch (columnIndex) {
            case 0:
                return media.getResource().getType().toString();
            case 1:
                return media.getResource().getPath();
            case 2:
                return media.getDuration().toString();
            default:
                return StringUtils.EMPTY;
        }
    }

    @Override
    public void addListener(ILabelProviderListener listener) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {

    }
}
