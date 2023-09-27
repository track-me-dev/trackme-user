package com.app.trackmeuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TrackmeUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackmeUserApplication.class, args);
    }

}
