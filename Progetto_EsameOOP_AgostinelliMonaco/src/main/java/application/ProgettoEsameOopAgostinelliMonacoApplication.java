package application;
import java.util.Iterator;
import java.util.Vector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import application.utility.ArrayType;
import stats_and_filters.Statistics;
import application.file.*;
import application.json.JsonHandler;

@SpringBootApplication
public class ProgettoEsameOopAgostinelliMonacoApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ProgettoEsameOopAgostinelliMonacoApplication.class, args);
		
		//il file 20201217 e' un aggiornamento fatto nel 16 modificanto la cartella Nuova Cartella
		
		//Statistics.difference("20201216","20201217");
		
	}//main

}
