package me.antoniocaccamo.player.rx.ui;

import com.diffplug.common.rx.RxBox;
import com.diffplug.common.swt.Layouts;
import com.diffplug.common.swt.jface.ColumnViewerFormat;
import com.diffplug.common.swt.jface.ViewerMisc;
import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.Application;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import me.antoniocaccamo.player.rx.service.ResourceService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import java.util.Optional;

/**
 * @author antoniocaccamo on 02/03/2020
 */
@Slf4j
public class ResourceLibraryUI extends Composite {

    public ResourceLibraryUI(Composite parent) {
        super(parent, SWT.NONE);


        Layouts.setGrid(this);


        SashForm psashForm = new SashForm(this, SWT.HORIZONTAL);
        Layouts.setGridData(psashForm).grabAll();

        SashForm sashForm = new SashForm(psashForm, SWT.VERTICAL);


//        Layouts.setGridData(sashForm).grabAll();


//        Button button2 = new Button(sashForm, SWT.NONE);
//        button2.setText("Button 2");
//
//        Button button3 = new Button(sashForm, SWT.NONE);
//        button3.setText("Button 3");
//       sashForm.setWeights(new int[] { 2, 1});

        ResourceService resourceService = Application.CONTEXT.getBean(ResourceService.class);

        Group group = new Group(sashForm, SWT.NONE);
        //Layouts.setGrid(group);
        //Layouts.setGridData(group).grabAll();
        group.setText("resources");
        //Layouts.setFill(group);

        ColumnViewerFormat<Resource> format = ColumnViewerFormat.builder();
        format.addColumn().setText("id").setLabelProviderText(r -> String.valueOf(r.getId()));
        format.addColumn().setText("location").setLabelProviderText(r-> r.getClass().getSimpleName());
        format.addColumn().setText("type").setLabelProviderText(r-> r.getType().name());
        format.addColumn().setText("duration").setLabelProviderText(r->r.getDuration() != null ?r.getDuration().toString(): "");
        format.addColumn().setText("path").setLabelProviderText(r->r.getPath());
        format.setStyle( SWT. FULL_SELECTION);

        TableViewer tableViewer = format.buildTable(group);
        tableViewer.setContentProvider(new ArrayContentProvider());
        RxBox<Optional<Resource>> resourceRxBox  = ViewerMisc.singleSelection(tableViewer);
        resourceRxBox.asObservable().subscribe(or -> or.ifPresent( r->log.info("selected : {}", r)));



        tableViewer.setInput(resourceService.getResources());

        group = new Group(sashForm, SWT.NONE);
        //Layouts.setGrid(group);
        //Layouts.setGridData(group).grabAll();
        group.setText("resources");
        //Layouts.setFill(group);

        format = ColumnViewerFormat.builder();
        format.addColumn().setText("id").setLabelProviderText(r -> String.valueOf(r.getId()));
        format.addColumn().setText("location").setLabelProviderText(r-> r.getClass().getSimpleName());
        format.addColumn().setText("type").setLabelProviderText(r-> r.getType().name());
        format.addColumn().setText("duration").setLabelProviderText(r->r.getDuration() != null ?r.getDuration().toString(): "");
        format.addColumn().setText("path").setLabelProviderText(r->r.getPath());
        format.setStyle( SWT. FULL_SELECTION);

        tableViewer = format.buildTable(group);
        tableViewer.setContentProvider(new ArrayContentProvider());
        resourceRxBox  = ViewerMisc.singleSelection(tableViewer);
        resourceRxBox.asObservable().subscribe(or -> or.ifPresent( r->log.info("selected : {}", r)));


        tableViewer.setInput(resourceService.getResources());

        // left
        sashForm = new SashForm(psashForm, SWT.VERTICAL);

        group = new Group(sashForm, SWT.NONE);
        //Layouts.setGrid(group);
        //Layouts.setGridData(group).grabAll();
        group.setText("resources");
        //Layouts.setFill(group);

        format = ColumnViewerFormat.builder();
        format.addColumn().setText("id").setLabelProviderText(r -> String.valueOf(r.getId()));
        format.addColumn().setText("location").setLabelProviderText(r-> r.getClass().getSimpleName());
        format.addColumn().setText("type").setLabelProviderText(r-> r.getType().name());
        format.addColumn().setText("duration").setLabelProviderText(r->r.getDuration() != null ?r.getDuration().toString(): "");
        format.addColumn().setText("path").setLabelProviderText(r->r.getPath());
        format.setStyle( SWT. FULL_SELECTION);

        tableViewer = format.buildTable(group);

        tableViewer.setContentProvider(new ArrayContentProvider());
        resourceRxBox  = ViewerMisc.singleSelection(tableViewer);
        resourceRxBox.asObservable().subscribe(or -> or.ifPresent( r->log.info("selected : {}", r)));


        tableViewer.setInput(resourceService.getResources());

        group = new Group(sashForm, SWT.NONE);
        //Layouts.setGrid(group);
        //Layouts.setGridData(group).grabAll();
        group.setText("resources");
        //Layouts.setFill(group);

        format = ColumnViewerFormat.builder();
        format.addColumn().setText("id").setLabelProviderText(r -> String.valueOf(r.getId()));
        format.addColumn().setText("location").setLabelProviderText(r-> r.getClass().getSimpleName());
        format.addColumn().setText("type").setLabelProviderText(r-> r.getType().name());
        format.addColumn().setText("duration").setLabelProviderText(r->r.getDuration() != null ?r.getDuration().toString(): "");
        format.addColumn().setText("path").setLabelProviderText(r->r.getPath());
        format.setStyle( SWT. FULL_SELECTION);

        tableViewer = format.buildTable(group);
        tableViewer.setContentProvider(new ArrayContentProvider());
        resourceRxBox  = ViewerMisc.singleSelection(tableViewer);
        resourceRxBox.asObservable().subscribe(or -> or.ifPresent( r->log.info("selected : {}", r)));


        tableViewer.setInput(resourceService.getResources());

        psashForm.setWeights( new int[]{3,1});


    }
}
