package ba.sum.fpmoz.event.repository;

import ba.sum.fpmoz.event.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
