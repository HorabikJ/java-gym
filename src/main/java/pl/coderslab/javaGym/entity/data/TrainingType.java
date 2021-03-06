package pl.coderslab.javaGym.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(length = 1000)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "trainingType", cascade = CascadeType.REMOVE)
    private List<TrainingClass> trainingClasses = new ArrayList<>();

}
