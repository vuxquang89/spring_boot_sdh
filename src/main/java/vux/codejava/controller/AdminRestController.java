package vux.codejava.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import vux.codejava.util.GoogleApiUtil;

@RestController
@RequestMapping("/api")
public class AdminRestController {
	
	@Autowired
	private GoogleApiUtil googleApi;

	@GetMapping("/port")
	public ResponseEntity<?> getPort() throws GeneralSecurityException, IOException{
		
		int port = findOpenPort();
		
		return ResponseEntity.ok("port : " + port);
	}
	
	@GetMapping("/url")
	public ResponseEntity<?> getUrl() throws GeneralSecurityException, IOException{
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		
		return ResponseEntity.ok("url : " + googleApi.getURL(HTTP_TRANSPORT));
	}
	
	private int findOpenPort() {
	    try (ServerSocket socket = new ServerSocket(0)) {
	      socket.setReuseAddress(true);
	      return socket.getLocalPort();
	    } catch (IOException e) {
	      throw new IllegalStateException("No free TCP/IP port to start embedded HTTP Server on");
	    }
	}
}
