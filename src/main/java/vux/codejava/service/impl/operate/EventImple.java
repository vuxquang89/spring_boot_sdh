package vux.codejava.service.impl.operate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.operate.Event;
import vux.codejava.repository.operate.EventRepository;
import vux.codejava.service.operate.EventServices;

@Service
public class EventImple implements EventServices{

	@Autowired
	private EventRepository repo;
	
	@Override
	public List<Event> findAll() {
		return repo.findAll();
	}
}
