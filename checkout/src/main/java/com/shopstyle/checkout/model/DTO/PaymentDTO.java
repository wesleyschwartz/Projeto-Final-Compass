package com.shopstyle.checkout.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    public PaymentDTO(String type, Integer discount, Boolean status) {
        this.type = type;
        this.discount = discount;
        this.status = status;
    }

    private Long payment_id;
    @NotNull
    private String type;
    @NotNull
    private Integer discount;
    @NotNull
    private Boolean status;

}
