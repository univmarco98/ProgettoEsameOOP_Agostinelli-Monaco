/**
 * 
 */
package application.file;

import java.io.Serializable;

/**
 * @author Matteo
 *
 */
public class Deleted implements Serializable {
	private String tag,name,path;

	public Deleted(String tag, String name, String path) {
		this.path=path;
		this.name=name;
		this.tag=tag;
	}

	
	public String get_path() {
		return this.path;
	}
	
	public String get_name() {
		return this.name;
	}
	
	public String get_tag() {
		return this.tag;
	}

	
	public void set_path(String path) {
		this.path=path;
	}
	
	public void set_name(String name) {
		this.name=name;
	}
	
	public void set_tag(String tag) {
		this.tag=tag;
	}
	


}
