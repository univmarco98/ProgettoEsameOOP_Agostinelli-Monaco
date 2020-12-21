/**
 * 
 */
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
import java.util.Vector;

import javax.swing.JOptionPane;

import stats_and_filters.Statistics;
import application.file.Deleted;
import application.file.File;
import application.json.JsonHandler;
import application.utility.ArrayType;
import application.utility.Time;

/**
 * @author MARCO
 *
 */
@RestController
public class SimpleRESTController {
	
	@GetMapping("/updateDatabase")
	public String updateDatabase() {
		Routine_GetAndSave_Datas.routine_run();
		return("{\"result\":\"ok\"}");
	}
	
	/*@GetMapping("/generalStats")
	public JSONObject generalStats( @RequestParam( name="date1")String date1 ,@RequestParam( name="date2")String date2 ) {
		try {
			return JsonHandler.getJsonAllStats(date1, date2 );
		}
		catch (NumberFormatException e) {
			JSONObject error = new JSONObject();
			error.put("error", "true");
			error.put("infoError", "Formattazione errata date1/date2");
			error.put("stackTrace", e.getMessage() );
			return	error;
		}
	}*/
	
	@PostMapping("/generalStats")
	public JSONObject generalStats2( @RequestParam( name="date1")String date1 ,@RequestParam( name="date2")String date2,@RequestBody String body ) {
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
		catch(NullPointerException e) {
			JSONObject error = new JSONObject();
			error.put("error", "true");
			error.put("infoError", "type1/type2 assenti");
			error.put("stackTrace", e.getMessage() );
			return	error;
			
		}
		/*catch(FileNotFoundException e) {
			JSONObject error = new JSONObject();
			error.put("error", "true");
			error.put("infoError", "File non trovato");
			error.put("stackTrace", e.getMessage() );
			return	error;
			
		}*/
	}
	

	@GetMapping("/test")
	public JSONObject test() {
		File file=new File("tag", "name", "path", "id");
		
		Time lastModify=new Time(2020,12,18,10,10);
		file.set_lastModify(lastModify);
		String revision ="xtcfgvjbhk";
		file.set_revision(revision);
		int size=102030;
		file.set_size(size);
		
		try {
			ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("prova1.txt")));
			file_output.writeObject(file);;
			file_output.close();
			System.out.println("File salvato!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file2=null;
		try {
			ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream("prova1.txt")));
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
