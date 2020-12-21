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

import application.exception.MyFileNotFoundException;
import application.exception.MyMissingFile;
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
			//System.out.println("successo api e formattazione list folder");
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
		

		
		public static JSONObject getJsonAllStats(String t1, String t2) throws MyMissingFile {
			boolean error=false;
			Vector<Vector> vv=new Vector<Vector>();
			try {
				if ( Integer.parseInt(t1) < Integer.parseInt(t2) )
					vv=Statistics.difference(t1,t2);
				else
					vv=Statistics.difference(t2,t1);
				if (vv.size()>4)
					error=true;
			}
			catch (MyMissingFile a) {
				throw new MyMissingFile (a.getMessage());
			}
			
			Vector<Vector> vvjo=new Vector<Vector>();
			int i=0;
			Iterator<Vector> vvI=vv.iterator();
			while(vvI.hasNext()) {
				
				Vector<Object> temp=vvI.next();
				vvjo.add(new Vector<JSONObject>());
							
				Iterator<Object> oI=temp.iterator();
				while(oI.hasNext()) {
					Object tempO=oI.next();
					vvjo.get(i).add(JsonHandler.toJSONObject((Deleted)tempO) );
				}
				i++;
			
			}
			JSONObject jo=new JSONObject();
			
			jo.put("Deleted", (Vector<JSONObject>) vvjo.get(0));
			jo.put("Folder", (Vector<JSONObject>) vvjo.get(1));
			jo.put("Modified File", (Vector<JSONObject>) vvjo.get(2));
			jo.put("New File", (Vector<JSONObject>) vvjo.get(3));
			
			jo.put("error", error);
			//System.out.println( "jo---> " + jo );
			return jo;
		}
		
		public static JSONObject getJsonPartialStats(String date1, String date2, JSONObject jobody) {
			String type1=(String) jobody.get("type1");
			String type2=(String) jobody.get("type2"); 
			JSONObject all=new JSONObject();
			try {
				all = getJsonAllStats(date1, date2 );
			}
			catch (MyMissingFile e) {
				JSONObject error = new JSONObject();
				error.put("error", "true");
				error.put("infoError", "File non trovati");
				error.put("stackTrace", e.getMessage() );
				return	error;
			}
			
			
			JSONObject parse = new JSONObject();
			
			if( (type1.equalsIgnoreCase("")) && (type2.equalsIgnoreCase(""))  ) {
				parse=all;
			}
			else {
				if( (type1.equalsIgnoreCase("file")) || (type2.equalsIgnoreCase("file"))  ) {
					parse.put("New File", all.get("New File"));
					parse.put("Modified File", all.get("Modified File"));
				}
				
				if( (type1.equalsIgnoreCase("new file")) || (type2.equalsIgnoreCase("new file"))  ) {
					parse.put("New File", all.get("New File"));
				}
				
				if( (type1.equalsIgnoreCase("modified file")) || (type2.equalsIgnoreCase("modified file"))  ) {
					parse.put("Modified File", all.get("Modified File"));
				}
				
				if( (type1.equalsIgnoreCase("folder")) || (type2.equalsIgnoreCase("folder"))  ) {
					parse.put("Folder", all.get("Folder"));
				}
			}
			parse.put("error", all.get("error"));
			return	parse;
		}
}
