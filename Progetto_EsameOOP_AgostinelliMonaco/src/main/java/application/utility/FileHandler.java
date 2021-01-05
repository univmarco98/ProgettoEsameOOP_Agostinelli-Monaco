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
 * 
 * Classe che rende disponibili alcuni metodi utili al salvataggio e caricamento su/da file
 * delle classi utilizzate per manipolare le informazioni sui metadata di dropbox
 * @author Marco and Matteo
 *
 */

public class FileHandler {
	/**
	 * Metodo per salvare un oggetto ArrayType in un file di testo (.txt)
	 * Il salvataggio prevede che il file sia nominato con la data del giorno
	 * del salvataggio stesso.
	 * es: se si salva in data 5 gennaio 2020 allora il file sarà nominato: 20200105.txt
	 * 
	 * @param aT Oggetto ArrayType che verrà salvato su file
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
	 * Metodo per caricare un oggetto ArrayType da un file di testo nominato come "yyyymmdd.txt"
	 * 
	 * @param nome_file Oggetto String che rappresenta il nome del file da caricare ("yyyymmdd.txt")
	 * @exception MyFileNotFoundException Se il file non viene trovato(FileNotFoundException) allora viene lanciata l'eccezione MyFileNotFoundException
	 * @return ArrayType contenente i metadata giornalieri del file
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
	
	/**
	 * Metodo per caricare un il token, in formato String ,dal file di testo nominato "Config.txt" che deve essere 
	 * residente nella catella DataBase
	 * @return ritorna il token in formato String
	 */
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
