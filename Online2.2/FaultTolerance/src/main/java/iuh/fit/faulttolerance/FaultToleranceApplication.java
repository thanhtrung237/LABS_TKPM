package iuh.fit.faulttolerance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FaultToleranceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaultToleranceApplication.class, args);
    }

}
