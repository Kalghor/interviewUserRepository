package pl.interview.users.converter;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.interview.users.domain.model.User;
import pl.interview.users.domain.model.UserBean;

import java.time.LocalDate;

@Component
public class UserConverter {

    public User userBeanToUser(UserBean userBean){
        String[] split = userBean.getBirthDate().split("\\.");
        LocalDate dateOfBirth = LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        return new User(null, userBean.getFirstName(), userBean.getLastName(), dateOfBirth, (long) userBean.getPhoneNumber());
    }

}
