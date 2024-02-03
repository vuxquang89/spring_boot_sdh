package vux.codejava.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import vux.codejava.convert.ExcellentIndividualConvert;
import vux.codejava.entity.ExcellentIndividualEntity;
import vux.codejava.entity.User;
import vux.codejava.exception.BaseException;
import vux.codejava.lib.Thumbnail;
import vux.codejava.request.ExcellentIndividualRequest;
import vux.codejava.response.ExcellentIndividualResponse;
import vux.codejava.response.ExcellentIndividualResponseEntity;
import vux.codejava.response.UserResponse;
import vux.codejava.service.ExcellentIndividualService;
import vux.codejava.service.UserServices;
import vux.codejava.util.FileUploadUtil;
import vux.codejava.util.GoogleApiUtil;

@RestController
@RequestMapping("/api")
public class AdminRestController {
	
	@Autowired
	private GoogleApiUtil googleApi;
	@Autowired
	private UserServices userServices;
	@Autowired
	private ExcellentIndividualConvert excellentIndividualConvert;
	@Autowired
	private ExcellentIndividualService excellentIndividualService;
	
	private final String OPT_FOLDER = "images";
	private final String UPLOAD_FOLDER = "upload";

	@GetMapping("/port")
	public ResponseEntity<?> getPort() throws GeneralSecurityException, IOException{
		
		int port = findOpenPort();
		
		return ResponseEntity.ok("port : " + port);
	}
	
	@GetMapping("/url")
	public ResponseEntity<?> getUrl() throws GeneralSecurityException, IOException{
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		
		return ResponseEntity.ok("url : " + googleApi.getURL(HTTP_TRANSPORT));
	}
	
	private int findOpenPort() {
	    try (ServerSocket socket = new ServerSocket(0)) {
	      socket.setReuseAddress(true);
	      return socket.getLocalPort();
	    } catch (IOException e) {
	      throw new IllegalStateException("No free TCP/IP port to start embedded HTTP Server on");
	    }
	}
	
	@GetMapping("/admin/users")
	public ResponseEntity<?> getUsers(){
		List<UserResponse> responses = userServices.findUser();
		return ResponseEntity.ok(responses);
	}
	
	@GetMapping("/admin/excellentindividual")
	public ResponseEntity<?> getItemUser(){		
		List<ExcellentIndividualResponse> responses = excellentIndividualService.findAll();		
		return ResponseEntity.ok(responses);
	}
	
	@DeleteMapping("/admin/excellentindividual/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable("id") Integer id){
		
		if(excellentIndividualService.delete(id)) {
			return ResponseEntity.ok("OK");
		}	
		
		return ResponseEntity.ok("NO");
	}
	
	@GetMapping("/admin/excellentindividual/{id}")
	public ResponseEntity<ExcellentIndividualResponseEntity> getItem(@PathVariable("id") Integer id){
		ExcellentIndividualEntity entity = excellentIndividualService.findById(id).orElse(null);
		ExcellentIndividualResponseEntity response = new ExcellentIndividualResponseEntity();
		response.setStatus(200);
		if(entity != null) {
			response.setContent(excellentIndividualConvert.response(entity));
		}else {
			response.setStatus(400);
			response.setMessage("Không tìm thấy");
		}
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/admin/excellentindividual")
	public ResponseEntity<ExcellentIndividualResponseEntity> saveItemUser(@ModelAttribute ExcellentIndividualRequest excellentIndividualRequest){
		Long userId = excellentIndividualRequest.getUserId();
		
		User user = userServices.findByUserId(userId).orElse(null);
		ExcellentIndividualResponseEntity responseEntity = new ExcellentIndividualResponseEntity();
		
		if(user != null) {
			ExcellentIndividualEntity excellentIndividualEntity = new ExcellentIndividualEntity();
			excellentIndividualEntity.setFullName(user.getFullName());
			excellentIndividualEntity.setContent(excellentIndividualRequest.getContent());
			
			if(excellentIndividualRequest.getImageAvatar() != null && !excellentIndividualRequest.getImageAvatar().isEmpty()) {
				String path = UPLOAD_FOLDER+"/"+OPT_FOLDER + "/sdh/ExcellentIndividual";
				try {
					String fileName = saveUploadedFile(excellentIndividualRequest.getImageAvatar(), path);
					String fileNameResize = "";
					
					try {
						fileNameResize = Thumbnail.resize_100(path, fileName);
						excellentIndividualEntity.setImageAvatarResize(path+"/"+fileNameResize);
						
					} catch (BaseException e) {	
						responseEntity.setStatus(400);
						responseEntity.setMessage("Thêm mới không thành công");
						responseEntity.setError("Lỗi thêm mới hình ảnh resize");
						e.printStackTrace();
					}
					excellentIndividualEntity.setImageAvatar(path+"/"+fileName);
					responseEntity.setContent(excellentIndividualService.saveEntity(excellentIndividualEntity));	
					responseEntity.setStatus(200);
					responseEntity.setMessage("Thêm mới thành công");
				} catch (IOException e) {
					responseEntity.setStatus(400);
					responseEntity.setMessage("Thêm mới không thành công");
					responseEntity.setError("Lỗi thêm mới hình ảnh");
					e.printStackTrace();
				}
				
			}else {
				responseEntity.setStatus(401);
				responseEntity.setMessage("Chưa chọn hình đại diện");
				responseEntity.setError("Lỗi thêm mới");
				return ResponseEntity.ok(responseEntity);
			}
		
		}
		
		return ResponseEntity.ok(responseEntity);
	}
	
	@PutMapping("/admin/excellentindividual")
	public ResponseEntity<ExcellentIndividualResponseEntity> updateItemUser(@ModelAttribute ExcellentIndividualRequest excellentIndividualRequest){
		
		ExcellentIndividualResponseEntity responseEntity = new ExcellentIndividualResponseEntity();
		ExcellentIndividualEntity excellentIndividualEntity = excellentIndividualService.findById(excellentIndividualRequest.getId()).orElse(null);
		if(excellentIndividualEntity != null) {
			
			excellentIndividualEntity.setContent(excellentIndividualRequest.getContent());
			
			
				try {
					if(excellentIndividualRequest.getImageAvatar() != null && !excellentIndividualRequest.getImageAvatar().isEmpty()) {
						String path = UPLOAD_FOLDER+"/"+OPT_FOLDER + "/sdh/ExcellentIndividual";
					String fileName = saveUploadedFile(excellentIndividualRequest.getImageAvatar(), path);
					String fileNameResize = "";
					
					try {
						fileNameResize = Thumbnail.resize_100(path, fileName);
						excellentIndividualEntity.setImageAvatarResize(path+"/"+fileNameResize);
						
					} catch (BaseException e) {	
						responseEntity.setStatus(400);
						responseEntity.setMessage("Cập nhật không thành công");
						responseEntity.setError("Lỗi cập nhật hình ảnh resize");
						
						e.printStackTrace();
						return ResponseEntity.ok(responseEntity);
					}
					excellentIndividualEntity.setImageAvatar(path+"/"+fileName);
					}
				} catch (IOException e) {
					responseEntity.setStatus(400);
					responseEntity.setMessage("Cập nhật không thành công");
					responseEntity.setError("Lỗi cập nhật hình ảnh");
					e.printStackTrace();
					return ResponseEntity.ok(responseEntity);
				}
				
			responseEntity.setStatus(200);
			responseEntity.setMessage("Cập nhật thành công");
			responseEntity.setContent(excellentIndividualService.saveEntity(excellentIndividualEntity));
		}
		
		return ResponseEntity.ok(responseEntity);
	}
	
	//save file
    private String saveUploadedFile(MultipartFile file, String pathUpload) throws IOException {
    	
    	String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileName = FileUploadUtil.saveFile(pathUpload, fileName, file);
        System.out.println(fileName);
       
        return fileName;
    }
}
