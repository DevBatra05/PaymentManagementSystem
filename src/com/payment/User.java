public class User {
    protected int userId;
    protected String name;
    protected String email;
    protected double balance;

    public User(int userId, String name, String email, double balance) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public void displayUserInfo() {
        System.out.println("User: " + name + ", Email: " + email + ", Balance: $" + balance);
    }
}