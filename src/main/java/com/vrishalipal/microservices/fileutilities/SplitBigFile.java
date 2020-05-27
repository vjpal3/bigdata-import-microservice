package com.vrishalipal.microservices.fileutilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class SplitBigFile {
		
	public static void readLargeFileAndSplit() {
		
		StringBuilder text = new StringBuilder();	
		ClassPathResource inputPathResource = new ClassPathResource("data/PS_Sample_log.csv");
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				inputPathResource.getInputStream()))) {
			int count = 0;
			
			int fileCount = 0;
			String line = in.readLine(); 
			
			while ((line = in.readLine()) != null) {
				text.append(line).append("\n");
				count++;
				
				if(count > 2) {
					fileCount++;
					writeSmallFile(text.toString(), fileCount);
					count = 0;
					text.setLength(0);
				}
			}
			if(text.length() != 0) {
				fileCount++;
				writeSmallFile(text.toString(), fileCount);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeSmallFile(String text, int fileCount) throws IOException {
		
		String filenameSuffix = "PS_Sample_log_Part_";
		String newFilename = filenameSuffix + fileCount + ".csv";
		
		File file = new File(newFilename);
		if(!file.exists()) {
			file.createNewFile();
		}					
		
		try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(newFilename), "UTF-8"))) {
				out.write(text.toString());		
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void startSplit() {
		readLargeFileAndSplit();
	}
}
