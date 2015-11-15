package framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NameGenerator {
	/*
	 * NameGenerator contains the getDemonName() method that returns a random name from the file with demon names
	 */

	private String filePath = "/home/tom/Copy/Java/pandemonium/src/framework/demon_names.txt";
	private List<String> demonNames = new ArrayList<>();
	
	public NameGenerator() {
		this.readNames();
	}
	
	public NameGenerator(String filePath) throws IOException {
		// overloaded constructor to change filePath to different file
		
		if (Files.exists(Paths.get(filePath))) {
			this.setFilePath(filePath);
		} else {
			throw new IOException("NameGenerator: FilePath does not exist.");
		}
		
		this.readNames();
	}
	
	public void setFilePath(String filePath) {
		// set different filePath variable
		this.filePath = filePath;
	}
	
	public void readNames() {
		// generate demon names based on filePath
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(this.filePath), Charset.defaultCharset())) {
			// create bufferedreader and append all read lines to List<String> demonNames
			String line = null;
			while ((line = reader.readLine()) != null) {
				this.demonNames.add(line);
			}
		} catch (IOException x) { x.printStackTrace(); }		
	}
	
	public String getDemonName() {
		// generates a random demon name from the file with demon names
		try {
			int randomIndex = Randomizer.random(this.demonNames.size());
			return demonNames.get(randomIndex).trim();
		} catch (IOException x) {
			x.printStackTrace();
			return "Unknown";
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TEST NameGenerator
		
		NameGenerator n = new NameGenerator();
		System.out.println("Amount of names: " + n.demonNames.size());
		System.out.println("Random name: " + n.getDemonName());
	}

}
