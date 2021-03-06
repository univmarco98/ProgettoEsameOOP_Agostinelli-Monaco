package application.utility;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;
import application.file.*;
import application.utility.json.JsonHandler;

/**
 * Classe per organizzare tutti i tipi di elementi (deleted, folder, file) in strutture dati di 
 * tipo vector, opportunamente parametrizzate. 
 * 
 * @author Marco 
 * @author Matteo
 *
 */

public class ArrayType implements Serializable {

	
	private static final long serialVersionUID = 1;
	private Vector<Deleted> vecDeleted = new Vector<Deleted>();
	private Vector<Folder>  vecFolder  = new Vector<Folder>();
	private Vector<File>    vecFile    = new Vector<File>();
	/**
	 * Costruttore della classe ArrayType
	 */
	public ArrayType() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Metodo per aggiungere un elemento ad ArrrayType
	 * 
	 * @param jsonElement JSONObject che puo' essere un deleted o folder o file
	 */
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
		
		File objFile = new File("File", name, path, id);
		
		vecFile.add(objFile);
		
	}
	
	/**
	 * Metodo per ottenre un Vector di deleted contenuti in ArrayType
	 * 
	 * @return Vector di Deleted
	 */
	public Vector<Deleted> get_vector_deleted() {
		return vecDeleted;
	}
	
	/**
	 * Metodo per ottenre un Vector di folder contenuti in ArrayType
	 * 
	 * @return Vector di Folder
	 */
	
	public Vector<Folder> get_vector_folder() {
		return vecFolder;
	}
	
	/**
	 * Metodo per ottenre un Vector di file contenuti in ArrayType
	 * 
	 * @return Vector di File
	 */
	
	public Vector<File> get_vector_file() {
		return vecFile;
	}	
	
	/**
	 * Metodo per cercare un elemento per nome in un ArrayType 
	 * 
	 * @param nome nome elemento da cercare
	 * @return Vector di Deleted (superclasse di Folder e File) trovati
	 */
	public Vector<Deleted> fetch(String nome) {
		Vector<Deleted> vecDel=new Vector<Deleted>();
		
		Iterator<Deleted> vDI=vecDeleted.iterator();
		while(vDI.hasNext()) {
			Deleted pippo=vDI.next();
			if ( pippo.getName().contains(nome) )
				vecDel.add(pippo);
		}		
		Iterator<Folder> vFI=vecFolder.iterator();
		while(vFI.hasNext()) {
			Deleted pippo=vFI.next();
			if ( pippo.getName().contains(nome) )
				vecDel.add(pippo);
		}		
		Iterator<File> vFiI=vecFile.iterator();
		while(vFiI.hasNext()) {
			Deleted pippo=vFiI.next();
			if ( pippo.getName().contains(nome) )
				vecDel.add(pippo);
		}
				
		return vecDel;
	}

	@Override
	public String toString() {
		return "ArrayType [vecDeleted=" + vecDeleted + ", vecFolder=" + vecFolder + ", vecFile=" + vecFile + "]";
	}

}
