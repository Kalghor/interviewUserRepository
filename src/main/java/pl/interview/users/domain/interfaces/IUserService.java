package pl.interview.users.domain.interfaces;

import pl.interview.users.domain.model.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers();
    void addUser(User user);
    void removeUser(int id);
}
