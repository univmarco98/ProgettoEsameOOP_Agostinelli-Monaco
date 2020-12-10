/**
 * 
 */
package application.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import application.json.ApiHandler;

/**
 * @author MARCO
 *
 */
public class JsonHandler {
		
		private JSONArray jsonArr;
		private JSONObject jsonObj;
		
		public JsonHandler() {
			this.jsonArr = new JSONArray();
			this.jsonObj = new JSONObject();
		}
		
		public JSONArray getJsonArray() {
			return this.jsonArr;
		}
		
		public void setArray(JSONArray jsonArr) {
			this.jsonArr = jsonArr;
		}

		public JSONObject getJsonObject() {
			return this.jsonObj;
		}

		public void setObject(JSONObject jsonObj) {
			this.jsonObj = jsonObj;
		}
		
		/**
		 * Inserisco un JSONObject nel mio JSONArray.
		 * @param jo JSONOnject
		 */
		public void insertObject(JSONObject jsonObj) {
			this.jsonArr.add(jsonObj);
		}
		
		/**
		 * Metodo per salvare un oggetto in un file di testo .json.
		 * Posso scegliere se salvare un JSONObject oppure un JSONArray.
		 * 
		 * @param nome_file Nome del file in cui salvare l'oggetto.
		 * @param isObject Specifica se l'oggetto da salvare � un JSONObject oppure un JSONArray.
		 */
		public void salvaFile(String nome_file) {
			try {
				PrintWriter file_output = new PrintWriter(new BufferedWriter(new FileWriter(nome_file)));
				file_output.println(this.jsonObj);
				file_output.close();
				System.out.println("File salvato!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Metodo per leggere un oggetto da un file di testo .json.
		 * Posso scegliere se caricare un JSONObject oppure un JSONArray.
		 * 
		 * @param nome_file Nome del file da cui leggere l'oggetto.
		 * @param isObject Specifica se l'oggetto da salvare � un JSONObject oppure un JSONArray.
		 */
		public void caricaFile(String nome_file, boolean isObject) {
			
		}
		
		
		public void formatta_list_folder(String data) {

			try {
				jsonArr = (JSONArray) JSONValue.parseWithException(data);
				System.out.println("OK");
				
				//scrivo alcune caratteristiche del file
				/*String file_id=(String)jsonObj.get("id");
				System.out.println(file_id);
				file_id=(String)jsonObj.get("server_modified");
				System.out.println(file_id);
				file_id=(String)jsonObj.get("client_modified");
				System.out.println(file_id);*/
				
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		/**
		 * Metodo per scaricare un oggetto utilizzando API.
		 * Posso scegliere se scaricare un JSONObject oppure un JSONArray.
		 * 
		 * @param url URL da cui utilizzare la chiamata API.
		 * @param isObject Specifica se l'oggetto da salvare � un JSONObject oppure un JSONArray.
		 */
		public void formatta_get_metadata(String data) {

			try {
				jsonObj = (JSONObject) JSONValue.parseWithException(data);
				System.out.println("OK");
				
				//scrivo alcune caratteristiche del file
				/*String file_id=(String)jsonObj.get("id");
				System.out.println(file_id);
				file_id=(String)jsonObj.get("server_modified");
				System.out.println(file_id);
				file_id=(String)jsonObj.get("client_modified");
				System.out.println(file_id);*/
				
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
	

}
