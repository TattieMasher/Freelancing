package workforce.freelance.user;

import jakarta.persistence.*;
import workforce.freelance.client.Client;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String fName;

    @Column(name = "last_name")
    private String sName;

    @Column(name = "email")
    private String email;

    @Column(name = "verified")
    private boolean verified;

    @Column(name = "type")
    private int userType;

    @ManyToOne
    @JoinColumn(name = "organisation")
    private Client client;

    public User() {};

    public User(Long id, String fName, String sName, String email, boolean verified, int userType) {
        this.id = id;
        this.fName = fName;
        this.sName = sName;
        this.email = email;
        this.verified = verified;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getEmail() {
        return email;
    }

    // Set and validate email
    public void setEmail(String email) {
        if (isValidEmailAddress(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getFullName() {
        return fName + " " + sName;
    }

    // Check if email address is valid via RegEx (TODO: Maybe use stricter RegEx?)
    public static boolean isValidEmailAddress(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return emailAddress.matches(regexPattern);
    }
}

