package application.exception;

/**
 * Eccezione creata appositamente per essere lanciata qualora FileHandler.caricaFile()  non trova il file dentro DataBase 
 * 
 * @author Marco
 * @author Matteo
 *
 */

public class MyFileNotFoundException extends Exception{

	/**
	 * 
	 */
	public MyFileNotFoundException() {
		super();
	}	
	public MyFileNotFoundException(String msg) {
		super(msg);
	}

}
