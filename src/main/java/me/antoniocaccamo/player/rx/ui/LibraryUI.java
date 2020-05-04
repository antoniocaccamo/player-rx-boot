package me.antoniocaccamo.player.rx.ui;

import com.diffplug.common.rx.RxBox;
import com.diffplug.common.swt.Layouts;
import com.diffplug.common.swt.jface.ColumnViewerFormat;
import com.diffplug.common.swt.jface.ViewerMisc;
import io.reactivex.subjects.PublishSubject;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.Application;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import me.antoniocaccamo.player.rx.service.ResourceService;
import me.antoniocaccamo.player.rx.ui.library.PreviewLibraryUI;
import me.antoniocaccamo.player.rx.ui.library.ResourceLibraryUI;
import me.antoniocaccamo.player.rx.ui.library.SequenceLibraryUI;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import java.util.Optional;

/**
 * @author antoniocaccamo on 02/03/2020
 */
@Slf4j
public class LibraryUI extends Composite {

    private final PublishSubject<Resource> resourcePublishSubject;

    public LibraryUI(Composite parent) {
        super(parent, SWT.NONE);

        resourcePublishSubject = PublishSubject.create();

        Layouts.setGrid(this).numColumns(1).columnsEqualWidth(false).margin(0).spacing(0);

        SashForm parentSashForm = new SashForm(this, SWT.HORIZONTAL);
        Layouts.setGridData(parentSashForm).grabAll();

        SashForm sashForm = new SashForm(parentSashForm, SWT.VERTICAL);
        //Layouts.setGridData(sashForm).grabAll();

        //Layouts.setGridData(new ResourceLibraryUI(sashForm)).grabAll();

        //Layouts.setGridData(new ResourceLibraryUI(sashForm)).grabAll();

        new ResourceLibraryUI(sashForm, resourcePublishSubject);
        new SequenceLibraryUI(sashForm, resourcePublishSubject);


        sashForm = new SashForm(parentSashForm, SWT.VERTICAL);
        //Layouts.setGridData(sashForm).grabAll();

        //Layouts.setGridData(new ResourceLibraryUI(sashForm)).grabAll();

        //Layouts.setGridData(new ResourceLibraryUI(sashForm)).grabAll();

        new PreviewLibraryUI(sashForm, resourcePublishSubject);

        int [] weights = {3,1};
        parentSashForm.setWeights(weights);

    }
}
