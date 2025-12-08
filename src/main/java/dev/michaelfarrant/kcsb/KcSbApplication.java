package dev.michaelfarrant.kcsb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class KcSbApplication {

    public static void main(String[] args) {
        SpringApplication.run(KcSbApplication.class, args);
    }

}
