package vux.codejava.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.util.GoogleApiUtil;

@Service
public class GoogleApiService {
	
	@Autowired
	private GoogleApiUtil googleApiUtil;

	public List<String> readDataFromGoogleSheet(String nameSheet) throws GeneralSecurityException, IOException {
		return googleApiUtil.getDataFromSheet(nameSheet);
	}

}
