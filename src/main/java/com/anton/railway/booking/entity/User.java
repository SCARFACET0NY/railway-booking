package com.anton.railway.booking.entity;

import com.anton.railway.booking.entity.enums.AccountStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;
    @Column(name = "first_name")
    @NotEmpty
    private String firstName;
    @Column(name = "last_name")
    @NotEmpty
    private String lastName;
    @Column(name = "phone")
    @Pattern(regexp = "[0-9]{3}-[0-9]{3}-[0-9]{2}-[0-9]{2}", message = "{validation.messages.phone}")
    private String phone;
    @Column(name = "email")
    @Email
    private String email;
    @Column(name = "date_joined")
    private LocalDateTime dateJoined;
    @Column(name = "card_number")
    @Pattern(regexp = "[0-9]{10}", message = "{validation.messages.card.number}")
    private String cardNumber;
    @Column(name = "username")
    @Size(min = 3, message = "{validation.message.username.size}")
    private String username;
    @Column(name = "password")
    @Pattern(regexp = "((?=.*[A-Z]).{5,})", message = "{validation.message.password}")
    private String password;
    @Column(name = "account_status")
    @Enumerated(value = EnumType.STRING)
    private AccountStatus accountStatus;
    @OneToMany(mappedBy = "user")
    private List<Payment> payments;
}
