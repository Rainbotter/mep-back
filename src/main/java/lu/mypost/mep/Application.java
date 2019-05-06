package lu.mypost.mep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"lu.mypost.mep"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
