/**
 * 
 */
package application.file;
import application.utility.Time;

/**
 * @author Matteo
 *
 */
public class File extends Folder {
	Time lastModify;
	String revision;
	int size;
	/**
	 * 
	 */
	public File (String tag, String name, String path, String id, Time latsModify, String revision, int size) {
		super (tag, name, path, id);
		this.lastModify=latsModify;
		this.revision=revision;
		this.size=size;
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

}
