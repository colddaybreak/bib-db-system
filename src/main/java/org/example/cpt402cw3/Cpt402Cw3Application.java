package org.example.cpt402cw3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@SpringBootApplication
public class Cpt402Cw3Application {

    public static void main(String[] args) {
        SpringApplication.run(Cpt402Cw3Application.class, args);
    }

}
