package vux.codejava.lib;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import vux.codejava.exception.BaseException;

public class Thumbnail {
	
	public static final int SIZE_WIDTH_100 = 100;
	public static final int SIZE_HEIGHT_100 = 100;

	public static String resize_100(String pathName, String fileName) throws BaseException {
		String path = pathName+"/"+fileName;
		File file = new File(path);
		
		if(!file.isFile()) {
			throw new BaseException("upload file is null");
		}
		String thumbnailFileName = fileName.replace(".", "_"+SIZE_WIDTH_100+"x"+SIZE_HEIGHT_100+".");
		System.out.println(thumbnailFileName);
		try {
						
			//Thumbnails.of(new File("path/to/directory").listFiles())
			Thumbnails.of(path)
			.size(SIZE_WIDTH_100, SIZE_HEIGHT_100)
			.toFile(pathName+"/"+thumbnailFileName);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		return thumbnailFileName;
	}
}
