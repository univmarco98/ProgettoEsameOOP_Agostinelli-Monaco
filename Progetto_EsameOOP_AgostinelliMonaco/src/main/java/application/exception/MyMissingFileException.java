package application.exception;

public class MyMissingFileException extends Exception {


	public MyMissingFileException() {
		super();
	}	
	public MyMissingFileException(String msg) {
		super(msg);
	}

}
