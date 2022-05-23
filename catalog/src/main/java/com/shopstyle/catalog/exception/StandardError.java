package com.shopstyle.catalog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StandardError {
    private Integer status;
    private String error;
}
