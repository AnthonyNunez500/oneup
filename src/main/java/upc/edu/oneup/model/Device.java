package upc.edu.oneup.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cart_items")
public class Device {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_quantity", length = 50)
    private int productQuantity;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "z", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Patient patient;
}
