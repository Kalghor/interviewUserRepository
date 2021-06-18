package pl.interview.users.domain.service;

import org.springframework.stereotype.Service;
import pl.interview.users.domain.interfaces.IUserService;
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
public class UserService implements IUserService {
    private List<User> users;

    public UserService(List<User> users) {
        this.users = users;
    }

    private static Long nextId = 1L;

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void addUser(User user) {
        user.setId(nextId++);
        users.add(user);
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

        for (User u : users) {
            if (u.getPhoneNumber() == phoneNumber) {
                result = true;
            }
        }
        return result;
    }

    public int count() {
        return users.size();
    }

    public List<User> listByAge() {
        return users.stream().sorted(Comparator.comparing(User::getBirthDate)).collect(Collectors.toList());
    }

    public void removeAll() {
        users = new ArrayList<>();
    }

    public User oldestUserWithPhoneNumber() {
        return users.stream()
                .filter(u -> u.getPhoneNumber() != 0)
                .min(Comparator.comparing(User::getBirthDate)).get();
    }

    public User showByLastName(String lastName) {
        Optional<User> any = users.stream().filter(u -> u.getLastName().equalsIgnoreCase(lastName)).findAny();
        if (any.isPresent()) {
            return any.get();
        } else {
            throw new NoSuchElementException("User not found");
        }
    }

    public void removeUser(int index) {
        if (index < 0 || index >= users.size()) {
            throw new IndexOutOfBoundsException("Index Out Of Bounds");
        }
        users.remove(index - 1);
    }
}