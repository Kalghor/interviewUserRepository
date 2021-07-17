package pl.interview.users.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.interview.users.domain.model.User;
import pl.interview.users.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/load/{fileName}")
    public void loadUsers(@PathVariable String fileName, HttpServletResponse response, HttpServletRequest request) {
        Path path = Paths.get(fileName + ".csv");
        try {
            log.debug("Loading data from file: " + path);
            userService.loadDataToDatabase(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/getUsers")
    @ResponseBody
    public List<User> getUsers() {
        log.debug("Returning a list of users");
        return userService.getUsers();
    }

    @GetMapping("/countUsers")
    @ResponseBody
    public Long countUsers() {
        Long count = userService.count();
        log.debug("Number of users in the database: " + count);
        return count;
    }

    @GetMapping("/byAge")
    @ResponseBody
    public List<User> usersByAge() {
        log.debug("Returning a list of users ordered by age");
        return userService.listByAge();
    }

    @GetMapping("/removeAll")
    @ResponseBody
    public void removeAll() {
        log.debug("Number of users in the database: " + userService.count());
        log.debug("Removing all data from database");
        userService.removeAll();
        log.debug("Number of users in the database: " + userService.count());
    }

    @GetMapping("/remove/{index:\\d+}")
    @ResponseBody
    public void remove(@PathVariable int index) {
        log.debug("Number of users in the database: " + userService.count());
        log.debug("Removing one user from database");
        userService.removeUser((long) index);
        log.debug("Number of users in the database: " + userService.count());
    }

    @GetMapping("/oldestUserWithPhoneNumber")
    @ResponseBody
    public User findFirstByOrderByBirthDateAsc() {
        User user = userService.findFirstByOrderByBirthDateAsc();
        log.debug("Returning oldest user with phone number: " + user);
        return user;
    }

    @GetMapping("/showByLastName/{lastName}")
    @ResponseBody
    public User showByLastName(@PathVariable String lastName) {
        return userService.showByLastName(lastName);
    }
}
