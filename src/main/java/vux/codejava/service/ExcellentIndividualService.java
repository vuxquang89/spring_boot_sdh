package vux.codejava.service;

import java.util.List;
import java.util.Optional;

import vux.codejava.entity.ExcellentIndividualEntity;
import vux.codejava.response.ExcellentIndividualResponse;

public interface ExcellentIndividualService {

	public List<ExcellentIndividualResponse> findAll();
	
	public ExcellentIndividualResponse saveEntity(ExcellentIndividualEntity entity);
	
	public Optional<ExcellentIndividualEntity> findById(Integer id);
	
	public boolean delete(Integer id);
}
