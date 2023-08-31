package vux.codejava.util;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileAction {

	public static void delete(String pathName, String fileName) {
		Path fileToDeletePath = Paths.get(pathName + "/" + fileName);
		try {
            // Delete file or directory
            Files.delete(fileToDeletePath);
            System.out.println("File or directory deleted successfully");
        } catch (NoSuchFileException ex) {
            System.out.printf("No such file or directory: %s\n", fileToDeletePath);
        } catch (DirectoryNotEmptyException ex) {
            System.out.printf("Directory %s is not empty\n", fileToDeletePath);
        } catch (IOException ex) {
            System.out.println(ex);
        }
	}
	
}
