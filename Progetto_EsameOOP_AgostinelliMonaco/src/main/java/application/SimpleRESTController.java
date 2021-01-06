package application;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Pattern;

import application.file.File;
import application.utility.Time;
import application.utility.json.JsonHandler;

/**
 * Classe che svolge le veci di un controller che espone delle API-REST attraverso delle rotte 
 * 	ben definite
 * 
 * @author Marco
 * @author Matteo
 */

@RestController
public class SimpleRESTController {
	/**
	 * Rotta per aggiornare il database, salvando i dati relativi alla data in cui viene invocata tale rotta
	 * @return JSON contenente l'esito positivo della procedura
	 */
	@GetMapping("/updateDatabase")
	public String updateDatabase() {
		try {
			System.out.println("Inizio procedura di update database...");
			Routine_GetAndSave_Datas.routine_run();
			return("{\"result\":\"ok\"}");
		}
		catch(IOException e){
			return("{\"result\":\"errore di tipo input/output,il token potrebbe essere errato o mancante\"}");
		}
		catch(Exception e){
			return("{\"result\":\"error during updating database\"}");
		}
	}
	
	/**
	 * Rotta per ottenerere le infomazioni sulle differenze(creazione, modifica e cancellazione) 
	 * tra cartelle e file del Dropbox tra due date specificate
	 * @param date1 data inizio confronto
	 * @param date2 data fine confronto
	 * @param body body della richiesta in formato testo Json: 
	 * 				{
     *					"type1":"" 
	 *				    "type2":""
	 *				    "file1Extention":""
	 *				    "file2Extention":""
	 *				    "sizeMin":""
	 *				    "sizeMax":""
	 *				}
	 * @return JSON contenente le info richieste e l'esito positivo della procedura
	 */
	@PostMapping("/generalStats")
	public JSONObject generalStats( @RequestParam( name="date1")String date1 ,@RequestParam( name="date2")String date2,@RequestBody String body ) {
		try {
			return JsonHandler.getJsonPartialStats(date1, date2, (JSONObject) JSONValue.parseWithException(body));
		}
		catch (NumberFormatException e) {
			JSONObject error = new JSONObject();
			error.put("error", "true");
			error.put("infoError", "Formattazione errata date1/date2");
			error.put("stackTrace", e.getMessage() );
			return	error;
		}
		catch (ParseException e) {
			JSONObject error = new JSONObject();
			error.put("error", "true");
			error.put("infoError", "Formattazione errata del body");
			error.put("stackTrace", e.getMessage() );
			return	error;
		}		
		catch(Exception e) {
			JSONObject error = new JSONObject();
			error.put("error", "true");
			error.put("infoError", "C'e' stato un problema");
			error.put("stackTrace", e.getMessage() );
			return	error;
		}
	}

	/**
	 * Rotta per ottenerere le infomazioni di un elemento(file o folder o deleted) all'interno di dropbox in uno specifico giorno 
	 * 
	 * @param oggetto nome dell'elemento da cercare, possono essere piu' di uno separati da una virgola (",") 
	 * @param date data(yyyymmdd) dove cercare l'elemento 
     *
	 * @return JSON contenente le info richieste e l'esito positivo della procedura
	 */
	
	@GetMapping("/searchByName")   //ricerca per nome in un determinato giorno
	public JSONObject searchByName(@RequestParam( name="object")String oggetto,@RequestParam( name="date")String date) {
		String[] splittedObject=oggetto.split(Pattern.quote(","));
		if(splittedObject.length==1)
			return JsonHandler.getJsonInfoByName(oggetto, date);
		else {
			JSONObject result=new JSONObject();
			for(String str : splittedObject) {
				result.put(str, JsonHandler.getJsonInfoByName(str, date));
			}
			return result;
		}
	}
	
	/**
	 * Rotta per testare il funzionamento generale dell'applicazione. 
	 * Crea un oggetto file, lo salva nella working directori del programma, lo carica leggendolo e poi lo restituisce come json al chiamante della rotta
	 * 
	 * @return JSON contenente le informazioni dell'oggetto file creato per effettuare il test
	 */
	
	@GetMapping("/test")
	public JSONObject test() {
		File file=new File("tag", "name", "path", "id");
		
		Time lastModify=new Time(2020,12,18,10,10);
		file.setLastModify(lastModify);
		String revision ="xtcfgvjbhk";
		file.setRevision(revision);
		int size=102030;
		file.setSize(size);
		
		try {
			ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("pippo.txt")));
			file_output.writeObject(file);;
			file_output.close();
			System.out.println("File salvato!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file2=null;
		try {
			ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream("pippo.txt")));
			file2=(File)file_input.readObject();
			file_input.close();
			System.out.println("File caricato!");
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return file2.toJSONObject();
	}
	
}
