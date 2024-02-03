package vux.codejava.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.convert.ExcellentIndividualConvert;
import vux.codejava.entity.ExcellentIndividualEntity;
import vux.codejava.repository.ExcellentIndividualRepository;
import vux.codejava.response.ExcellentIndividualResponse;
import vux.codejava.service.ExcellentIndividualService;

@Service
public class ExcellentIndividualServiceImpl implements ExcellentIndividualService {
	@Autowired
	private ExcellentIndividualConvert convert;
	@Autowired
	private ExcellentIndividualRepository excellentIndividualRepository;
	
	@Override
	public List<ExcellentIndividualResponse> findAll() {
		List<ExcellentIndividualEntity> entities = excellentIndividualRepository.findAll();
		List<ExcellentIndividualResponse> responses = new ArrayList<>();
		for(ExcellentIndividualEntity entity : entities) {
			responses.add(convert.response(entity));
		}
		return responses;
	}

	@Override
	public ExcellentIndividualResponse saveEntity(ExcellentIndividualEntity entity) {
		ExcellentIndividualEntity entitySave = excellentIndividualRepository.save(entity);
		return convert.response(entitySave);
	}

	@Override
	public Optional<ExcellentIndividualEntity> findById(Integer id) {
		return excellentIndividualRepository.findById(id);
	}

	@Override
	public boolean delete(Integer id) {
		ExcellentIndividualEntity entity = excellentIndividualRepository.findById(id).orElse(null);
		if(entity != null) {
			excellentIndividualRepository.delete(entity);
			return true;
		}
		return false; 
	}

}
