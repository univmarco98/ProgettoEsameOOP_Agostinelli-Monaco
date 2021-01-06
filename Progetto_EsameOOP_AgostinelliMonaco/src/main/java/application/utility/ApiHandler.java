package application.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * @author Marco and Matteo
 *
 */

public class ApiHandler {
	private String token="";
	private String cursor,has_more;
	
	/**
	 * Costruttore di ApiHandler
	 */
	public ApiHandler() {
		this.setToken();
	}
	
	/**
	 * metodo per settare il token attraverso il metodo FileHandler.caricaConfigs()
	 */
	public void setToken() {
		System.out.println("setToken() avviato");
		this.token=FileHandler.caricaConfigs();
	}
	
	/** 
	 * Metodo per settare l'attributo cursor di ApiHandler
	 * @param cursor stringa ideantificativa del punto di break della rotta Dropbox list_folder
	 */
	public void setCursor(String cursor) {
		this.cursor=cursor;
	}
	
	/** 
	 * Metodo per settare l'attributo has_more di ApiHandler
	 * @param has_more stringa che indica se chiamare la rotta list_folder/continue (ovvero il metodo
	 * 		 apicall_list_folder_continue())
	 */
	public void setHasMore(String has_more) {
		this.has_more=has_more;
	}
	
	/**
	 * Metodo che restituisce l'attributo has_more
	 * @return has_more in formato String
	 */
	public String getHas_more() {
		return this.has_more;
	}
	
	/**
	 * Metodo che restituisce l'attributo token
	 * @return token in formato String
	 */
	public String getToken() {
		return this.token;
	}
	
	/**
	 * Metodo che invoca la rotta get_metadata di Dropbox, acquisisce il risultato e lo restituisce
	 * come una stringa, la quale stringa rispetta il formato di un file json testuale
	 * @param path path in formato String
	 * @return stringa rappresentativa del risultato della rotta Dropbox get_metadata
	 */
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
	
	/**
	 * Metodo che invoca la rotta list_folder di Dropbox, acquisisce il risultato e lo restituisce
	 * come una stringa, la quale stringa rispetta il formato di un file json testuale
	 * @param path path in formato String
	 * @return stringa rappresentativa del risultato della rotta Dropbox list_folder
	 * @throws IOException 
	 */
	public String apicall_list_folder(String path) throws IOException {
		
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
					+ "    \"include_deleted\": true,\r\n"
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
			throw new IOException();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * Metodo che fa la stessa cosa di apicall_list_folder() ma che è necessario invocare
	 * nel caso in cui il risultato del solo apicall_list_folder() sia troppo lungo per poter
	 * essere gestito in una sola passata(cioè attraverso la verifica dell'attributo json "has_more")
	 * @return stringa rappresentativa del risultato della rotta Dropbox list_folder
	 */
	public String apicall_list_folder_continue() {
		
		String data = "";
		String line = "";
		String url="https://api.dropboxapi.com/2/files/list_folder/continue";
		try {

			HttpURLConnection openConnection = (HttpURLConnection) new URL(url).openConnection();
			openConnection.setRequestMethod("POST"); //one of: GET POST HEAD OPTIONS PUT DELETE TRACE are legal, subject to protocol restrictions
			openConnection.setRequestProperty("Authorization",
					"Bearer "+token);
			openConnection.setRequestProperty("Content-Type", "application/json");
			openConnection.setRequestProperty("Accept", "application/json");
			openConnection.setDoOutput(true);
			
			String jsonBody = "{\r\n"
					+ "    \"cursor\": \""+cursor+"\"\r\n"
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

	@Override
	public String toString() {
		return "ApiHandler [token=" + token + ", cursor=" + cursor + ", has_more=" + has_more + "]";
	}
	
}