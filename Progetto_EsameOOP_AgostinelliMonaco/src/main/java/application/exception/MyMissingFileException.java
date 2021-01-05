package application.exception;

/**
 * Eccezione creata appositamente per essere lanciata da Statistics.differencence qualora FileHandler.caricaFile()  non trova il file al secondo tentativo, dentro DataBase
 * 
 * @author Marco and Matteo
 *
 */

public class MyMissingFileException extends Exception {


	public MyMissingFileException() {
		super();
	}	
	public MyMissingFileException(String msg) {
		super(msg);
	}

}
