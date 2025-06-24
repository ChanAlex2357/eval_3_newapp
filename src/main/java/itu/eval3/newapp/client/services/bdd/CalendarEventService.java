package itu.eval3.newapp.client.services.bdd;
import itu.eval3.newapp.client.models.calendar.CEvent;
import itu.eval3.newapp.client.repositories.CalendarEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalendarEventService {

    private final CalendarEventRepository repository;

    public CalendarEventService(CalendarEventRepository repository) {
        this.repository = repository;
    }

    public List<CEvent> findAll() {
        return repository.findAll();
    }

    public Optional<CEvent> findById(Long id) {
        return repository.findById(id);
    }

    public CEvent save(CEvent entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
