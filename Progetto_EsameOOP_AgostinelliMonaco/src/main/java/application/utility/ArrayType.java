/**
 * 
 */
package application.utility;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.Vector;
import application.file.*;
import application.json.JsonHandler;

/**
 * @author Matteo
 *
 */
public class ArrayType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	private Vector<Deleted> vecDeleted = new Vector<Deleted>();
	private Vector<Folder>  vecFolder  = new Vector<Folder>();
	private Vector<File>    vecFile    = new Vector<File>();
	/**
	 * 
	 */
	public ArrayType() {
		// TODO Auto-generated constructor stub
	}
	
	public void add_element(JSONObject jsonElement) {
		String type=JsonHandler.getFileType(jsonElement);
		//System.out.println(jsonElement);
		
		
		if ( type.equals("deleted") ) 
			add_deleted(jsonElement);
		
		else if (type.equals("folder") )
			add_folder(jsonElement);
		
		else if (type.equals("file") )
			add_file(jsonElement);
		
		else
			System.out.print("Tipo non trovato");
	}
	
	private void add_deleted(JSONObject jsonDeleted) {
		String name=jsonDeleted.get("name").toString();
		String path=jsonDeleted.get("path_display").toString();
		
		Deleted objDeleted = new Deleted("deleted", name, path);
		
		vecDeleted.add(objDeleted);
	}
	
	private void add_folder(JSONObject jsonFolder) {
		String name=jsonFolder.get("name").toString();
		String path=jsonFolder.get("path_display").toString();
		String id  =jsonFolder.get("id").toString();
		
		Folder objFolder = new Folder("Folder", name, path, id);
		
		vecFolder.add(objFolder);
		
	}
	
	private void add_file(JSONObject jsonFile) {
		String name=jsonFile.get("name").toString();
		String path=jsonFile.get("path_display").toString();
		String id  =jsonFile.get("id").toString();
		
		File objFile = new File("Folder", name, path, id);
		
		vecFile.add(objFile);
		
	}
	
	public Vector<Deleted> get_vector_deleted() {
		return vecDeleted;
	}
	
	public Vector<Folder> get_vector_folder() {
		return vecFolder;
	}
	
	public Vector<File> get_vector_file() {
		return vecFile;
	}

}
