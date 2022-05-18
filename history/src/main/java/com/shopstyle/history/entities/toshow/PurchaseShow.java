package com.shopstyle.history.entities.toshow;

import com.shopstyle.history.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseShow {
    private Payment paymentMethod;
    private List<ProductShow> products;
    private Double total;
    private String date;
}
