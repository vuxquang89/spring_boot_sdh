$(document).ready(function(){
	//$('.pass_show').append('<span class="ptxt">Show</span>');  
	
	$('#btnChangePassword').click(function(){
		if(form_validate()){
			$('#formChangePassword').submit();
		}
	})
});
  

$(document).on('click','.pass_show .ptxt', function(){ 

	$(this).text($(this).text() == "Show" ? "Hide" : "Show"); 

	$(this).prev().attr('type', function(index, attr){return attr == 'password' ? 'text' : 'password'; }); 

}); 

function form_validate(){
	var currentPassword = $('#currentPassword');
		newPassword = $('#newPassword');
		confirmPassword = $('#confirmPassword');
	var checkForm = true;
	if(currentPassword.val().trim() == null || currentPassword.val().trim() == ""){
		errorAction(currentPassword, "Chưa nhập mật khẩu", false);
		checkForm = false;
	}else{
		errorAction(currentPassword, "", true);
	}
	
	if(newPassword.val().trim().length < 6 || newPassword.val().trim() == ""){
		errorAction(newPassword, "Mật khẩu mới cần lớn hơn 6 ký tự", false);
		checkForm = false;
	}else if(currentPassword.val().trim() == newPassword.val().trim()){
		errorAction(newPassword, "Mật khẩu mới trùng mật khẩu cũ", false);
		checkForm = false;
	}else{
		errorAction(newPassword, "", true);
	}
	
	if(confirmPassword.val().trim() == null || confirmPassword.val().trim() == ""){
		errorAction(confirmPassword, "Nhập lại mật khẩu mới", false);
		checkForm = false;
	}else if(newPassword.val().trim() !== confirmPassword.val().trim()){
		errorAction(confirmPassword, "Nhập lại mật khẩu không đúng", false);
		checkForm = false;
	}else{
		errorAction(confirmPassword, "", true);
	}
	
	
	return checkForm;
}