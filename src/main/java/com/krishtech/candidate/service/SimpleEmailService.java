package com.krishtech.candidate.service;

import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class SimpleEmailService {
	
	@Value("${to.email}")
	private static String toEmail;
	
	@Value("${from.email}")
	private static String fromEmail;
	
	@Value("${aws.region}")
	private static String awsRegion;
	
	@Value("${aws.configSet}")
	private static String configSet;
	
	public void sendMail(final String subjectStr, final String bodyStr) {
		Destination destination = new Destination()
				.withToAddresses(new String[] { toEmail });

		// Create the subject and body of the message.
		Content subject = new Content().withData(subjectStr);
		Content textBody = new Content().withData(bodyStr);
		Body body = new Body().withText(textBody);

		// Create a message with the specified subject and body.
		Message message = new Message().withSubject(subject).withBody(body);

		
		AmazonSimpleEmailService client = 
		          AmazonSimpleEmailServiceClientBuilder.standard()
		          // Replace US_WEST_2 with the AWS Region you're using for
		          // Amazon SES.
		            .withRegion(awsRegion).build();
		      SendEmailRequest request = new SendEmailRequest()
		          .withDestination(
		        		  destination)
		          .withMessage(message);
		      client.sendEmail(request);
	}
}
