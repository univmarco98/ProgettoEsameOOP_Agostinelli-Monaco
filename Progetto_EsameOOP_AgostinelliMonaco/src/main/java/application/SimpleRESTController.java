/**
 * 
 */
package application;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("/generalStats")
	public JSONObject generalStats() {
		return JsonHandler.ritornaJ();
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
