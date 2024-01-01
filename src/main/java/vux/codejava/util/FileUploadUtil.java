package vux.codejava.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	@Value("${upload.path}")
	private static String UPLOADED_FOLDER;
	
	public static String saveFile(String optFolder, String fileName, MultipartFile multipartFile) throws IOException {
//		String uploadDir = "upload/"+optFolder;
		String uploadDir = UPLOADED_FOLDER + optFolder;
		Path uploadPath = Paths.get(uploadDir);
		//System.out.println(uploadPath.toString());
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		String fileCode = RandomStringUtils.randomAlphanumeric(8);
		String fiName = fileCode + "-" + fileName;
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fiName);
			System.out.println(filePath.toString());
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save file: " + fileName, ioe);
		}
		return fiName;
	}
}
