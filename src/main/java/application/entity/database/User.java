package application.entity.database;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class User extends Id {
    @NotBlank
    private String password;
    @NotBlank
    private String username;
}