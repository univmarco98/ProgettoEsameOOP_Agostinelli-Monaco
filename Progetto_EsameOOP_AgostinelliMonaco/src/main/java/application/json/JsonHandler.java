/**
 * 
 */
package application.json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

/**
 * @author MARCO
 *
 */
public class JsonHandler {
		
		private JSONArray jsonArr = null;
		private JSONObject jsonObj = null;
		
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
		
		/**
		 * Metodo per scaricare un oggetto utilizzando API.
		 * Posso scegliere se scaricare un JSONObject oppure un JSONArray.
		 * 
		 * @param url URL da cui utilizzare la chiamata API.
		 * @param isObject Specifica se l'oggetto da salvare � un JSONObject oppure un JSONArray.
		 */
		public void chiamataAPI() {
			//SpringApplication.run(TestDropBxApiApplication.class, args); //per lanciare il server
			String url="https://api.dropboxapi.com/2/files/get_metadata";
			try {

				HttpURLConnection openConnection = (HttpURLConnection) new URL(url).openConnection();
				openConnection.setRequestMethod("POST"); //one of: GET POST HEAD OPTIONS PUT DELETE TRACE are legal, subject to protocol restrictions
				openConnection.setRequestProperty("Authorization",
						"Bearer apLbyiJWIa0AAAAAAAAAAaoW0voRsvsHVHefHG8-fiBH7UGA9tcr9JXvM4SWGncg");
				openConnection.setRequestProperty("Content-Type", "application/json");
				openConnection.setRequestProperty("Accept", "application/json");
				openConnection.setDoOutput(true);
				
				String jsonBody = "{\r\n"
						+ "    \"path\": \"/matteo/scuola/elettronica/8-12 consegnata/prova  10/screenshot 2017-02-18 12.54.30.png\",\r\n"
						+ "    \"include_media_info\": true,\r\n"
						+ "    \"include_deleted\": false,\r\n"
						+ "    \"include_has_explicit_shared_members\": true\r\n"
						+ "}";
				
				/*String jsonBody ="{\r\n"
						+ "    \"path\": \"\",\r\n"
						+ "    \"recursive\": false,\r\n"
						+ "    \"include_media_info\": false,\r\n"
						+ "    \"include_deleted\": false,\r\n"
						+ "    \"include_has_explicit_shared_members\": false,\r\n"
						+ "    \"include_mounted_folders\": true,\r\n"
						+ "    \"include_non_downloadable_files\": true\r\n"
						+ "}";*/
				
				try (OutputStream os = openConnection.getOutputStream()) {
					byte[] input = jsonBody.getBytes("utf-8");
					os.write(input, 0, input.length);
				}
				

				InputStream in = openConnection.getInputStream();

				String data = "";
				String line = "";
				try {
					InputStreamReader inR = new InputStreamReader(in);
					BufferedReader buf = new BufferedReader(inR);

					while ((line = buf.readLine()) != null) {
						data += line;
						System.out.println(line);
					}
				}
				finally {
					in.close();
				}
				jsonObj = (JSONObject) JSONValue.parseWithException(data);
				System.out.println("OK");
				
				String file_id=(String)jsonObj.get("id");
				System.out.println(file_id);
				file_id=(String)jsonObj.get("server_modified");
				System.out.println(file_id);
				file_id=(String)jsonObj.get("client_modified");
				System.out.println(file_id);
				
			}
			catch (IOException | ParseException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

}
