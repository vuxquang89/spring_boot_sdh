package vux.codejava.entity;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFiles {

	private MultipartFile[] files;

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	
	
}
