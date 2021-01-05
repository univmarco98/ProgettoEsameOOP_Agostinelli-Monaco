package application.file;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

import application.utility.Time;

/**
 * 
 * Classe per modellarele i file
 *
 * @author Marco 
 * @author Matteo
 *
 */

public class File extends Folder implements Serializable {

	private static final long serialVersionUID = 1;
	Time lastModify;
	String revision;
	int size;
	
	/**
	 * Costruttore classe File
	 * 
	 * @param tag identificativo testuale tipo di elemento (file)
	 * @param name nome file
	 * @param path path file
	 * @param id codice identificativo univoco della dell'elemento file
	 */
	public File (String tag, String name, String path, String id) {
		super (tag, name, path, id);
	//ho eliminato parte del costruttore perche non conosco gli ultimi 3 valori
	//dopo aver fatto la list folder
	}
	
	/**
	 * Metodo per settare la dimensione di un file
 	 *
 	 * @param size dimensione in formato int
	 */	
	public void setSize(int size) {
		this.size=size;
	}
	
	/**
	 * Metodo per settare la revisione di un file
 	 *
 	 * @param revision in formato String
	 */
	public void setRevision(String revision) {
		this.revision=revision;
	}

	/**
	 * Metodo per settare l'ultima modifica di un file
 	 *
 	 * @param lastModify ultima modifica in formato Time
	 */
	public void setLastModify(Time lastModify) {
		this.lastModify=lastModify;
	}
	
	/**
	 * Metodo per ottenerere la dimensione di un file
	 * 
	 * @return size in formato int
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Metodo per ottenerere la revision di un file
	 * 
	 * @return revision in formato String
	 */
	public String getRevision() {
		return this.revision;
	}
	
	/**
	 * Metodo per ottenerere l'ultima modifica di un file
	 * 
	 * @return	ultima modifica in formato Time
	 */
	public Time getLastModify() {
		return this.lastModify;
	}
	
	/**
	 * Metodo per ottenerere l'estensione di un file
	 * 
	 * @return estensione di un file
	 */
	@Override
	public String getExtetion() {
		String[] splittedArray=this.getName().split(Pattern.quote("."));
		return splittedArray[splittedArray.length-1];
	}
	
	/**
	 * Metodo per ottenerere un JSONObject rappresentativo dell'oggetto file
	 * 
	 * @return nome in formato String
	 * 
	 */
	public JSONObject toJSONObject() {
		JSONObject result=super.toJSONObject();
		result.put("last modify", this.lastModify.toString());
		result.put("size", this.size);
		result.put("revision", this.revision);
		return result;
	}

	@Override
	public String toString() {
		return "File [lastModify=" + lastModify + ", revision=" + revision + ", size=" + size
				 + super.toString()+"]";
	}

	
}
