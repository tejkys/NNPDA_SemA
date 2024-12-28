package com.st61019.SemA.dao.entities;

import com.st61019.SemA.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String firstName;
    private String lastName;
    private int id;
    private String email;
    public UserDTO(User user){
        firstName = user.getFirstName();
        lastName = user.getLastName();
        id = user.getId();
        email = user.getEmail();
    }
}
