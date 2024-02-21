package vux.codejava.controller.shift;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vux.codejava.entity.shift.ShiftRequest;
import vux.codejava.service.GoogleApiService;

@RestController
@RequestMapping("/api/shift")
public class ShiftRestController {
	
	@Autowired
	private GoogleApiService googleApiService;

	@PostMapping()
	public ResponseEntity<?> addShift(@RequestBody ShiftRequest shiftRequest) {
		System.out.println(shiftRequest.getNote());
		return ResponseEntity.status(HttpStatus.OK).body("OK");
	}
	
	@GetMapping("/getData")
	public List<String> readDataFromGoogleSheet() throws GeneralSecurityException, IOException {
		return googleApiService.readDataFromGoogleSheet("MT");
	}
}
