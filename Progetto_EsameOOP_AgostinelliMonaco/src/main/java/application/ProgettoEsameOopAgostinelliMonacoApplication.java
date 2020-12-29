package application;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import application.utility.ArrayType;
import application.utility.Time;
import stats_and_filters.Statistics;
import application.file.*;
import application.json.JsonHandler;

@SpringBootApplication
public class ProgettoEsameOopAgostinelliMonacoApplication {

	public static void main(String[] args) {

		//SpringApplication.run(ProgettoEsameOopAgostinelliMonacoApplication.class, args);
		File file=new File("tag", "name", "path", "id");
		Time lastModify=new Time(2020,12,18,10,10);
		file.setLastModify(lastModify);
		String revision ="xtcfgvjbhk";
		file.setRevision(revision);
		int size=102030;
		file.setSize(size);
		
		JSONObject testjo=file.toJSONObject();
			String s=testjo.toString();
			System.out.println(s);
	}//main

}
