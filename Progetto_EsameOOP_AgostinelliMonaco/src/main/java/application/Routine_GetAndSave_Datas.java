package application;

import java.util.Iterator;
import java.util.Vector;

import org.json.simple.JSONObject;

import application.file.File;
import application.json.ApiHandler;
import application.json.JsonHandler;
import application.utility.ArrayType;
import application.utility.FileHandler;
import application.utility.Time;

public class Routine_GetAndSave_Datas {
	public static void routine_run() {
		//eseguo chiamata a list folder e formatto la risposta
		ApiHandler api=new ApiHandler();
		System.out.println(api.getToken());
		
		String strlistfolder=api.apicall_list_folder("");
		
		Vector<JSONObject> jsonlistfile=JsonHandler.format_list_folder(strlistfolder);
		
		JSONObject info_per_continuare = jsonlistfile.lastElement();
		jsonlistfile.remove(jsonlistfile.lastElement());
		
		api.setCursor( (String) info_per_continuare.get("cursor") );
		api.setHasMore( (String) info_per_continuare.get("has_more") );
		
		//creo i 3 array di tipo diverso
		Iterator<JSONObject> iterator =(Iterator<JSONObject>) jsonlistfile.iterator();		
		ArrayType Array_diviso_per_tipo= new ArrayType();
		while(iterator.hasNext()) {
			Array_diviso_per_tipo.add_element(iterator.next());
		}
		int a=0;
		while ( Boolean.parseBoolean(info_per_continuare.get("has_more").toString() )) {
			strlistfolder=api.apicall_list_folder_continue();
			
			if(a==0)
				System.out.print("\nLoading");
			else
				System.out.print("..");
			a++;
			if(a==13)
				a=0;
			
			Vector<JSONObject> jsonlistfile1=JsonHandler.format_list_folder(strlistfolder);

			info_per_continuare = jsonlistfile1.lastElement();
			jsonlistfile1.remove(jsonlistfile1.lastElement());

			api.setCursor( (String) info_per_continuare.get("cursor") );
			api.setHasMore( (String) info_per_continuare.get("has_more") );
			
			Iterator<JSONObject> iterator1 =(Iterator<JSONObject>) jsonlistfile1.iterator();		
			while(iterator1.hasNext()) {
				Array_diviso_per_tipo.add_element(iterator1.next());
			}
		}
		
		
		//devo fare i get metadata solo dei file, creo iteratore per scorrere il vector che li contiene
		Vector<File> vectorFile = (Vector<File>) Array_diviso_per_tipo.get_vector_file();
		Iterator<File> file =(Iterator<File>) vectorFile.iterator();
		System.out.println("deleted "+  Array_diviso_per_tipo.get_vector_deleted().size() +" - folder "+Array_diviso_per_tipo.get_vector_folder().size()  +" - file "+ Array_diviso_per_tipo.get_vector_file().size() +" - total " +(Array_diviso_per_tipo.get_vector_deleted().size()+Array_diviso_per_tipo.get_vector_folder().size()+Array_diviso_per_tipo.get_vector_file().size()));
		int i = 1;
		while (file.hasNext() ) {
			File temp=file.next();
			System.out.print(i+" - ");
			i=i+1;
			//effettuo chiama e formattazione get metadata
			String path=temp.getPath();
			String strgetmetadatar=api.apicall_get_metadata(path);
			JSONObject jsongetmetadata=JsonHandler.format_get_metadata(strgetmetadatar);
			
			//aggiorno temp(elemento in vectorfile->l'oggetto file) con le nuove informazioni
			temp.setRevision(jsongetmetadata.get("rev").toString());
			temp.setSize((int) Integer.parseInt( jsongetmetadata.get("size").toString()   ) );
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
			lastmod.set_month(Integer.parseInt(veclastmod[1]) );
			lastmod.set_day(Integer.parseInt(veclastmod[2]) );
			lastmod.set_hour(Integer.parseInt(veclastmod[3]) );
			lastmod.set_minute(Integer.parseInt(veclastmod[4]) );
			
			temp.setLastModify(lastmod);
		}//fine while
		
		
		//mi faccio scrivere tutti gli oggetti file che ha trovato
		Iterator<File> file2 =(Iterator<File>) vectorFile.iterator();
		
		while (file2.hasNext() ) {
			File temp=file2.next();
			
			System.out.println(temp.getName());
		}//while
		System.out.println("Ho finito");

		FileHandler.saveFile(Array_diviso_per_tipo);
	}
}
