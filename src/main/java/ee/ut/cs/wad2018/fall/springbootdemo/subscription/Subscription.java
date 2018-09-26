package ee.ut.cs.wad2018.fall.springbootdemo.subscription;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "subscription")
public class Subscription {

    @Id
    @SequenceGenerator(name="subscription_id_seq", sequenceName="subscription_subscription_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_id_seq")
    private Long subscriptionId;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String email;
}
