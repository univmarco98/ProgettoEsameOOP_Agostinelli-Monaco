/**
 * 
 */
package stats_and_filtres;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import application.file.*;
import application.json.JsonHandler;
import application.utility.ArrayType;

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
	
	public static void difference(String data1, String data2) {
		
		ArrayType aT1=JsonHandler.caricaFile(data1+".txt");
		ArrayType aT2=JsonHandler.caricaFile(data2+".txt");
		
		System.out.println("confronto deleted");

		Vector<Deleted> deleted = JsonHandler.caricaFile(data2+".txt").get_vector_deleted();
		Vector<Integer> erPosition=findEreseableDel(aT1.get_vector_deleted(), aT2.get_vector_deleted());
		deleted=deletedVelementDel( deleted, erPosition);
		
		Vector<Folder> folder = JsonHandler.caricaFile(data2+".txt").get_vector_folder();
		erPosition=findEreseableFol(  aT1.get_vector_folder(), aT2.get_vector_folder());
		folder=deletedVelementFol( folder, erPosition);
		
		Vector<File> file = JsonHandler.caricaFile(data2+".txt").get_vector_file();
		Vector<Vector> all=findEreseableFil(  aT1.get_vector_file(), aT2.get_vector_file());
		erPosition=(Vector<Integer>)all.get(0);
		
		file=deletedVelementFil( file, erPosition);
		
	System.out.println(deleted.size());
	System.out.println(folder.size());
	System.out.println(file.size());
		
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
	
	private static Vector<Vector> findEreseableFil(Vector<File> aT1, Vector<File> aT2){
		
		SimpleDateFormat data = new SimpleDateFormat();
		data.applyPattern("yyyyMMddhhmm");
		String dataOggi = data.format(new Date()); // data corrente 202012171327
		
		Vector<Integer> erPosition= new Vector<Integer>();
		Vector<Integer> newPosition= new Vector<Integer>();
		
		Iterator<File> delI2 = (Iterator<File>) aT2.iterator();
		int position=0;
		while(delI2.hasNext()) {
			File tmp2 = delI2.next();
			
			Iterator<File> delI1=(Iterator<File>) aT1.iterator();
			while (delI1.hasNext() ) {
				File tmp1=delI1.next();
				
				if( tmp1.get_id().equalsIgnoreCase(tmp2.get_id() )){
						if( tmp1.get_revision().equalsIgnoreCase(tmp2.get_revision()) ) {
							erPosition.add(position);//è uguale a prima
							break;
						}
				}
				else if ( tmp1.get_lastModify().toString().equals(dataOggi)  )
					newPosition.add(position);
				
				}	
			position++;
			}
		Vector<Vector> result= new Vector<Vector>();
		result.add(erPosition);
		result.add(newPosition);
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
