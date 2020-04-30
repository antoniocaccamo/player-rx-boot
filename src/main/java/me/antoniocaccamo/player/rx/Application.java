package me.antoniocaccamo.player.rx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@SpringBootApplication @Slf4j
public class Application {

    public static int SERVER_PORT = 8080;
    static public ConfigurableApplicationContext CONTEXT = null;

	public static void main(String[] args) {
		CONTEXT = SpringApplication.run(Application.class, args);
		final AtomicInteger counter = new AtomicInteger(0);

		log.info("arguments : ");
		Stream.of(args).forEach(
				s -> log.info("\t{}", s)
		);

		log.debug("**************** START: Total Bean Objects: {} ******************", CONTEXT.getBeanDefinitionCount());

		List<String> beans =Arrays.asList(CONTEXT.getBeanDefinitionNames());
		beans.sort( (o1, o2) -> o1.compareTo(o2) );
		beans.stream().forEach(
				beanName -> {
					log.debug("{}) Bean Name: {} ", counter.incrementAndGet(), beanName);
				});

		log.debug("**************** END: Total Bean: {} ******************", CONTEXT.getBeanDefinitionCount());
		
		ApplicationUI mainUI = CONTEXT.getBean(ApplicationUI.class);
		try {
			mainUI.show();
		} catch (Exception e) {
			log.error("error => ", e);
			System.exit(SpringApplication.exit(CONTEXT, () -> 0 ));
		}
	
	}

	/*@EventListener
	public void onApplicationReady(ApplicationReadyEvent applicationReadyEvent){

		final AtomicInteger counter = new AtomicInteger(0);
		log.info("**************** START: Total Bean Objects: {} ******************", CONTEXT.getBeanDefinitionCount());

		Arrays.asList(CONTEXT.getBeanDefinitionNames())
				.forEach(beanName -> {
					log.info("{}) Bean Name: {} ", counter.incrementAndGet(), beanName);
				});

		log.info("**************** END: Total Bean: {} ******************", CONTEXT.getBeanDefinitionCount());
		
		log.info("event receive : {}", applicationReadyEvent);




		Executors.newSingleThreadScheduledExecutor().execute( () -> {

					*//*
					Shells.builder(SWT.RESIZE | SWT.ICON | SWT.CLOSE, cmp -> {
						log.info("launching swt");
						Layouts.setGrid(cmp)
								.numColumns(1)
								.columnsEqualWidth(true)
								.horizontalSpacing(0)
								.verticalSpacing(0)
						;

						SwtRx.addListener(cmp, SWT.Dispose)
								.subscribe(event -> {
									log.info("closing application");
									System.exit(SpringApplication.exit(CONTEXT, () -> 0 ));
								});


					})
							.setTitle(applicationReadyEvent.getApplicationContext().getApplicationName())
							.setSize( 400, 300)
							.setLocation( new Point(200, 100))
							.openOnDisplayBlocking();
					*//*

			ApplicationUI mainUI = CONTEXT.getBean(ApplicationUI.class);
					mainUI.show();
				}

		);
	}*/

}
