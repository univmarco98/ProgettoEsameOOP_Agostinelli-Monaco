package application.file;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

/**
 * Classe per modellarele le cartelle
 * 
 * @author Marco 
 * @author Matteo
 *
 */

public class Folder extends Deleted implements Serializable{

	private static final long serialVersionUID = 1;
	private String id;

	/**
	 * Costruttore di Folder
	 * @param tag identificativo testuale tipo di elemento (folder)
	 * @param name nome folder
	 * @param path path folder
	 * @param id codice identificativo univoco della dell'elemento folder
	 */
	public Folder(String tag, String name, String path, String id) {
		super(tag, name, path);
		this.id=id;
	}
	
	/**
	 * Metodo per ottenerere l'id di un folder
	 * @return id in formato String
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Metodo per settare l'id di un folder
 	 *
 	 * @param id in formato String
	 */
	public void setId(String id) {
		this.id=id;
	}
	
	/**
	 * Metodo per ottenerere l'estensione di un folder
	 * Folder non ha estensione, quindi ritorno il nome
	 * 
	 * @return nome in formato String
	 * 
	 */
	@Override
	public String getExtetion() {
		return this.getName();
	}
	
	/**
	 * Metodo per ottenerere un JSONObject rappresentativo dell'oggetto folder
	 * 
	 * @return nome in formato String
	 * 
	 */
	public JSONObject toJSONObject() {
		JSONObject result=super.toJSONObject();
		result.put("id", this.id);
		return result;
	}

	@Override
	public String toString() {
		return "Folder [id=" + id + super.toString() +"]";
	}

}
