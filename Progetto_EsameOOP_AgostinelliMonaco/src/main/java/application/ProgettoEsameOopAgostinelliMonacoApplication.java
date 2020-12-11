package application;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import application.json.*;

@SpringBootApplication
public class ProgettoEsameOopAgostinelliMonacoApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ProgettoEsameOopAgostinelliMonacoApplication.class, args);
		String app=new ApiHandler().apicall_list_folder("");
		//System.out.println(app);
		Vector<JSONObject> temp=JsonHandler.format_list_folder(app);
		JSONObject myobj=temp.get(0);
		System.out.println(myobj);
		System.out.println(JsonHandler.getFileType(myobj));
		//Crea i 3 Vector per i 3 tipi di file
		
	}

}
