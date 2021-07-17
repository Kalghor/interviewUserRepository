package pl.interview.users.domain.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UserBean extends CsvBean{

    @CsvBindByName(column = "first_name")
    private String firstName;

    @CsvBindByName(column = "last_name")
    private String lastName;

    @CsvBindByName(column = "birth_date")
    private String birthDate;

    @CsvBindByName(column = "phone_no")
    private int phoneNumber;
}
