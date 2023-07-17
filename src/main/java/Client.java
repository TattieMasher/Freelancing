public class Client {
    private int id;
    private String name;
    private int primary_user;

    public Client(int id, String name, int primary_user) {
        this.id = id;
        this.name = name;
        this.primary_user = primary_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrimary_user() {
        return primary_user;
    }

    public void setPrimary_user(int primary_user) {
        this.primary_user = primary_user;
    }

    // Other methods to implement
}
