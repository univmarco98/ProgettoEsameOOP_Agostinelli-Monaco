/**
 * 
 */
package application.json;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import application.utility.ArrayType;

/**
 * @author MARCO
 *
 */
public class JsonHandler {
		
		/**
		 * Metodo per salvare un oggetto in un file di testo .json.
		 * Posso scegliere se salvare un JSONObject oppure un JSONArray.
		 * 
		 * @param nome_file Nome del file in cui salvare l'oggetto.
		 * @param isObject Specifica se l'oggetto da salvare � un JSONObject oppure un JSONArray.
		 */
		public static void saveFile(ArrayType aT) {
			SimpleDateFormat data = new SimpleDateFormat();
			data.applyPattern("yyyyMMdd");
			String dataStr = data.format(new Date()); // data corrente (20 febbraio 2014)
			String nome_file = (dataStr+".txt");
			try {
				ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("D:\\Users\\Matteo\\Documenti\\Eclipse\\ProgettoEsameOOP_Agostinelli-Monaco\\Progetto_EsameOOP_AgostinelliMonaco\\DataBase\\"+nome_file)));
				file_output.writeObject(aT);;
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
		public static ArrayType caricaFile(String nome_file) {
			ArrayType aT=null;
			try {
				ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream("D:\\Users\\Matteo\\Documenti\\Eclipse\\ProgettoEsameOOP_Agostinelli-Monaco\\Progetto_EsameOOP_AgostinelliMonaco\\DataBase\\"+nome_file)));
				aT=(ArrayType)file_input.readObject();
				file_input.close();
				System.out.println("File salvato!");
			}
			catch(ClassNotFoundException e){
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			return aT;
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
