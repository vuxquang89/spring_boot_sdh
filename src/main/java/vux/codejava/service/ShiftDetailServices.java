package vux.codejava.service;

import java.util.List;

import vux.codejava.entity.shift.ShiftDetailEntity;

public interface ShiftDetailServices {

	List<ShiftDetailEntity> saveAll(List<ShiftDetailEntity> shiftDetails);
}
