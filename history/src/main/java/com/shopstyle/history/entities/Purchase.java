package com.shopstyle.history.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
public class Purchase {

    @Id
    private Long purchase_id;
    private Long user_id;
    private Payment paymentMethod;
    @DocumentReference
    private List<Product> products;
    private Double total;
    private String date;
}
