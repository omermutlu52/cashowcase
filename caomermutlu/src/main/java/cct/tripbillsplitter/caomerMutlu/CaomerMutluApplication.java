package cct.tripbillsplitter.caomerMutlu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("cct.tripbillsplitter*")
public class CaomerMutluApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaomerMutluApplication.class, args);
	}

}
