/**
 * 
 */
package application;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Vector;
import stats_and_filters.Statistics;
import application.file.Deleted;
import application.json.JsonHandler;

/**
 * @author MARCO
 *
 */
@RestController
public class SimpleRESTController {
	//api mi arriva api per aggiornare
	
	@GetMapping("/updateDatabase")
	public String updateDatabase() {
		//Routine_GetAndSave_Datas.routine_run();
		return("OK");
	}
	
	@GetMapping("/generalStats")
	public JSONObject generalStats() {
		//return Statistics.difference("20201216","20201217");
		return JsonHandler.ritornaJ();
	}
	
}
