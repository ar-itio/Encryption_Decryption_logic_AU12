package com.banking.system;

import java.security.PublicKey;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping("/hello")
	public String Hello() {
		return "Hello Deepti";
		
	}
	

    
    
	@RequestMapping("/encrypt")
    public String encryptMessage() {
		return "Good morning"; 
	}

}
