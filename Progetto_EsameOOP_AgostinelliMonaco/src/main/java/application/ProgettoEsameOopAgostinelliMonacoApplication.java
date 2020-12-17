package application;
import java.util.Iterator;
import java.util.Vector;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import application.utility.ArrayType;
import stats_and_filtres.Statistics;
import application.file.*;
import application.json.JsonHandler;

@SpringBootApplication
public class ProgettoEsameOopAgostinelliMonacoApplication {

	public static void main(String[] args) {
		
		//SpringApplication.run(ProgettoEsameOopAgostinelliMonacoApplication.class, args);
		//Routine_GetAndSave_Datas.routine_run();
		
		//il file 20201217 e' un aggiornamento fatto nel 16 modificanto la cartella Nuova Cartella
		
		Statistics.difference("20201216","20201217");
		
		
		
/*commenta qui con /* per commentare la parte del folder*/
	/*	
		System.out.println("confronto folder");
		
		Vector<Folder> folder = JsonHandler.caricaFile("20201217.txt").get_vector_folder();
		int_del.clear();
		Iterator<Folder> folI2 = (Iterator<Folder>) aT2.get_vector_folder().iterator();
		
		i=0;
		while(folI2.hasNext()) {
			Folder tmp2 = folI2.next();
			
			Iterator<Folder> folI1=(Iterator<Folder>) aT1.get_vector_folder().iterator();
			while (folI1.hasNext() ) {
				Deleted tmp1=folI1.next();
				
				if( tmp1.get_path().equalsIgnoreCase(tmp2.get_path() ) ) {
					int_del.add(i);
					//System.out.println(tmp2+"  "+tmp1);
					//System.out.println(deleted.remove(0));
					break;
				}
					
			}	
			i++;
		}
		
		while ( int_del.size()!=0) {	
			int tmp = int_del.lastElement();
			int_del.remove(int_del.lastElement());
			folder.remove(tmp);
		}
		
		
		System.out.println( folder.size() );
		//System.out.println( deleted.size() );
		System.out.println( folder.get(0).get_path());
		System.out.println("bu");//*/
		
		
		/*System.out.println("confronto file");
		//Vector<File> file1 = aT1.get_vector_file();
		Vector<File> file2 = aT2.get_vector_file();
		Iterator<File> file=(Iterator<File>) aT1.get_vector_file().iterator();
		while (file.hasNext() ) {
			file.next();
			if( file2.contains(file) )
				file2.remove(file);
			//controllare last modify
		}
		
		System.out.println( deleted2.size());//+folder2.toString()+file.toString() );
		
		
		
		System.out.println("Ho finito");
		
		
		
		
		
		
		/*Iterator<File> fileI =(Iterator<File>) aT1.get_vector_file().iterator();
				
		while (fileI.hasNext() ) {
			File temp=fileI.next();
			System.out.println(temp.get_name());
		}//*/
	}//main

}
