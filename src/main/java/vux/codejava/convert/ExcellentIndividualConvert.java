package vux.codejava.convert;

import org.springframework.stereotype.Component;

import vux.codejava.entity.ExcellentIndividualEntity;
import vux.codejava.response.ExcellentIndividualResponse;

@Component
public class ExcellentIndividualConvert {
	
	public ExcellentIndividualEntity entity(ExcellentIndividualResponse response) {
		ExcellentIndividualEntity entity = new ExcellentIndividualEntity();
		entity.setId(response.getId());
		entity.setFullName(response.getFullName());
		entity.setContent(response.getContent());
		//entity.setImageAvatar(response.getImageAvatar());
		return entity;
	}
	
	public ExcellentIndividualResponse response(ExcellentIndividualEntity entity) {
		ExcellentIndividualResponse response = new ExcellentIndividualResponse();
		response.setId(entity.getId());
		response.setFullName(entity.getFullName());
		response.setContent(entity.getContent());
		response.setImageAvatar(entity.getImageAvatar());
		response.setImageAvatarResize(entity.getImageAvatarResize());
		return response;
	}
}
