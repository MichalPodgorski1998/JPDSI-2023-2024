package org.example.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"productName", "productImage"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String productName;
    private String productDescription;
    private int productQuantity;
    private double productPrice;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String productImage;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;
}
