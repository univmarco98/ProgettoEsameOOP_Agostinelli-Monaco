package application.file;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

/**
 * Classe per modellare il tipo di file cancellato (puo' rappresentare un file o folder cancellati)
 * 
 * @author Marco 
 * @author Matteo
 *
 */

public class Deleted implements Serializable {
	private static final long serialVersionUID = 1;
	private String tag,name,path;
	
	/**
	 * Costruttore di Deleted
	 * 
	 *
	 */
	public Deleted() {
		this.path=null;
		this.name=null;
		this.tag=null;
	}
	
	/**
	 * Costruttore di Deleted
	 * @param tag identificativo testuale tipo di elemento (deleted, folder o file)
	 * @param name nome elemento
	 * @param path path elemento
	 */
	public Deleted(String tag, String name, String path) {
		this.path=path;
		this.name=name;
		this.tag=tag;
	}

	/**
	 * Metodo per ottenerere il path di un elemento
	 * @return path in formato String 
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * Metodo per ottenerere il nome di un elemento
	 * @return nome in formato String 
	 */	
	public String getName() {
		return this.name;
	}

	/**
	 * Metodo per ottenerere il tag di un elemento
	 * @return tag in formato String 
	 */
	public String getTag() {
		return this.tag;
	}

	/**
	 * Metodo per settare il path di un elemento
	 * @param path in formato String
	 */
	public void setPath(String path) {
		this.path=path;
	}

	/**
	 * Metodo per settare il nome di un elemento
	 * @param name nome in formato String
	 */	
	public void setName(String name) {
		this.name=name;
	}

	/**
	 * Metodo per settare il tag di un elemento
	 * @param tag in formato String
	 */
	public void setTag(String tag) {
		this.tag=tag;
	}
	
	/**
	 * Metodo per ottenerere l'estensione di un elemento deleted
	 * 
	 * @return l'estensione in formato String
	 * se il deleted e' una cartella, restituisco il nome
	 * 
	 */
	public String getExtetion() {
		String[] splittedArray=this.getName().split(Pattern.quote("."));
		return splittedArray[splittedArray.length-1];
	}
	
	/**
	 * Metodo per ottenerere un JSONObject rappresentativo dell'oggetto folder
	 * 
	 * @return nome in formato String
	 * 
	 */
	public JSONObject toJSONObject() {
		JSONObject result=new JSONObject();
		result.put("type", this.tag);
		result.put("name", this.name);
		result.put("path", this.path);
		return result;
	}

	@Override
	public String toString() {
		return "Deleted [tag=" + tag + ", name=" + name + ", path=" + path + "]";
	}
}
