/**
 * 
 */
package application.file;

import org.json.simple.JSONObject;

/**
 * @author Matteo
 *
 */
public class Folder extends Deleted {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	private String id;

	public Folder(String tag, String name, String path, String id) {
		super(tag, name, path);
		this.id=id;
	}
	
	public String get_id() {
		return this.id;
	}
	
	public void set_id(String id) {
		this.id=id;
	}
	
	public JSONObject toJSONObject() {
		JSONObject result=super.toJSONObject();
		result.put("id", this.id);
		return result;
	}
}
