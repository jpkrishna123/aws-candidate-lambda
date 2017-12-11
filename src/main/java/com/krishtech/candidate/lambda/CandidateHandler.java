package com.krishtech.candidate.lambda;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.Request;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.krishtech.candidate.service.S3Service;
import com.krishtech.candidate.service.SimpleEmailService;

public class CandidateHandler implements RequestHandler<Request, String> {
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private SimpleEmailService emailService;
	
    @Override
    public String handleRequest(Request request, Context context) {
    	
    	Map<String, String> params = request.getParameters();
    	
    	String resumeKey = params.get("resume_s3_key");
    	
    	String subject = "";
    	
    	if(resumeKey != null && resumeKey.length() > 0) {
    		String resumeURL = s3Service.getURL(resumeKey);
    		
    		subject = String.format("New candidate details %s",
    				params.get("first_name"));
    		
    		String body = String
    				.format("Hello Recirter,\n\nHere is the new candidate details : please review my resume:\n%s\n\nFirst Name: %s\nLastName: %s \nEmail : %s \n Phone Number: %s ",
    						resumeURL, params.get("first_name"), params.get("last_name"), params.get("email"),
    						params.get("phone_number"));
    		
    		emailService.sendMail(subject, body);
    	}
    	
    	return subject;
        
    }
    
    
}