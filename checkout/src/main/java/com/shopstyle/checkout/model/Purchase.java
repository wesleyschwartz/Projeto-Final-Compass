package com.shopstyle.checkout.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchase_id;
    @NotNull
    private Long user_id;
    @NotNull
    private Long payment_id;
    @NotNull
    @OneToMany
    @JoinColumn(name = "purchase_id")
    private List<Cart> cart;



}
