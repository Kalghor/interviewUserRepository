package pl.interview.users.service;

import org.springframework.stereotype.Service;
import pl.interview.users.converter.UserConverter;
import pl.interview.users.domain.interfaces.IUserRepository;
import pl.interview.users.domain.model.User;
import pl.interview.users.domain.model.UserBean;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final CsvToBeanService csvToBeanService;
    private final UserConverter userConverter;

    public UserService(IUserRepository userRepository, CsvToBeanService csvToBeanService, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.csvToBeanService = csvToBeanService;
        this.userConverter = userConverter;
    }

    public List<User> getUsers() {
        Iterable<User> all = userRepository.findAll();
        List<User> users = new ArrayList<>();
        all.forEach(users::add);
        return users;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }


    public void loadDataToDatabase(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found !!!");
        }
        List<UserBean> userBeans = csvToBeanService.readCsvFile(path);
        List<User> users = userBeans.stream()
                .map(userBean -> userConverter.userBeanToUser(userBean))
                .collect(Collectors.toList());

        for(User u : users){
            addUser(u);
        }
    }

    public Long count() {
        return userRepository.count();
    }

    public List<User> listByAge() {
        Iterable<User> all = userRepository.findAll();
        List<User> users = new ArrayList<>();
        all.forEach(users::add);
        return users.stream().sorted(Comparator.comparing(User::getBirthDate)).collect(Collectors.toList());
    }

    public void removeAll() {
        userRepository.deleteAll();
    }

    public User findFirstByOrderByBirthDateAsc() {
        return userRepository.findFirstByOrderByBirthDateAsc();
    }

    public User showByLastName(String lastName) {
        return userRepository.findUserByLastName(lastName);
    }

    public void removeUser(Long index) {
        if (index < 0 || index >= userRepository.count()) {
            throw new IndexOutOfBoundsException("Index Out Of Bounds");
        }
        userRepository.deleteById(index);
    }
}