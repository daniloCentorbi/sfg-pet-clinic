package guru.springframework.sfgpetclinic.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate date;

    @Column(name = "description")
    @Size(min = 3, max = 255)
    private String description;

    @ManyToOne
    @JoinColumn( name = "pet_id")
    private Pet pet;

}
