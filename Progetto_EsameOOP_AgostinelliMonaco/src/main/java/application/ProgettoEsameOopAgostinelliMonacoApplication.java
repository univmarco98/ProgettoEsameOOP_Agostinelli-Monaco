package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import application.json.*;

@SpringBootApplication
public class ProgettoEsameOopAgostinelliMonacoApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ProgettoEsameOopAgostinelliMonacoApplication.class, args);
		JsonHandler myJsnoH=new JsonHandler();
		myJsnoH.chiamataAPI();
		myJsnoH.salvaFile("pippoh.txt");
	}

}
