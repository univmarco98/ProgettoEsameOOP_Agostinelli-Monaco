package application.exception;

public class MyMissingFile extends Exception {


	public MyMissingFile() {
		super();
	}	
	public MyMissingFile(String msg) {
		super(msg);
	}

}
