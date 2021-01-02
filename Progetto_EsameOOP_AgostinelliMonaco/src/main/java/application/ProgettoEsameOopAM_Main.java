package application;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import application.utility.ArrayType;
import application.utility.Time;
import application.utility.json.JsonHandler;
import stats_and_filters.Statistics;
import application.file.*;

@SpringBootApplication
public class ProgettoEsameOopAM_Main {

	public static void main(String[] args) {

		SpringApplication.run(ProgettoEsameOopAM_Main.class, args);
		
	}//main

}
