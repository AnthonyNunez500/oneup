package upc.edu.oneup.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String name;

    @Column(name = "age", nullable = false)
    @NotNull
    private int age;

    @Column(name = "direction", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String direction;

    @Column(name = "disease", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String disease;

    @JsonIgnore
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @JsonIgnore
    @JsonManagedReference
    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Device device;
}