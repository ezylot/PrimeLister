package at.ezylot.primelister;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    WorkScheduler workScheduler;

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new SpringApplicationBuilder()
                .sources(Application.class)
                .bannerMode(Banner.Mode.OFF)
            .run();

        context.getBean(Application.class).start();
    }

    public void start() throws InterruptedException {
        logger.info("Starting search for primes.");
        workScheduler.startBlocking();
    }

}
