package vux.codejava.controller.shift;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vux.codejava.entity.shift.ShiftRequest;

@RestController
@RequestMapping("/api/shift")
public class ShiftRestController {

	@PostMapping()
	public ResponseEntity<?> addShift(@RequestBody ShiftRequest shiftRequest) {
		System.out.println(shiftRequest.getNote());
		return ResponseEntity.status(HttpStatus.OK).body("OK");
	}
}
