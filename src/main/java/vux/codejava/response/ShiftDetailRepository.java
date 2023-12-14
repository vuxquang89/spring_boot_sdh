package vux.codejava.response;

import org.springframework.data.jpa.repository.JpaRepository;

import vux.codejava.entity.shift.ShiftDetailEntity;

public interface ShiftDetailRepository extends JpaRepository<ShiftDetailEntity, Long> {

}
