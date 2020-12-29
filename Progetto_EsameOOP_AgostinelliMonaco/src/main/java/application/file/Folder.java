/**
 * 
 */
package application.file;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

/**
 * @author Matteo
 *
 */
public class Folder extends Deleted implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	private String id;

	public Folder(String tag, String name, String path, String id) {
		super(tag, name, path);
		this.id=id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id=id;
	}
	
	//overriding del metodo in quanto una foder non ha estensione
	public String getExtetion() {
		return this.getName();

	}
	
	public JSONObject toJSONObject() {
		JSONObject result=super.toJSONObject();
		result.put("id", this.id);
		return result;
	}
}
