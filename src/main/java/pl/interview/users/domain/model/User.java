package pl.interview.users.domain.model;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate BirthDate;
    private int phoneNumber;

    public User(Long id, String s, String s1, LocalDate dateOfBirth) {
    }
}
