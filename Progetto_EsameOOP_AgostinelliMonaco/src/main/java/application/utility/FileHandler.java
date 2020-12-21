/**
 * 
 */
package application.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import application.exception.MyFileNotFoundException;

/**
 * @author MARCO
 *
 */
public class FileHandler {
	/**
	 * Metodo per salvare un oggetto in un file di testo .json.
	 * Posso scegliere se salvare un JSONObject oppure un JSONArray.
	 * 
	 * @param nome_file Nome del file in cui salvare l'oggetto.
	 * @param isObject Specifica se l'oggetto da salvare � un JSONObject oppure un JSONArray.
	 */
	public static void saveFile(ArrayType aT) {
		SimpleDateFormat data = new SimpleDateFormat();
		data.applyPattern("yyyyMMdd");
		String dataStr = data.format(new Date()); // data corrente (20 febbraio 2014)
		String nome_file = (dataStr+".txt");
		try {
			ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("DataBase\\"+nome_file)));
			file_output.writeObject(aT);;
			file_output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo per leggere un oggetto da un file di testo .json.
	 * Posso scegliere se caricare un JSONObject oppure un JSONArray.
	 * 
	 * @param nome_file Nome del file da cui leggere l'oggetto.
	 * @param isObject Specifica se l'oggetto da salvare � un JSONObject oppure un JSONArray.
	 */
	public static ArrayType caricaFile(String nome_file) throws MyFileNotFoundException {
		ArrayType aT=null;
		try {
			ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream("DataBase\\"+nome_file)));
			aT=(ArrayType)file_input.readObject();
			file_input.close();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			throw new MyFileNotFoundException ("File non trovato");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return aT;
	}
}
