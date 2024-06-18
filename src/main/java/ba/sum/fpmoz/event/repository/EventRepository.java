package ba.sum.fpmoz.event.repository;

import ba.sum.fpmoz.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
