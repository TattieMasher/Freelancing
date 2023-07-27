package workforce.freelance;

import jakarta.persistence.*;

@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "primary_user")
    private Long primaryUser;

    public Client() {
    }

    public Client(Long id, String name, Long primaryUser) {
        this.id = id;
        this.name = name;
        this.primaryUser = primaryUser;
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

    public Long getPrimaryUser() {
        return primaryUser;
    }

    public void setPrimaryUser(Long primaryUser) {
        this.primaryUser = primaryUser;
    }
}
