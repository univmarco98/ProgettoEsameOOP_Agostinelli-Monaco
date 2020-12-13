/**
 * 
 */
package application.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Vector;


import java.io.BufferedWriter;
//import java.io.FileReader;
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
		
		//private JSONArray jsonArr;
		//private JSONObject jsonObj;
		
		public JsonHandler() {
			//this.jsonArr = new JSONArray();
			//this.jsonObj = new JSONObject();
		}
		
		/*public JSONArray getJsonArray() {
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
		}*/
		
		/**
		 * Inserisco un JSONObject nel mio JSONArray.
		 * @param jo JSONOnject
		 */
		/*public void insertObject(JSONObject jsonObj) {
			this.jsonArr.add(jsonObj);
		}*/
		
		/**
		 * Metodo per salvare un oggetto in un file di testo .json.
		 * Posso scegliere se salvare un JSONObject oppure un JSONArray.
		 * 
		 * @param nome_file Nome del file in cui salvare l'oggetto.
		 * @param isObject Specifica se l'oggetto da salvare � un JSONObject oppure un JSONArray.
		 */
		public static void salvaFile(String nome_file, JSONObject jsonObj) {
			try {
				PrintWriter file_output = new PrintWriter(new BufferedWriter(new FileWriter(nome_file)));
				file_output.println(jsonObj);
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
		
		
		public static Vector<JSONObject> format_list_folder(String data) {
			JSONArray jsonArr=null;
			Vector<JSONObject> jo=new Vector<JSONObject>();
			try {
				JSONObject jsonObj= (JSONObject) JSONValue.parseWithException(data);
				System.out.println("successo api e formattazione list folder");
				String cursor=jsonObj.get("cursor").toString();
				String has_more=jsonObj.get("has_more").toString();
				
				jsonArr=(JSONArray)jsonObj.get("entries");
				Iterator<JSONObject> iterator=(Iterator<JSONObject>)jsonArr.iterator();
				while(iterator.hasNext()) {
					jo.add(iterator.next());
				}
				//scrivo alcune caratteristiche del file
				/*String file_id=(String)jsonObj.get("id");
				System.out.println(file_id);*/
				
				//ho crato l'oggetto aggiungere per verificare se chiamare continue o no
				JSONObject aggiungere=(JSONObject) JSONValue.parseWithException("{\r\n"
						+ "    \"cursor\": \"" + cursor + "\",\r\n"
						+ "    \"has_more\": \""+has_more+"\"\r\n"
						+ "}");
				
				
				jo.add(aggiungere);
				System.out.println(jo.size());
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
			return jo;
		}
		
		
		
		/**
		 * Metodo per scaricare un oggetto utilizzando API.
		 * Posso scegliere se scaricare un JSONObject oppure un JSONArray.
		 * 
		 * @param url URL da cui utilizzare la chiamata API.
		 * @param isObject Specifica se l'oggetto da salvare � un JSONObject oppure un JSONArray.
		 */
		public static JSONObject format_get_metadata(String data) {
			JSONObject jsonObj=null;
			try {
				jsonObj = (JSONObject) JSONValue.parseWithException(data);
				System.out.println("successo api e formattazione get metadata");
				
				//scrivo alcune caratteristiche del file
				/*String file_id=(String)jsonObj.get("id");
				System.out.println(file_id);*/
				
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
			return jsonObj;
		}
		
		public static String getFileType(JSONObject jsonObj) {
			return(jsonObj.get(".tag").toString());
		}

}
