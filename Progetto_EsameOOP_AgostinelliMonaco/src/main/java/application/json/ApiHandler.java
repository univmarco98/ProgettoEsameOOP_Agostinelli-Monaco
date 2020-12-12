/**
 * 
 */
package application.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author Matteo
 *
 */
public class ApiHandler {
	private String token="apLbyiJWIa0AAAAAAAAAAaoW0voRsvsHVHefHG8-fiBH7UGA9tcr9JXvM4SWGncg";
	
	/**
	 * 
	 */
	public ApiHandler() {
		
	}
	
	public void setToken(String token) {
		this.token=token;
	}
	
	public String apicall_get_metadata(String path) {
		
		String data = "";
		String line = "";
		String url="https://api.dropboxapi.com/2/files/get_metadata";
		try {

			HttpURLConnection openConnection = (HttpURLConnection) new URL(url).openConnection();
			openConnection.setRequestMethod("POST"); //one of: GET POST HEAD OPTIONS PUT DELETE TRACE are legal, subject to protocol restrictions
			openConnection.setRequestProperty("Authorization",
					"Bearer "+token);
			openConnection.setRequestProperty("Content-Type", "application/json");
			openConnection.setRequestProperty("Accept", "application/json");
			openConnection.setDoOutput(true);
			
			String jsonBody = "{\r\n"
					+ "    \"path\": \"" + path + "\",\r\n"
					+ "    \"include_media_info\": true,\r\n"
					+ "    \"include_deleted\": true,\r\n"
					+ "    \"include_has_explicit_shared_members\": false\r\n"
					+ "}";
			
			try (OutputStream os = openConnection.getOutputStream()) {
				byte[] input = jsonBody.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
			

			InputStream in = openConnection.getInputStream();

			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);

				while ((line = buf.readLine()) != null) {
					data += line;
				}

			}
			finally {
				in.close();
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public String apicall_list_folder(String path) {
		
		String data = "";
		String line = "";
		String url="https://api.dropboxapi.com/2/files/list_folder";
		try {

			HttpURLConnection openConnection = (HttpURLConnection) new URL(url).openConnection();
			openConnection.setRequestMethod("POST"); //one of: GET POST HEAD OPTIONS PUT DELETE TRACE are legal, subject to protocol restrictions
			openConnection.setRequestProperty("Authorization",
					"Bearer "+token);
			openConnection.setRequestProperty("Content-Type", "application/json");
			openConnection.setRequestProperty("Accept", "application/json");
			openConnection.setDoOutput(true);
			
			String jsonBody = "{\r\n"
					+ "    \"path\": \"" + path + "\",\r\n"
					+ "    \"recursive\": true,\r\n"
					+ "    \"include_media_info\": false,\r\n"
					+ "    \"include_deleted\": false,\r\n"
					+ "    \"include_has_explicit_shared_members\": false,\r\n"
					+ "    \"include_mounted_folders\": true,\r\n"
					+ "    \"include_non_downloadable_files\": true\r\n"
					+ "}";
			
			try (OutputStream os = openConnection.getOutputStream()) {
				byte[] input = jsonBody.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
			

			InputStream in = openConnection.getInputStream();

			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);

				while ((line = buf.readLine()) != null) {
					data += line;
				}

			}
			finally {
				in.close();
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	
	
	
	
	
}//class