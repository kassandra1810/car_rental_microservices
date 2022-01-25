package github.kassandra1810.user_service.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class User extends UserDetails {

    @ApiModelProperty("unique id of user")
    private String id;

    public User (final String id, UserDetails userDetails) {
        this.id = id;
        this.setFirstName(userDetails.getFirstName());
        this.setLastName(userDetails.getLastName());
        this.setUserType(userDetails.getUserType());
        this.setDateOfBirth(userDetails.getDateOfBirth());
    }

    public User (final String id, String firstName, String lastName, UserType userType, LocalDate dateOfBirth) {
        this.id = id;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setUserType(userType);
        this.setDateOfBirth(dateOfBirth);
    }

}
