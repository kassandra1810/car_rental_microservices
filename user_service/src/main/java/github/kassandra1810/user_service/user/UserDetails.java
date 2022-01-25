package github.kassandra1810.user_service.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private UserType userType;

    @NonNull
    private LocalDate dateOfBirth;
}
