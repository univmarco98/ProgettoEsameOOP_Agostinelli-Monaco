package application;
import java.util.Iterator;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import application.utility.ArrayType;
import application.file.*;
import application.json.JsonHandler;

@SpringBootApplication
public class ProgettoEsameOopAgostinelliMonacoApplication {

	public static void main(String[] args) {
		
		//SpringApplication.run(ProgettoEsameOopAgostinelliMonacoApplication.class, args);
		//Routine_GetAndSave_Datas.routine_run();
		ArrayType aT=JsonHandler.caricaFile("20201215.txt");
		Iterator<File> fileI =(Iterator<File>) aT.get_vector_file().iterator();
				
		while (fileI.hasNext() ) {
			File temp=fileI.next();
			System.out.println(temp.get_name());
		}//*/
	}//main

}
