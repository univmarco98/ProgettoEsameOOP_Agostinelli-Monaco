package stats_and_filters;

import java.util.Iterator;
import java.util.Vector;

import application.exception.MyFileNotFoundException;
import application.exception.MyMissingFileException;
import application.file.*;
import application.utility.ArrayType;
import application.utility.FileHandler;

/**
 * Classe utile a elaborare statistiche manipolando file di testo con salvati oggetti di tipo ArrayType
 * @author Marco
 * @author Matteo
 *
 */

public class Statistics {
	
	/**
	 *  Metodo che, prendendo in input 2 date, estrapola tutte le differenze, tra le due date in questione,
	 * 	a livello di elementi creati, modificati o cancellati e le restituisce sotto forma di Vector di Vector.
	 * 	Dove i Vector interni si riferiscono rispettivamente a: elenco di Deleted, elenco di Folder, elenco di File modificati
	 *  e elenco di nuovi File.
	 *  Viene inoltre aggiunto un elemento Vector "vuoto" indicativo del fatto
	 *		che non è stato possibile trovare il file di una delle due date
	 *  
	 * @param data1 prima data in formato String
	 * @param data2 seconda data in formato String
	 * @return ritorna il Vector dove i Vector interni si riferiscono rispettivamente a: elenco di Deleted, elenco di Folder, elenco di File modificati
	 *  e elenco di nuovi File.
	 * @throws MyMissingFileException viene lanciata nel caso in cui entrambi i tentativi di apertura file
	 * 	non vadano a buon fine
	 */
	public static Vector<Vector> difference(String data1, String data2) throws MyMissingFileException {
		ArrayType aT1=new ArrayType();
		ArrayType aT2=new ArrayType();
		Vector<Vector> all=new Vector<Vector>();
		boolean error=false; //per getire delle date non conformi
		try {
			aT1=FileHandler.caricaFile(data1+".txt");
		}
		catch (MyFileNotFoundException e) {
			data1=Integer.toString( Integer.parseInt(data1)-1);
			
			try {
				aT1=FileHandler.caricaFile(data1+".txt");
				error=true;
			}
			catch (MyFileNotFoundException a) {
				throw new MyMissingFileException ("File non trovato "+ data1 /*data di 2° tentativo*/+
						" "+ (Integer.parseInt(data1)+1)/*data di 1° tentativo*/ );
			}
		}

		try {
			aT2=FileHandler.caricaFile(data2+".txt");
		}
		catch (MyFileNotFoundException e) {
			data2=Integer.toString( Integer.parseInt(data2)+1);
			
			try {
				aT2=FileHandler.caricaFile(data2+".txt");
				error=true;
			}
			catch (MyFileNotFoundException a) {
				throw new MyMissingFileException ("File non trovati "+ (Integer.parseInt(data2)-1)/*data di 1° tentativo*/
						+" "+ data2/*data di 2° tentativo*/);
			}
		}
		
		try {
			Vector<Deleted> deleted = FileHandler.caricaFile(data2+".txt").get_vector_deleted();
			Vector<Integer> erPosition=findEreseableDel(aT1.get_vector_deleted(), aT2.get_vector_deleted());
			deleted=deletedVelementDel( deleted, erPosition);
			
			Vector<Folder> folder = FileHandler.caricaFile(data2+".txt").get_vector_folder();
			erPosition=findEreseableFol(  aT1.get_vector_folder(), aT2.get_vector_folder());
			folder=deletedVelementFol( folder, erPosition);
			
			Vector<File> modificatedFile = FileHandler.caricaFile(data2+".txt").get_vector_file();
			Vector<File> newAddedFile = FileHandler.caricaFile(data2+".txt").get_vector_file();

			all=findEreseableFil(  aT1.get_vector_file(), aT2.get_vector_file(), data2);
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
			if (error) //se error è true
				all.add(new Vector()); // aggiungo un elemento Vector "vuoto" che poi mi servirà per capire
									    // che non è stato possibile trovare il file di una delle due date
		}
		catch (Exception e) {
		}
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
				
				if( tmp1.getPath().equalsIgnoreCase(tmp2.getPath() ) ) {
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
				
				if( tmp1.getPath().equalsIgnoreCase(tmp2.getPath() ) ) {
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
			if( tmp2.getLastModify().toString().equals(date2)  ) {
				Iterator<File> delI1=(Iterator<File>) aT1.iterator();
				while (delI1.hasNext() ) {
					File tmp1=delI1.next();
					
					if( (tmp1.getId().equalsIgnoreCase(tmp2.getId())) ) {//file non modificato
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
