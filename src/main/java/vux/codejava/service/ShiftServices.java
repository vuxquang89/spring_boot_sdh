package vux.codejava.service;

import java.util.List;

import vux.codejava.entity.shift.ShiftEntity;

public interface ShiftServices {

	ShiftEntity findByKeyCode(String keyCode);
	ShiftEntity save(ShiftEntity shiftEntity);
	List<ShiftEntity> listAll();
	List<ShiftEntity> listByDateReceive(String month);
}
