package com.krishtech.candidate.service;
import java.io.IOException;
import java.net.URL;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.krishtech.candidate.exception.FileArchiveServiceException;

@Service
public class S3Service {
	
	private Logger logger = LoggerFactory.getLogger(S3Service.class);
	
	//@Autowired
	private AmazonS3Client s3Client;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	/**
	 * get resume to S3 and return as URL
	 * 
	 * @param key as String
	 * @return as String
	 * @throws IOException
	 */
	public String getURL(final String key) {
		URL signedUrl = null;
		
		try {
			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key);
			generatePresignedUrlRequest.setMethod(HttpMethod.GET);
			generatePresignedUrlRequest.setExpiration(DateTime.now().plusYears(1).toDate());
			signedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest); 

        }catch (Exception ex) {
        	throw new FileArchiveServiceException("An error occurred while creating s3 URL", ex);
        }
		return signedUrl.toString();
	}

	
}