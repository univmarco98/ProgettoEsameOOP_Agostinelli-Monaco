/**
 * 
 */
package application.file;

import java.io.Serializable;
import java.util.regex.Pattern;

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
	
	public void setSize(int size) {
		this.size=size;
	}
	
	public void setRevision(String revision) {
		this.revision=revision;
	}
	
	public void setLastModify(Time lastModify) {
		this.lastModify=lastModify;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public String getRevision() {
		return this.revision;
	}
	
	public Time getLastModify() {
		return this.lastModify;
	}
	
	public String getExtetion() {
		String[] splittedArray=this.getName().split(Pattern.quote("."));
		return splittedArray[splittedArray.length-1];
	}
	

	public JSONObject toJSONObject() {
		JSONObject result=super.toJSONObject();
		result.put("last modify", this.lastModify.toString());
		result.put("size", this.size);
		result.put("revision", this.revision);
		return result;
	}
}
