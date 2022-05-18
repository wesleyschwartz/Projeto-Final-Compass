package com.shopstyle.customer.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shopstyle.customer.model.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotEmpty
    private String firstName;
    @Size(min = 3)
    @NotEmpty
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Pattern(regexp = "(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)", message = "camp invalid, this camp should be xxx.xxx.xxx-xx")
    private String cpf;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;
    @Email
    @NotNull
    @NotBlank
    private String email;
    @Size(min = 8)
    private String password;
    @NotNull
    private Boolean active;
}
