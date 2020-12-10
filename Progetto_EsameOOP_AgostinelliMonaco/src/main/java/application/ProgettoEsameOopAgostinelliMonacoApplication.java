package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import application.json.*;

@SpringBootApplication
public class ProgettoEsameOopAgostinelliMonacoApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ProgettoEsameOopAgostinelliMonacoApplication.class, args);
		System.out.println("ciao");
		JsonHandler myJsonH=new JsonHandler();
	  
		//myJsonH.chiamataAPI();
		myJsonH.salvaFile("pippoh.txt");
	}

}
