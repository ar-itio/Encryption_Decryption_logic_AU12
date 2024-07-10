package com.banking.system;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EncryptionDecryptionController {
	
	 @Autowired
	    private EncryptionDecryptionService encryptionDecryptionService;

	    private final String VPA_RSA_MODULUS = "xTSiS4+I/x9awUXcF66Ffw7tracsQfGCn6g6k/hGkLquHYMFTCYk4mOB5NwLwqczwvl8HkQfDShGcvrm47XHKUzA8iadWdA5n4toBECzRxiCWCHm1KEg59LUD3fxTG5ogGiNxDj9wSguCIzFdUxBYq5ot2J4iLgGu0qShml5vwk=";
	    private final String VPA_RSA_PRIVATE_KEY_D = "g1WAWI4pEK9TA7CA2Yyy/2FzzNiu0uQCuE2TZYRNiomo96KQXpxwqAzZLw+VDXfJMypwDMAVZe/SqzSJnFEtZxjdxaEo3VLcZ1mnbIL0vS7D6iFeYutF9kF231165qGd3k2tgymNMMpY7oYKjS11Y6JqWDU0WE5hjS2X35iG6mE=";
	    private final String VPA_RSA_EXPONENT = "AQAB";

	    @GetMapping("/encrypt")
	    public String encrypt() {
	        try {
	        	String message= "12345";
	        			
	            PublicKey publicKey = encryptionDecryptionService.readPublicKeyFromMod(VPA_RSA_MODULUS, VPA_RSA_EXPONENT);
	            byte[] encryptedMessage = encryptionDecryptionService.encrypt(message, publicKey);
	            return Base64.getEncoder().encodeToString(encryptedMessage);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Encryption failed: " + e.getMessage();
	        }
	    }

	    @GetMapping("/decrypt")
	    public String decrypt( ) {
	        try {
	        	String encryptedMessage= "eG0L4p1sTcnLYKxE2DQ9HloDg2G9OiR3uQ8Au21vq5yGdUOAkEj3M7UVHADDg2w0hlubJ982hXgQWGeBieSHcHP91kh1Kihk27VnXL/mEXM4fD54l3KxtxBKM2Vb+58vPLh+EcE20y2UR/CbxCYeXzvaH5IQQHtW6sEVxdcZ5Wg=";

	            PrivateKey privateKey = encryptionDecryptionService.readPrivateKeyFromMod(VPA_RSA_MODULUS, VPA_RSA_PRIVATE_KEY_D);
	            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);
	            return encryptionDecryptionService.decrypt(encryptedBytes, privateKey);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Decryption failed: " + e.getMessage();
	        }
	    }

}
