package pl.interview.users.domain.interfaces;

import org.springframework.data.repository.CrudRepository;
import pl.interview.users.domain.model.User;

public interface IUserRepository extends CrudRepository<User, Long> {
}
