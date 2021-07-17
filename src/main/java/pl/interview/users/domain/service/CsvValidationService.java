package pl.interview.users.domain.service;

import org.springframework.stereotype.Component;
import pl.interview.users.domain.interfaces.IUserRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CsvValidationService {

    private final IUserRepository iUserRepository;
    private final String DATE_VALIDATION = "^\\d{4}\\.(0?[1-9]|1[012])\\.(0?[1-9]|[12][0-9]|3[01])$";
    private final String USERNAME_VALIDATION = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{3,}";
    private final String PHONE_NUMBER_VALIDATION = "\\d{9}";

    public CsvValidationService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public boolean validate(String[] strings) {
        List<Long> phoneNumbersAllUsers = iUserRepository.findPhoneNumbersAllUsers();
        boolean blank = strings[0].equals("");
        if (!blank) {
            if (strings.length > 4) {
                String[] tmp = strings.clone();
                strings = new String[]{tmp[0], tmp[1], tmp[2], tmp[3]};
            }
            if (validateUserName(strings[0], strings[1]) && validateDateOfBirth(strings[2]) && validateUserNumber(phoneNumbersAllUsers, strings[3])) {
                return true;
            }
        }
        return false;
    }

    private boolean validateUserName(String... userData) {
        boolean result = false;
        Pattern nameValid = Pattern.compile(USERNAME_VALIDATION);
        Matcher firstNameMatcher = nameValid.matcher(userData[0].trim());
        Matcher lastNameMatcher = nameValid.matcher(userData[1].trim());
        if (firstNameMatcher.matches() && lastNameMatcher.matches()) {
            result = true;
        }
        return result;
    }

    private boolean validateUserNumber(List<Long> phoneNumbers, String userData) {
        boolean result = false;
        Pattern phoneNumberValid = Pattern.compile(PHONE_NUMBER_VALIDATION);
        Matcher phoneNumberMatcher = phoneNumberValid.matcher(userData);
        if (phoneNumberMatcher.matches()) {
            if (isPhoneDistinct(phoneNumbers, userData)) {
                result = true;
            }
        }
        return result;
    }

    private boolean isPhoneDistinct(List<Long> phoneNumbers, String userData) {
        boolean result = true;
        if (phoneNumbers.contains(Long.valueOf(userData.trim()))) {
            result = false;
        }
        return result;
    }

    private boolean validateDateOfBirth(String userData) {
        boolean result = false;
        Pattern dateOfBirthValid = Pattern.compile(DATE_VALIDATION);
        Matcher dateOfBirthMatcher = dateOfBirthValid.matcher(userData);
        if (dateOfBirthMatcher.matches()) {
            result = true;
        }
        return result;
    }
}
