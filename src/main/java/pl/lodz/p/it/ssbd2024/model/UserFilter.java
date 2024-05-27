package pl.lodz.p.it.ssbd2024.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "user_filters")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean blocked = null;
    private Boolean verified = null;
    private String role;

    @ManyToOne(optional = false)
    private User user;
}
