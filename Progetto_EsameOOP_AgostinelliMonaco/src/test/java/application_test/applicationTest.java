package application_test;
import application.exception.*;
import application.file.File;
import application.utility.FileHandler;
import application.utility.Time;

import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class applicationTest {

	File testFile;
	JSONObject testjo;
	@BeforeEach
	void setUp() {
		//-INIZIALIZZAZIONE testFile---------------------------
			testFile=new File("tag", "name", "path", "id");
			Time lastModify=new Time(2020,12,18,10,10);
			testFile.setLastModify(lastModify);
			String revision ="xtcfgvjbhk";
			testFile.setRevision(revision);
			int size=102030;
			testFile.setSize(size);
		//-----------------------------------------------------
		//-INIZIALIZZAZIONE testjo---------------------------
			testjo=testFile.toJSONObject();
		//-----------------------------------------------------
		//-CARICAFILE
			//non serve inizializzare nulla
		//------------------------------------------------------
	}

	@AfterEach
	void tearDown(){
	}

	@Test
	void testToString_File() {
		assertEquals("File [lastModify=20201218, revision=xtcfgvjbhk, size=102030Folder [id=idDeleted [tag=tag, name=name, path=path]]]", testFile.toString());
	}
	
	@Test
	void testToJSONObject_File() {
		assertEquals("{\"path\":\"path\",\"size\":102030,\"name\":\"name\",\"id\":\"id\",\"type\":\"tag\",\"last modify\":\"20201218\",\"revision\":\"xtcfgvjbhk\"}",testjo.toString());
	}
	
	@Test
	void testFileHandler_caricaFile_Exception() {
		MyFileNotFoundException exception=assertThrows(MyFileNotFoundException.class, () -> {FileHandler.caricaFile("pincoPallo");} );
		assertEquals("File non trovato", exception.getMessage());
		/*se si fosse aperto un file del database, per esempio 20201214.txt il test avrebbe restituito
		 una failure perché l'eccezione che si aspetta l'assertThrows non viene giustamente lanciata*/
	}

}
