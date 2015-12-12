package randomizers;

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
	private List<String> names = new ArrayList<>();
	
	public NameGenerator(String type) {
		if (type.equals("angelic")) {
			setFilePath("src/angelic_names.txt");
		}
		readNames();
	}
	
	public NameGenerator(String type, String filePath) throws IOException {
		// overloaded constructor to change filePath to different file
		
		if (Files.exists(Paths.get(filePath))) {
			setFilePath(filePath);
		} else {
			throw new IOException("NameGenerator: FilePath does not exist.");
		}
		
		readNames();
	}
	
	public String getName() {
		// generates a random demon name from the file with demon names
		int randomIndex = Randomizer.random(names.size());
		return names.get(randomIndex).trim();
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
				names.add(line);
			}
		} catch (IOException x) { x.printStackTrace(); }		
	}
	
	public static void main(String[] args) throws IOException {
		// TEST NameGenerator
		
		NameGenerator n = new NameGenerator("demonic");
		System.out.println("Amount of names: " + n.names.size());
		System.out.println("Random name: " + n.getName());
	}

}
