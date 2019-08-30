package guru.springframework.sfgpetclinic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vets")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vet extends Person {

    @ManyToMany
    @JoinTable(name = "vets_specialities",
            joinColumns = @JoinColumn(name = "vet_id") ,
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private Set<Speciality> specialities = new HashSet<>();

}
