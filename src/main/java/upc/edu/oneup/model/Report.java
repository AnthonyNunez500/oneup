package upc.edu.oneup.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="reports")
public class Report {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "heart_rate",nullable = false, length = 50)
    private double heartRate;

    @Column(name = "temperature",nullable = false, length = 50)
    private double temperature;

    @Column(name = "pressure",nullable = false,  length = 50)
    private double pressure;

    @Column(name = "time", length = 50)
    private LocalDateTime time;
//falta obtener la fecha en ese instante

    @JsonIgnore
    @JsonBackReference
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Patient patient;
}
