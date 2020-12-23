/**
 * 
 */
package application.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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
	
	public static String caricaConfigs() {
		String temp=new String("");
		try {
			BufferedReader file_input=new BufferedReader(new FileReader("Database\\Config.txt"));
			temp=file_input.readLine();
			System.out.println(temp);
			file_input.close();
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) { //abbiamo messo eccezione generica perche ParseException non funziona correttamente
			System.out.println(e.getMessage());
		}
		return temp;
	}
}
