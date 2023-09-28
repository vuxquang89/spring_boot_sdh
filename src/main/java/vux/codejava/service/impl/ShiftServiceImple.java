package vux.codejava.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.shift.ShiftEntity;
import vux.codejava.lib.Convert;
import vux.codejava.repository.ShiftRepository;
import vux.codejava.service.ShiftServices;

@Service
public class ShiftServiceImple implements ShiftServices{
	
	@Autowired
	private ShiftRepository shiftRepository;

	@Override
	public ShiftEntity findByKeyCode(String keyCode) {
		return shiftRepository.findByKeyCode(keyCode).get();
	}

	@Override
	public ShiftEntity save(ShiftEntity shiftEntity) {
		return shiftRepository.save(shiftEntity);
	}

	@Override
	public List<ShiftEntity> listAll() {
		return shiftRepository.findAll();
	}

	@Override
	public List<ShiftEntity> listByDateReceive(String month) {
		String[] months = Convert.convertStringToMonthYear(month);
		return shiftRepository.findByDateReceive(months[1], months[0]);
	}
	
	

}
