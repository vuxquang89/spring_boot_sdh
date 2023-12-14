package vux.codejava.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.shift.ShiftDetailEntity;
import vux.codejava.response.ShiftDetailRepository;
import vux.codejava.service.ShiftDetailServices;

@Service
public class ShiftDetailServiceImple implements ShiftDetailServices{

	@Autowired
	private ShiftDetailRepository shiftDetailRepo;
	
	@Override
	public List<ShiftDetailEntity> saveAll(List<ShiftDetailEntity> shiftDetails) {
		return shiftDetailRepo.saveAll(shiftDetails);
	}

}
