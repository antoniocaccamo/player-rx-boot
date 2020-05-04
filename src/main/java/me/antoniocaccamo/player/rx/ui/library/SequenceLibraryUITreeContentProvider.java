package me.antoniocaccamo.player.rx.ui.library;

import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.model.preference.LoadedSequence;
import me.antoniocaccamo.player.rx.model.sequence.Media;
import org.eclipse.jface.viewers.ITreeContentProvider;

import java.util.stream.Stream;


/**
 * @author antoniocaccamo on 05/05/2020
 */
@Slf4j
public class SequenceLibraryUITreeContentProvider implements ITreeContentProvider {

    private static final Object[] EMPTY = new Object[] {};

    @Override
    public Object[] getElements(Object inputElement) {
        Iterable<LoadedSequence> loadedSequences = (Iterable<LoadedSequence>) inputElement;
        return Observable.fromIterable(loadedSequences)
                .toList().blockingGet().toArray();
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if ( parentElement instanceof LoadedSequence){
            LoadedSequence loadedSequence = (LoadedSequence) parentElement;
            return loadedSequence.getSequence().getMedias().toArray();
        }

        return EMPTY;
    }

    @Override
    public Object getParent(Object element) {
        if ( element instanceof Media)
            return ((Media) element ).getSequence().getLoadedSequence();
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        return getChildren(element).length > 0;
    }
}
