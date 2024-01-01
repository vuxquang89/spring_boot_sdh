package vux.codejava.util;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class GoogleApiUtil {

	private static final String APPLICATION_NAME = "Google API Integration";
	  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	  private static final String TOKENS_DIRECTORY_PATH = "/var/tmp/tokens/path";
//	  private static final String TOKENS_DIRECTORY_PATH = "tokens/path";

	  /**
	   * Global instance of the scopes required by this quickstart.
	   * If modifying these scopes, delete your previously saved tokens/ folder.
	   */
	  private static final List<String> SCOPES =
	      Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
	  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

	  /**
	   * Creates an authorized Credential object.
	   *
	   * @param HTTP_TRANSPORT The network HTTP Transport.
	   * @return An authorized Credential object.
	   * @throws IOException If the credentials.json file cannot be found.
	   */
	  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
	      throws IOException {
	    // Load client secrets.
	    InputStream in = GoogleApiUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
	    if (in == null) {
	      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
	    }
	    GoogleClientSecrets clientSecrets =
	        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

	    // Build flow and trigger user authorization request.
	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
	        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
	        .setAccessType("offline")
	        .build();
	    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(32838).build();
	    //return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//	    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost("10.255.254.111").setPort(8888).build();
//	    LocalServerReceiver receiver = new LocalServerReceiver();
	    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	  }
	  
	  public String getURL(final NetHttpTransport HTTP_TRANSPORT)
		      throws IOException {
		    // Load client secrets.
		    InputStream in = GoogleApiUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		    if (in == null) {
		      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		    }
		    GoogleClientSecrets clientSecrets =
		        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		    // Build flow and trigger user authorization request.
		    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
		        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
		        .setAccessType("offline")
		        .build();
		    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(51607).build();
		    String redirectUri = receiver.getRedirectUri();
		      AuthorizationCodeRequestUrl authorizationUrl =
		          flow.newAuthorizationUrl().setRedirectUri(redirectUri);
		      
		    return authorizationUrl.build();
		  }
	  
	  public List<String> getDataFromSheet(String nameSheet) throws GeneralSecurityException, IOException {
		  final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//		  final String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
//		  final String range = "Class Data!A10:F";
		  final String spreadsheetId = "1toNRoC9rX7tAD3mdgYfHveOJldlL75BGwDYs0d23HYI";
		  final String range = "Data "+ nameSheet +"!B1:E1";
		    Sheets service =
		        new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
		            .setApplicationName(APPLICATION_NAME)
		            .build();
		    ValueRange response = service.spreadsheets().values()
		        .get(spreadsheetId, range)
		        .execute();
		    List<List<Object>> values = response.getValues();
//		    Map<Object, Object> storeDataFromGoogleSheet = new HashMap<>();
		    List<String> shiftNames = new ArrayList<String>();
		    if (values == null || values.isEmpty()) {
		      System.out.println("No data found.");
		    } else {
		      System.out.println("Name, Major");
		      for (List row : values) {
		    	  shiftNames.add((String)row.get(2));
		    	  shiftNames.add((String)row.get(3));
//		    	  storeDataFromGoogleSheet.put(row.get(0), row.get(2));
		        // Print columns A and E, which correspond to indices 0 and 4.
		        System.out.printf("%s, %s\n", row.get(0), row.get(3));
		      }
		      
//		      return storeDataFromGoogleSheet;
		    }
//		    return storeDataFromGoogleSheet;
		    return shiftNames;
	  }

	  /**
	   * Prints the names and majors of students in a sample spreadsheet:
	   * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
	   */
//	  public static void main(String... args) throws IOException, GeneralSecurityException {
//		    // Build a new authorized API client service.
//		    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//		    final String spreadsheetId = "1toNRoC9rX7tAD3mdgYfHveOJldlL75BGwDYs0d23HYI";
//		    final String range = "Data MT!B1:E1";
//		    Sheets service =
//		        new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//		            .setApplicationName(APPLICATION_NAME)
//		            .build();
//		    ValueRange response = service.spreadsheets().values()
//		        .get(spreadsheetId, range)
//		        .execute();
//		    List<List<Object>> values = response.getValues();
//		    if (values == null || values.isEmpty()) {
//		      System.out.println("No data found.");
//		    } else {
//		      System.out.println("Name, Major");
//		      for (List row : values) {
//		        // Print columns A and E, which correspond to indices 0 and 4.
//		        System.out.printf("%s, %s\n", row.get(0), row.get(3));
//		      }
//		    }
//		  }
}
