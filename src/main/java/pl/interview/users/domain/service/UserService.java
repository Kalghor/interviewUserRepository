package pl.interview.users.domain.service;

import org.springframework.stereotype.Service;
import pl.interview.users.domain.interfaces.IUserRepository;
import pl.interview.users.domain.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {
    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;;
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


    public void loadDataToDatabase(Path path) throws FileNotFoundException {
        int numberOfLine = 0;
        File file = new File(String.valueOf(path));
        Scanner scan = new Scanner(file);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found: " + path);
        }
        while (scan.hasNext()) {
            numberOfLine++;
            String line = scan.nextLine();
            String[] userArr = line.split(";");
            if (userArr.length < 2 || line.equals("first_name;last_name;birth_date;phone_no")) {
                continue;
            }
            //userArr[0] - firstName
            //userArr[1] - lastname
            //userArr[2] - dateOfBirth
            //userArr[3] - phoneNumber
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.M.d");
            String date = userArr[2];
            LocalDate dateOfBirth = LocalDate.parse(date, formatter);
            userArr[0] = userArr[0].trim();
            userArr[1] = userArr[1].trim();
            if (validateUserName(userArr)) {
                User user = new User();
                if (userArr.length == 3 || userArr[3].equals("-")) {
                    user = new User(null, userArr[0], userArr[1], dateOfBirth, 0);
                } else if (userArr.length == 4 && validateUserNumber(userArr)) {
                    user = new User(null, userArr[0].trim(), userArr[1].trim(), dateOfBirth, Integer.parseInt(userArr[3]));
                }
                if (!isPhoneNumberExist(user.getPhoneNumber()) || user.getPhoneNumber() == 0) {
                    addUser(user);
                } else {
                    System.out.println("User with the given phone number already exists in the database. Incorrect data: " + user);
                }
            } else {
                System.out.println("Incorrect data on line " + numberOfLine + " of the file.");
            }
        }
    }

    private boolean validateUserName(String[] userData) {
        Boolean result = false;
        Pattern nameValid = Pattern.compile("[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{3,}");

        Matcher firstNameMatcher = nameValid.matcher(userData[0]);
        Matcher lastNameMatcher = nameValid.matcher(userData[1]);

        if (firstNameMatcher.matches() && lastNameMatcher.matches()) {
            result = true;
        }
        return result;
    }

    private boolean validateUserNumber(String[] userData) {
        Boolean result = false;
        Pattern phoneNumberValid = Pattern.compile("\\d{9}");

        Matcher phoneNumberMatcher = phoneNumberValid.matcher(userData[3]);

        if (phoneNumberMatcher.matches()) {
            result = true;
        }
        return result;
    }

    private boolean isPhoneNumberExist(int phoneNumber) {
        boolean result = false;
        Iterable<User> users = userRepository.findAll();

        for (User u : users) {
            if (u.getPhoneNumber() == phoneNumber) {
                result = true;
            }
        }
        return result;
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

    public User oldestUserWithPhoneNumber() {
        Iterable<User> all = userRepository.findAll();
        List<User> users = new ArrayList<>();
        all.forEach(users::add);
        return users.stream()
                .filter(u -> u.getPhoneNumber() != 0)
                .min(Comparator.comparing(User::getBirthDate)).get();
    }

    public List<User> showByLastName(String lastName) {
        Iterable<User> all = userRepository.findAll();
        List<User> users = new ArrayList<>();
        all.forEach(users::add);
        List<User> userList = users.stream().filter(u -> u.getLastName().equalsIgnoreCase(lastName)).collect(Collectors.toList());
        if (!userList.isEmpty()) {
            return userList;
        } else {
            throw new NoSuchElementException("User not found");
        }
    }

    public void removeUser(Long index) {
        if (index < 0 || index >= userRepository.count()) {
            throw new IndexOutOfBoundsException("Index Out Of Bounds");
        }
        userRepository.deleteById(index);
    }
}