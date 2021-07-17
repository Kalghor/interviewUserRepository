package pl.interview.users.domain.interfaces;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.interview.users.domain.model.User;

import java.util.List;

public interface IUserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u.phoneNumber from User u")
    List<Long> findPhoneNumbersAllUsers();


}
