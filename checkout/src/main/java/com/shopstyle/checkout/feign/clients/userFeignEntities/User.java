package com.shopstyle.checkout.feign.clients.userFeignEntities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long user_id;
    private String firstName;
    private String lastName;
    private String sex;
    private String cpf;
    private String birthdate;
    private String email;
    private String password;
    private Boolean active;
}
