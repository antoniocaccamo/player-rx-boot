package me.antoniocaccamo.player.rx.ui.library;

import me.antoniocaccamo.player.rx.model.preference.LoadedSequence;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import org.apache.commons.lang3.StringUtils;

/**
 * @author antoniocaccamo on 05/05/2020
 */
public class SequenceLibraryUITreeTableLabelProvider extends SequenceLibraryUITableLabelProvider {

    public String getColumnText(Object element, int columnIndex) {
        if ( element instanceof Media)
            return super.getColumnText(element, columnIndex);
        LoadedSequence loadedSequence = (LoadedSequence) element;
        switch (columnIndex) {
            case 0:
                return loadedSequence.getName();
            case 1:
                return loadedSequence.getPath().toFile().getAbsolutePath();
            case 2:
                return String.valueOf(loadedSequence.getSequence().getMedias().size());
            default:
                return StringUtils.EMPTY;
        }
    }
}
