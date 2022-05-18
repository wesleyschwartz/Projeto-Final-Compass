package com.shopstyle.history.entities;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Payment {
    private String type;
    private Integer discount;
    private Boolean status;
}
