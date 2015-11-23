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

	private String filePath = "src/demon_names.txt";
	private List<String> demonNames = new ArrayList<>();
	
	public NameGenerator() {
		readNames();
	}
	
	public NameGenerator(String filePath) throws IOException {
		// overloaded constructor to change filePath to different file
		
		if (Files.exists(Paths.get(filePath))) {
			setFilePath(filePath);
		} else {
			throw new IOException("NameGenerator: FilePath does not exist.");
		}
		
		readNames();
	}
	
	public String getDemonName() {
		// generates a random demon name from the file with demon names
		int randomIndex = Randomizer.random(demonNames.size());
		return demonNames.get(randomIndex).trim();
	}
	
	private void setFilePath(String filePath) {
		// set different filePath variable
		this.filePath = filePath;
	}
	
	private void readNames() {
		// generate demon names based on filePath
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), Charset.defaultCharset())) {
			// create bufferedreader and append all read lines to List<String> demonNames
			String line = null;
			while ((line = reader.readLine()) != null) {
				demonNames.add(line);
			}
		} catch (IOException x) { x.printStackTrace(); }		
	}
	
	public static void main(String[] args) throws IOException {
		// TEST NameGenerator
		
		NameGenerator n = new NameGenerator();
		System.out.println("Amount of names: " + n.demonNames.size());
		System.out.println("Random name: " + n.getDemonName());
	}

}
