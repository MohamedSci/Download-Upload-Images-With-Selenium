package merge_json_files;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MergeJSONFiles {

	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, JsonNode>> allMapResources = new ArrayList<>();

		try {
			// List all JSON files in a directory
			File folder = new File("C:\\Users\\moham\\OneDrive\\Desktop\\zekr");
			File[] listOfFiles = folder.listFiles();
			if (listOfFiles != null) {
				for (File file : listOfFiles) {
					if (file.isFile() && file.getName().endsWith(".json")) {
						// Read and parse JSON file
						JsonNode rootNode = mapper.readTree(file);
						// Extract map resources from JSON object
						String fileNameWithoutExtension = file.getName().replaceFirst("[.][^.]+$", "");
						AppendTextToFile.AppendTextToFileFun(fileNameWithoutExtension);;
						JsonNode mapResources = rootNode.get(fileNameWithoutExtension);
						System.out.println("---mapResources: " + mapResources);
						if (mapResources != null && mapResources.isArray()) {
							System.out.println("---Not Null mapResources: " + mapResources);
							for (JsonNode resource : mapResources) {
								Map<String, JsonNode> modifiedResources = new HashMap<>();
								modifiedResources.put(fileNameWithoutExtension, resource);
								System.out.println("---resource: " + resource);
								allMapResources.add(modifiedResources);
							}
							System.out.println("---allMapResources.size(): " + allMapResources.size());
						}
					}
				}
			}

			// Convert list of map resources into a JSON array
			JsonNode combinedMapResources = mapper.valueToTree(allMapResources);

			// Write combined JSON array to a new file
			mapper.writeValue(
					new File(
							"C:\\Users\\moham\\OneDrive\\Desktop\\zekr\\single_zekr_dataset\\single_zekr_dataset.json"),
					combinedMapResources);

			System.out.println("Merged JSON files successfully.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
