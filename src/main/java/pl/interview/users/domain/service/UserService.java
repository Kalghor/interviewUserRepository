package pl.interview.users.domain.service;

import org.springframework.stereotype.Service;
import pl.interview.users.domain.interfaces.IUserRepository;
import pl.interview.users.domain.model.User;
import pl.interview.users.domain.model.UserBean;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class UserService {
    private IUserRepository userRepository;
    private CsvToBeanService csvToBeanService;

    public UserService(IUserRepository userRepository, CsvToBeanService csvToBeanService) {
        this.userRepository = userRepository;
        this.csvToBeanService = csvToBeanService;
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
        System.out.println(userBeans.toString());
    }



//

//
//    public Long count() {
//        return userRepository.count();
//    }
//
//    public List<User> listByAge() {
//        Iterable<User> all = userRepository.findAll();
//        List<User> users = new ArrayList<>();
//        all.forEach(users::add);
//        return users.stream().sorted(Comparator.comparing(User::getBirthDate)).collect(Collectors.toList());
//    }
//
//    public void removeAll() {
//        userRepository.deleteAll();
//    }
//
//    public User oldestUserWithPhoneNumber() {
//        Iterable<User> all = userRepository.findAll();
//        List<User> users = new ArrayList<>();
//        all.forEach(users::add);
//        return users.stream()
//                .filter(u -> u.getPhoneNumber() != 0)
//                .min(Comparator.comparing(User::getBirthDate)).get();
//    }
//
//    public List<User> showByLastName(String lastName) {
//        Iterable<User> all = userRepository.findAll();
//        List<User> users = new ArrayList<>();
//        all.forEach(users::add);
//        List<User> userList = users.stream().filter(u -> u.getLastName().equalsIgnoreCase(lastName)).collect(Collectors.toList());
//        if (!userList.isEmpty()) {
//            return userList;
//        } else {
//            throw new NoSuchElementException("User not found");
//        }
//    }
//
//    public void removeUser(Long index) {
//        if (index < 0 || index >= userRepository.count()) {
//            throw new IndexOutOfBoundsException("Index Out Of Bounds");
//        }
//        userRepository.deleteById(index);
//    }
}