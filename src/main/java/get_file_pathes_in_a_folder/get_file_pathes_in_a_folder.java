package get_file_pathes_in_a_folder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


	public class get_file_pathes_in_a_folder {

	    public static List<String> getAllFilesInFolder(String folderPath) {
	        List<String> filePaths = new ArrayList<>();

	        File folder = new File(folderPath);
	        if (folder.isDirectory()) {
	            File[] files = folder.listFiles();
	            if (files != null) {
	                for (File file : files) {
	                    if (file.isFile()) {
	                    	String s = file.getAbsolutePath();
	                    	String ss =s.replace("D:\\projects\\flutter\\rabehna\\", "");
	                    	String sss =ss.replace("\\", "/");
	                        filePaths.add(sss);
	                    } else if (file.isDirectory()) {
	                        filePaths.addAll(getAllFilesInFolder(file.getAbsolutePath()));
	                    }
	                }
	            }
	        }

	        return filePaths;
	    }

	    public static void main(String[] args) {
	        String folderPath = "D:\\projects\\flutter\\rabehna\\assets\\images\\culture\\books";
	        List<String> files = getAllFilesInFolder(folderPath);
	        for (String file : files) {
	            System.out.println(file);
	        }
	    }
	}


