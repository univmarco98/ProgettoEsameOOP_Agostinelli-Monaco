package application.exception;

/**
 * Eccezione lanciata per indicate un'estensione di un file non contemplata dal programma
 * es: Il metodo JsonHandler.getJsonAllStats(....) viene lanciata se entrambe le estensioni non rientrano nelle estensioni di quelle valide
 * @author Marco and Matteo
 *
 */

public class NotMatchedExtentionException extends Exception{

	/**
	 * 
	 */
	public NotMatchedExtentionException() {
		// TODO Auto-generated constructor stub
	}
	
	public NotMatchedExtentionException(String msg) {
		super(msg);
	}

}
