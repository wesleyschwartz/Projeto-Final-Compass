package com.shopstyle.checkout.model.DTO;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Data
@Getter
public class PaymentDTO {
    private Long payment_id;
    @NotNull
    private String type;
    @NotNull
    private Integer discount;
    @NotNull
    private Boolean status;

}
