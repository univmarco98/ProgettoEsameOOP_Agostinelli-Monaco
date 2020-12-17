/**
 * 
 */
package stats_and_filters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import application.file.*;
import application.json.JsonHandler;
import application.utility.ArrayType;
import application.utility.FileHandler;

/**
 * @author Matteo
 *
 */
public class Statistics {

	/**
	 * 
	 */
	public Statistics() {
		// TODO Auto-generated constructor stub
	}
	
	public static Vector<Vector> difference(String data1, String data2) {
		
		ArrayType aT1=FileHandler.caricaFile(data1+".txt");
		ArrayType aT2=FileHandler.caricaFile(data2+".txt");
		
		System.out.println("confronto deleted");

		Vector<Deleted> deleted = FileHandler.caricaFile(data2+".txt").get_vector_deleted();
		Vector<Integer> erPosition=findEreseableDel(aT1.get_vector_deleted(), aT2.get_vector_deleted());
		deleted=deletedVelementDel( deleted, erPosition);
		
		Vector<Folder> folder = FileHandler.caricaFile(data2+".txt").get_vector_folder();
		erPosition=findEreseableFol(  aT1.get_vector_folder(), aT2.get_vector_folder());
		folder=deletedVelementFol( folder, erPosition);
		
		Vector<File> modificatedFile = FileHandler.caricaFile(data2+".txt").get_vector_file();
		Vector<File> newAddedFile = FileHandler.caricaFile(data2+".txt").get_vector_file();
		Vector<Vector> all=findEreseableFil(  aT1.get_vector_file(), aT2.get_vector_file(), data2);
		erPosition=(Vector<Integer>)all.get(0); //non modificati da "eliminare"
		Vector<Integer> newPosition=(Vector<Integer>)all.get(1);
		//
		modificatedFile=deletedVelementFil( modificatedFile, erPosition);
		newAddedFile=deletedVelementFil( newAddedFile, newPosition);
		
		System.out.println("deleted elements: "+deleted.size());
		System.out.println("new folders: "+folder.size());
		System.out.println("\tmodificated files: "+modificatedFile.size());
		System.out.println("\tnew added files: "+newAddedFile.size());
		
		//uso il Vector<Vector> all per fare il return senza usare un Vector<Vector> nuovo
		all.clear();
		all.add(deleted);
		all.add(folder);
		all.add(modificatedFile);
		all.add(newAddedFile);
		return all;
	}
	
	private static Vector<Integer> findEreseableDel(Vector<Deleted> aT1, Vector<Deleted> aT2){
		Vector<Integer> erPosition= new Vector<Integer>();
		
		Iterator<Deleted> delI2 = (Iterator<Deleted>) aT2.iterator();
		int position=0;
		while(delI2.hasNext()) {
			Deleted tmp2 = delI2.next();
			
			Iterator<Deleted> delI1=(Iterator<Deleted>) aT1.iterator();
			while (delI1.hasNext() ) {
				Deleted tmp1=delI1.next();
				
				if( tmp1.get_path().equalsIgnoreCase(tmp2.get_path() ) ) {
					erPosition.add(position);
					//System.out.println(tmp2+"  "+tmp1);
					//System.out.println(deleted.remove(tmp1));
					break;
				}	
			}	
			position++;
		}
		return erPosition;
	}
	
	private static Vector<Integer> findEreseableFol(Vector<Folder> aT1, Vector<Folder> aT2){
		Vector<Integer> erPosition= new Vector<Integer>();
		
		Iterator<Folder> delI2 = (Iterator<Folder>) aT2.iterator();
		int position=0;
		while(delI2.hasNext()) {
			Folder tmp2 = delI2.next();
			
			Iterator<Folder> delI1=(Iterator<Folder>) aT1.iterator();
			while (delI1.hasNext() ) {
				Folder tmp1=delI1.next();
				
				if( tmp1.get_path().equalsIgnoreCase(tmp2.get_path() ) ) {
					erPosition.add(position);
					//System.out.println(tmp2+"  "+tmp1);
					//System.out.println(deleted.remove(tmp1));
					break;
				}	
			}	
			position++;
		}
		return erPosition;
	}
	
	private static Vector<Vector> findEreseableFil(Vector<File> aT1, Vector<File> aT2, String date2){
		
		Vector<Integer> erPosition= new Vector<Integer>();
		Vector<Integer> notNewPosition= new Vector<Integer>();
		
		Iterator<File> delI2 = (Iterator<File>) aT2.iterator();
		int position=0;
		boolean flag=false;
		while(delI2.hasNext()) {
			File tmp2 = delI2.next();
			if( tmp2.get_lastModify().toString().equals(date2)  ) {
				Iterator<File> delI1=(Iterator<File>) aT1.iterator();
				while (delI1.hasNext() ) {
					File tmp1=delI1.next();
					
					if( (tmp1.get_id().equalsIgnoreCase(tmp2.get_id())) ) {//file non modificato
						erPosition.add(position);//è uguale a prima
						flag=true;
						break;
					}
				}
				//if(erPosition.lastElement()!=position) {//CONDIZIONE EQUIVALENTE (PIACEVA A MATTEO) 
				if(!flag) { //file non nuovo
					notNewPosition.add(position);
				}
				flag=false;
			}
			else //file ne nuovo ne modificato
			{
				erPosition.add(position);
				notNewPosition.add(position);
			}
			position++;
		}
		Vector<Vector> result= new Vector<Vector>();
		result.add(erPosition);
		result.add(notNewPosition);
		return result;
	}
	
	private static Vector<Deleted> deletedVelementDel(Vector<Deleted> Vec, Vector<Integer> index){
		while ( index.size()!=0) {	
			int tmp = index.lastElement();
			index.remove(index.lastElement());
			Vec.remove(tmp);
		}
	return Vec;
	}
	
	private static Vector<Folder> deletedVelementFol(Vector<Folder> Vec, Vector<Integer> index){
		while ( index.size()!=0) {	
			int tmp = index.lastElement();
			index.remove(index.lastElement());
			Vec.remove(tmp);
		}
	return Vec;
	}
	
	private static Vector<File> deletedVelementFil(Vector<File> Vec, Vector<Integer> index){
		while ( index.size()!=0) {	
			int tmp = index.lastElement();
			index.remove(index.lastElement());
			Vec.remove(tmp);
		}
	return Vec;
	}
}
