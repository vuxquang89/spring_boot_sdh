package vux.codejava.repository.operate;

import org.springframework.data.jpa.repository.JpaRepository;

import vux.codejava.entity.operate.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
