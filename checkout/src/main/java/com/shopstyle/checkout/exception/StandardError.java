package com.shopstyle.checkout.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StandardError {
    private Integer status;
    private String error;
}
