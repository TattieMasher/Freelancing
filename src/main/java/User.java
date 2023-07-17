public class User {
    private int id;
    private String fName;
    private String sName;
    private String email;
    private boolean verified;
    private int userType;

    public User(int id, String fName, String sName, String email, boolean verified, int userType) {
        this.id = id;
        this.fName = fName;
        this.sName = sName;
        this.email = email;
        this.verified = verified;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setEmail(String email) {
        this.email = email;
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


}

