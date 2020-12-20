/**
 * 
 */
package application.file;

import java.io.Serializable;

import org.json.simple.JSONObject;

import application.utility.Time;

/**
 * @author Matteo
 *
 */
public class File extends Folder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	Time lastModify;
	String revision;
	int size;
	/**
	 * 
	 */
	public File (String tag, String name, String path, String id) {
		super (tag, name, path, id);
	//ho eliminato parte del costruttore perche non conosco gli ultimi 3 valore
	//dopo aver fatto la list folder
	}
	
	public void set_size(int size) {
		this.size=size;
	}
	
	public void set_revision(String revision) {
		this.revision=revision;
	}
	
	public void set_lastModify(Time lastModify) {
		this.lastModify=lastModify;
	}
	
	public int get_size() {
		return this.size;
	}
	
	public String get_revision() {
		return this.revision;
	}
	
	public Time get_lastModify() {
		return this.lastModify;
	}

	public JSONObject toJSONObject() {
		JSONObject result=super.toJSONObject();
		result.put("last modify", this.lastModify.toString());
		result.put("size", this.size);
		result.put("revision", this.revision);
		return result;
	}
}
