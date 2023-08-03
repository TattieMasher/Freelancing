package workforce.freelance.client;

import jakarta.persistence.*;
import workforce.freelance.user.User;

@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "primary_user")
    private Long primaryUserId;

    // TODO: CHECK ME
    @OneToOne(mappedBy = "client")
    @JoinColumn(name = "primary_user", referencedColumnName = "id")
    private User primaryUser;

    public Client() {
    }

    public Client(Long id, String name, Long primaryUserId) {
        this.id = id;
        this.name = name;
        this.primaryUserId = primaryUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrimaryUserId() {
        return primaryUserId;
    }

    public void setPrimaryUserId(Long primaryUser) {
        this.primaryUserId = primaryUser;
    }

    public User getPrimaryUser() {
        return primaryUser;
    }

    public void setPrimaryUser(User primaryUser) {
        this.primaryUser = primaryUser;
    }
}
