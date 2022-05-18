package com.shopstyle.history.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HistoryDTO {
    private UserDTO userDTO;
    private PurchaseDTO purchaseDTO;
    private List<ProductDTO> productDTOS;

}
