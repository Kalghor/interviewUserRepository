package pl.interview.users.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "users")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "phone_number")
    private Long phoneNumber;


}
