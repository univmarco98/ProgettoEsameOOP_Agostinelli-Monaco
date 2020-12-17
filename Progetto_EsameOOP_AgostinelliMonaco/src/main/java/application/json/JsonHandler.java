/**
 * 
 */
package application.json;

import java.util.Iterator;
import java.util.Vector;
//import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import application.file.Deleted;
import stats_and_filters.Statistics;

/**
 * @author MARCO
 *
 */
public class JsonHandler {
		
		
		
		
		
		
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
		public static JSONObject toJSONObject(Deleted d) {
			return d.toJSONObject();
		}
		
		public static String getFileType(JSONObject jsonObj) {
			return(jsonObj.get(".tag").toString());
		}
		
		public static JSONObject ritornaJ() {
			Vector<Vector> vv=Statistics.difference("20201216","20201217");
			Iterator<Vector> vvI=vv.iterator();
			while(vvI.hasNext()) {
				Vector<Object> temp=vvI.next();
				Iterator<Object> oI=temp.iterator();
				while(oI.hasNext()) {
					Object tempO=oI.next();
					tempO=JsonHandler.toJSONObject((Deleted)tempO);
					System.out.println(tempO);
				}
			}
			JSONObject jo=new JSONObject();
			jo.put("Deleted", vv.get(0));
			jo.put("Folder", vv.get(1));
			jo.put("Modified File", vv.get(2));
			jo.put("New File", vv.get(3));
			System.out.println(jo);
			
			return jo;
		}
}
