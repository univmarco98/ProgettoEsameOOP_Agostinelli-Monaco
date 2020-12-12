package application;

import application.file.File;
//importiamo le nostri classi
import application.json.ApiHandler;
import application.json.JsonHandler;
import application.utility.ArrayType;
import application.utility.Time;

import java.util.Iterator;
import java.util.Vector;

//import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ProgettoEsameOopAgostinelliMonacoApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ProgettoEsameOopAgostinelliMonacoApplication.class, args);
		
		//eseguo chiamata a list folder e formatto la risposta
		String strlistfolder=new ApiHandler().apicall_list_folder("");
		Vector<JSONObject> jsonlistfile=JsonHandler.format_list_folder(strlistfolder);
		
		//creo i 3 array di tipo diverso
		Iterator<JSONObject> iterator =(Iterator<JSONObject>) jsonlistfile.iterator();
		ArrayType Array_diviso_per_tipo= new ArrayType();
		while(iterator.hasNext()) {
			Array_diviso_per_tipo.add_element(iterator.next());
		}
		
		
		//devo fare i get nmetadata solo dei file, creo iteratore per scorrere il vector che li contiene
		Vector<File> vectorFile = (Vector<File>) Array_diviso_per_tipo.get_vector_file();
		Iterator<File> file =(Iterator<File>) vectorFile.iterator();
		
		
		while (file.hasNext() ) {
			File temp=file.next();
			
			//effettuo chiama e formattazione get metadata
			String path=temp.get_path();
			String strgetmetadatar=new ApiHandler().apicall_get_metadata(path);
			JSONObject jsongetmetadata=JsonHandler.format_get_metadata(strgetmetadatar);
			
			//aggiorno temp(elemento in vectorfile->l'oggetto file) con le nuove informazioni
			temp.set_revision(jsongetmetadata.get("rev").toString());
			temp.set_size((int) Integer.parseInt( jsongetmetadata.get("size").toString()   ) );
			//strasformo in int una cosa castata in string che proviene da un oggetto json
			//troppo brutto ma funzionale
			
			//formatto la stringa dell'orario in modo comodo
			//strlastmod = 2020-11-12T08:08:13Z --> 2020-11-12-08-08-13
			String strlastmod=jsongetmetadata.get("server_modified").toString();
			strlastmod=strlastmod.replace("T", "-");
			strlastmod=strlastmod.replace("Z", "");
			strlastmod=strlastmod.replace(":", "-"); 
			
			String[] veclastmod=strlastmod.split("-");
			Time lastmod=new Time();
			
			lastmod.set_year(Integer.parseInt(veclastmod[0]) );
			lastmod.set_month(Integer.parseInt(veclastmod[2]) );
			lastmod.set_day(Integer.parseInt(veclastmod[3]) );
			lastmod.set_hour(Integer.parseInt(veclastmod[4]) );
			lastmod.set_minute(Integer.parseInt(veclastmod[5]) );
			
			temp.set_lastModify(lastmod);
		}//fine while
		
		
		//mi faccio scrivere tutti gli oggetti file che ha trovato
		Iterator<File> file2 =(Iterator<File>) vectorFile.iterator();
		
		while (file2.hasNext() ) {
			File temp=file2.next();
			
			System.out.println(temp.get_name());
		}//while
	System.out.println("Ho finito");
	}//main

}
