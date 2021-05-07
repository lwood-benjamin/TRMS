package com.revature.project_one.util;

import java.io.InputStream;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class S3Util {

	private static final Logger log = LogManager.getLogger(CassUtil.class);
	public static final Region region = Region.US_EAST_2;
	public static final String BUCKET_NAME = "reactive-0329-bwood";
	
	private static S3Util instance = null;
	private S3Client s3 = null;
	
	private S3Util() {
		log.trace("S3 Utility Constructor");
		s3 = S3Client.builder().region(region).build();
	}
	
	public static synchronized S3Util getInstance() {
		if(instance == null) {

			log.trace("Created S3 Instance");
			instance = new S3Util();
		}

		log.trace("Returning S3 Instance: "+instance.toString());
		return instance;
	}
	
	public void uploadToBucket(String key, byte[] file) {
		PutObjectRequest objRequest = PutObjectRequest.builder()
				.bucket(BUCKET_NAME).key(key).build();
		s3.putObject(objRequest, RequestBody.fromBytes(file));
		log.trace("Uploaded file to bucket: "+BUCKET_NAME);
	}
	
	public InputStream getObject(String key) {
		InputStream s = s3.getObject(GetObjectRequest.builder().bucket(BUCKET_NAME).key(key).build());
		log.trace("Got file: "+s.getClass()+" from bucket: "+BUCKET_NAME);
		return s;
	}
	
	public void listBuckets() {
		ListBucketsRequest list = ListBucketsRequest.builder().build();
		ListBucketsResponse response = s3.listBuckets(list);
		response.buckets().stream().forEach(x -> System.out.println(x.name()));
	}
	

}
