package application.utility.json;

import java.util.Iterator;
import java.util.Vector;
//import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import application.exception.MyFileNotFoundException;
import application.exception.MyMissingFileException;
import application.exception.NotMatchedExtentionException;
import application.file.*;
import application.utility.ArrayType;
import application.utility.FileHandler;
import stats_and_filters.Statistics;

/**
 * 
 * Classe che espone metodi utili a trattare e manipolare, i tipi di dati di cui il programma
 * si serve, per rappresentare i metadata di dropbox. Questa lo fa secondo un approccio di manipolazione volto
 * alla rappresentazione dei metadata tramite JSONObject o eventualmente Vector di JSONObject
 * 
 * @author Marco 
 * @author Matteo
 *
 */

public class JsonHandler {
		
	/**
	 * Metodo che formatta il risultato della chiamata list_folder o list_folder/continue di Dropbox
	 * in un oportuno Vector di JSONObject
	 * 
	 * @param data String che contiene il risultato della rotta di Dropbox list_folder o list_folder/continue 
	 * @return Vector di JSONObject
	 */
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
		 * Metodo che formatta il risultato della chiamata get_metadata di Dropbox
		 * in un oportuno JSONObject
		 * 
		 * @param data String che contiene il risultato della rotta di Dropbox get_metadata 
		 * @return	JSONObject
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
		
		/**
		 * Metodo per poter convertite un oggetto Deleted in un JSONObject
		 * 
		 * @param d oggetto Deleted
		 * @return JSONObject
		 */
		public static JSONObject toJSONObject(Deleted d) {
			return d.toJSONObject();
		}
		
		/**
		 * Metodo per poter ottenere in tag di un JSONObject
		 * 
		 * @param  jO JSONObject in input
		 * @return tag in formato String
		 */
		public static String getFileType(JSONObject jO) {
			return(jO.get(".tag").toString());
		}
		
		/**
		 * Metodo che prendee in input 2 date (yyyymmdd) in formato String, elabora i metadati
		 * contenuti nei file, relativi a queste 2 date. Infine restituisce un JSONObject che contiene tali differenze
		 * 
		 * @param t1 prima data in formato String
		 * @param t2 seconda data in formato String
		 * @return	JSONObject che contiene le differenze temporali
		 * @throws MyMissingFileException viene lanciata se non viene trovato uno dei 2 file per 2 volte
		 */
		public static JSONObject getJsonAllStats(String t1, String t2) throws MyMissingFileException {
			boolean error=false;
			Vector<Vector> vv=new Vector<Vector>();
			try {
				if ( Integer.parseInt(t1) < Integer.parseInt(t2) )
					vv=Statistics.difference(t1,t2);
				else
					vv=Statistics.difference(t2,t1);
				if (vv.size()>4) //se è stato accodato il Vector vuoto allora setto error a true
					error=true;
			}
			catch (MyMissingFileException a) {
				throw new MyMissingFileException (a.getMessage());
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
			
			jo.put("error", error); //informazioni sulla corretta riuscita della ricerca dei file
			//System.out.println( "jo---> " + jo );
			return jo;
		}
		
		/**
		 * Metodo che prendee in input 2 date (yyyymmdd) in formato String, dimensione minima e dimensione massima e 2 estensioni.
		 *  Elabora i metadati contenuti nei file, relativi a queste 2 date e filtra le informazioni.
		 *  Infine restituisce un JSONObject che contiene tali differenze
		 * 
		 * @param t1 prima data in formato String
		 * @param t2 seconda data in formato String
		 * 
		 * @param sizeMin dimensione minima in formato  int
		 * @param sizeMax dimensioni massima in formato int
		 * 
		 * @param file1Extention estensione del primo file in fromato String
		 * @param file2Extention estensione del secondo file in fromato String
		 * @return JSONObject che contiene le differenze del filtraggio multiplo(per data e/o dimensione file e/o estensione)
		 * 
		 * @throws MyMissingFileException viene lanciata se non viene trovato uno dei 2 file per 2 volte
		 * @throws NotMatchedExtentionException viene lanciata nel caso in cui entrambe le estensioni inserite non siano contemplate dal programma
		 */
		public static JSONObject getJsonAllStats(String t1, String t2, int sizeMin, int sizeMax, String file1Extention, String file2Extention) 
																	throws MyMissingFileException, NotMatchedExtentionException {
			String[] handledExtention={".7z",".bz2",".gz",".iso",".rar",".xz",".z",".zip",".djvu",".doc",".docx",".epub"
					,".odt",".pdf",".rtf",".tex",".txt",".bmp",".gif",".ico",".jpg",".jpeg",".png",".psd",".tif",".tiff",
					".aac",".flac",".m4a",".mp3",".ogg",".wma",".wav",".csv",".odp",".ods",".pps",".ppt",".pptx",".xls",
					".xlsx",".avi",".flv",".m4v",".mkv",".mov",".mp4",".mpeg",".mpg",".wmv"};
			
			boolean notExtFilt;   //not extention filtering
			
			
			if (file1Extention.equals("") && file2Extention.equals(""))
				notExtFilt=true;
			else {
				boolean matched1=false, matched2=false;
				for (String ex:handledExtention) {
					if (ex.equals(file1Extention))
						matched1=true;
					if (ex.equals(file2Extention))
						matched2=true;
				}
				notExtFilt=false;
				if(matched1==false && matched2==false)
					throw new NotMatchedExtentionException();
			}
			
			
			
			boolean error=false;
			Vector<Vector> vv=new Vector<Vector>();
			try {
				if ( Integer.parseInt(t1) < Integer.parseInt(t2) )
					vv=Statistics.difference(t1,t2);
				else
					vv=Statistics.difference(t2,t1);
				if (vv.size()>4) //se è stato accodato il Vector vuoto allora setto error a true
					error=true;
			}
			catch (MyMissingFileException a) {
				throw new MyMissingFileException (a.getMessage());
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
					if (i==0) 
					{
						Deleted tempOD=(Deleted)tempO;
						String extetion="."+tempOD.getExtetion();
						if ( notExtFilt || extetion.equals(file1Extention) || 
											extetion.equals(file2Extention) )
							vvjo.get(i).add(JsonHandler.toJSONObject((Deleted)tempO) );
					}
						
					else if(i>1) {
						File tempOF=(File)tempO;
						if((sizeMin<tempOF.getSize())&&(tempOF.getSize()<sizeMax)) {
							
							String extetion="."+tempOF.getExtetion();
							if ( notExtFilt || extetion.equals(file1Extention) || 
												extetion.equals(file2Extention) )
								vvjo.get(i).add(JsonHandler.toJSONObject((Deleted)tempO) );
						}
					}
					else
						vvjo.get(i).add(JsonHandler.toJSONObject((Deleted)tempO) );
				}
				i++;
			
			}
			JSONObject jo=new JSONObject();
			
			jo.put("Deleted", (Vector<JSONObject>) vvjo.get(0));
			jo.put("Folder", (Vector<JSONObject>) vvjo.get(1));
			jo.put("Modified File", (Vector<JSONObject>) vvjo.get(2));
			jo.put("New File", (Vector<JSONObject>) vvjo.get(3));
			
			jo.put("error", error); //informazioni sulla corretta riuscita della ricerca dei file
			//System.out.println( "jo---> " + jo );
			return jo;
		}
		
		/**
		 * Metodo che prendee in input 2 date (yyyymmdd) in formato String e il body della richiesta POST in formato JSONObject 
		 * Elabora i metadati contenuti nei file, relativi a queste 2 date e filtra le informazioni in base alle indicazioni del body.
		 * Infine restituisce un JSONObject che contiene tali differenze
		 * 
		 * @param date1 prima data in formato String
		 * @param date2 seconda data in formato String
		 * @param jobody 
		 * 				{
		 *					"type1":"" 
		 *				    "type2":""
		 *				    "file1Extention":""
		 *				    "file2Extention":""
		 *				    "sizeMin":""
		 *				    "sizeMax":""
		 *				}
		 * @return JSONObject che contiene le differenze del filtraggio multiplo(per data e/o dimensione file e/o estensione)
		 *	filtrando anche per tipo di elemento(file, folder o deletd)
		 */
		public static JSONObject getJsonPartialStats(String date1, String date2, JSONObject jobody) {
			String type1=new String();
			String type2=new String();
			String file1Extention=new String("11");
			String file2Extention=new String("22");
			int sizeMin=0;
			int sizeMax=0;
			
			try{
				type1= jobody.get("type1").toString();
				type2= jobody.get("type2").toString();
			}
			catch(NullPointerException e) {
				JSONObject error = new JSONObject();
				error.put("error", "true");
				error.put("infoError", "type1/type2 assente/i");
				error.put("stackTrace", e.getMessage() );
				return	error;
			}
			
			try {
				file1Extention=jobody.get("file1Extention").toString();
				file2Extention=jobody.get("file2Extention").toString();
			}
			catch(NullPointerException e) {
				System.out.print("Eccezzione");
				JSONObject error = new JSONObject();
				error.put("error", "true");
				error.put("infoError", "file1Extention/file2Extention assente/i");
				error.put("stackTrace", e.getMessage() );
				return	error;
			}
			
			try {	
				if(jobody.get("sizeMin").toString().equals(""))
					sizeMin=0;
				else
					sizeMin=Integer.parseInt(jobody.get("sizeMin").toString());
				
				
				if(jobody.get("sizeMax").toString().equals("")) {
					sizeMax=Integer.MAX_VALUE;
				}
				else if(Integer.MAX_VALUE<Long.parseLong(jobody.get("sizeMax").toString())) {
						sizeMax=Integer.MAX_VALUE;
				}
				else {
					sizeMax=Integer.parseInt(jobody.get("sizeMax").toString());
				}
			}
			catch(NullPointerException e) {
				JSONObject error = new JSONObject();
				error.put("error", "true");
				error.put("infoError", "sizeMin/sizeMax assente/i");
				error.put("stackTrace", e.getMessage() );
				return	error;
			}
			catch(NumberFormatException e) {
				JSONObject error = new JSONObject();
				error.put("error", "true");
				error.put("infoError", "formato sizeMin/sizeMax errato");
				error.put("stackTrace", e.getMessage() );
				return	error;
			}
			
			JSONObject all=new JSONObject();
			try {
				if((sizeMin==0) && (sizeMax==Integer.MAX_VALUE) && (file1Extention.equals("")) && (file2Extention.equals("")) )
					all = getJsonAllStats(date1, date2 );
				else
					all = getJsonAllStats(date1, date2, sizeMin, sizeMax, file1Extention, file2Extention);
			}
			catch (MyMissingFileException e) {
				JSONObject error = new JSONObject();
				error.put("error", "true");
				error.put("infoError", "File non trovati");
				error.put("stackTrace", e.getMessage() );
				return	error;
			}
			catch (NotMatchedExtentionException e) {
				JSONObject error = new JSONObject();
				error.put("error", "true");
				error.put("infoError", "Formato estensione file non valido");
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
				
				if( (type1.equalsIgnoreCase("deleted")) || (type2.equalsIgnoreCase("deleted"))  ) {
					parse.put("Deleted", all.get("Deleted"));
				}
				
				if (parse.size()==0) {
					all.put("error", "true");  //sovrascrivo il precendete error in all
					parse.put("infoError", "Il contenuto di type1/type2 non e' valido");
				
				}
					
			}
			parse.put("error", all.get("error"));
			return	parse;
		}
		
	/**
	 * Metodo che estrapola i metadata del contenuto di uno specifico elemento, in una specifica gionata	
	 * @param nomeElemento nome dell'elemento completo di estensione (nome.estensione)
	 * @param date data nella quale cercare l'elemento
	 * @return JSONObject contenente il risultato della ricerca(possono esserci più risultati inerenti allo
	 * 		stesso nome)
	 */
	public static JSONObject getJsonInfoByName (String nomeElemento, String date) {
		ArrayType aT;
		boolean error=false;
		try {
			aT=FileHandler.caricaFile(date+".txt");
		}
		catch (MyFileNotFoundException e) {
			JSONObject errorJO = new JSONObject();
			errorJO.put("error", "true");
			errorJO.put("infoError", "File "+date+"non trovato");
			errorJO.put("stackTrace", e.getMessage() );
			return	errorJO;
		}
		Vector<Deleted> vDInfo = aT.fetch(nomeElemento);
		Vector<JSONObject> vJInfo = new Vector<JSONObject>();
		
		Iterator<Deleted> vDII=vDInfo.iterator();
		while (vDII.hasNext()){
			Deleted temp=vDII.next();
			vJInfo.add(JsonHandler.toJSONObject((Deleted)temp) ); 
			//System.out.println("\n"+JsonHandler.toJSONObject((Deleted)temp) );
		}
		
		JSONObject returnJ=new JSONObject();
		returnJ.put("info",vJInfo);
		returnJ.put("error", error);
		return returnJ;
	}
		
	
}
