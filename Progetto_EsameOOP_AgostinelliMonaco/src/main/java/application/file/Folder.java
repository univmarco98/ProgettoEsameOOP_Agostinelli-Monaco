/**
 * 
 */
package application.file;

/**
 * @author Matteo
 *
 */
public class Folder extends Deleted {
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
	
	

}
