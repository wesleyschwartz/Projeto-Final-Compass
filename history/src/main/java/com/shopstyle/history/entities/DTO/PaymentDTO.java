package com.shopstyle.history.entities.DTO;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class PaymentDTO {
    private String type;
    private Integer discount;
    private Boolean status;

}
