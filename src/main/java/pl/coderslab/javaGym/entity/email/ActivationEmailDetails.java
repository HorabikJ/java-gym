package pl.coderslab.javaGym.entity.email;

import lombok.*;
import pl.coderslab.javaGym.entity.user.User;

import javax.persistence.*;
import java.time.ZonedDateTime;


@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class ActivationEmailDetails implements ConfirmationEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @OneToOne
    private User user;

    @NonNull
    @Column
    private String param;

    @NonNull
    @Column
    private ZonedDateTime sendTime;

    @NonNull
    @Column
    private Integer minutesExpirationTime;

}
