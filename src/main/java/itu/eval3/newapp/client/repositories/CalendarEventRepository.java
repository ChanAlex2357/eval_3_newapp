package itu.eval3.newapp.client.repositories;
import itu.eval3.newapp.client.models.calendar.CEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarEventRepository extends JpaRepository<CEvent, Long> {
    // Custom queries if needed
}
    