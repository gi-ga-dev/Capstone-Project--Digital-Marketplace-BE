package com.gigadev.digitalmarketplace.downloadcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*")
//@CrossOrigin(origins = "https://gecko2code.vercel.app", allowedHeaders = "*")
@RequestMapping("/downloadcodes")
public class DownloadCodeController {
	
	@Autowired DownloadCodeService codeService;
	
	@PostMapping("/{prodId}/saveCode")
	@PreAuthorize("isAuthenticated()")
	@Operation(security = @SecurityRequirement(name = "bearer-authentication"))
	public ResponseEntity<DownloadCode> saveCode(@PathVariable Long prodId) {	
		return ResponseEntity.ok(codeService.saveCode("PlayToken", prodId));
	}

}
