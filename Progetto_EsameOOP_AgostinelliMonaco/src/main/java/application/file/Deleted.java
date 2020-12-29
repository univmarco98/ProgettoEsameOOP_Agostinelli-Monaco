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
public class Deleted implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	private String tag,name,path;
	
	public Deleted() {
		this.path=null;
		this.name=null;
		this.tag=null;
	}
	
	public Deleted(String tag, String name, String path) {
		this.path=path;
		this.name=name;
		this.tag=tag;
	}

	
	public String getPath() {
		return this.path;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getTag() {
		return this.tag;
	}

	
	public void setPath(String path) {
		this.path=path;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setTag(String tag) {
		this.tag=tag;
	}
	
	
	public String getExtetion() {
		String[] splittedArray=this.getName().split(Pattern.quote("."));
		return splittedArray[splittedArray.length-1];
	}
	
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
