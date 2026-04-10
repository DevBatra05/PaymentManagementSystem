public class Admin extends User {
    public Admin(int userId, String name, String email, double balance) {
        super(userId, name, email, balance);
    }

    public void manageTransactions() {
        System.out.println("Admin " + name + " is managing transactions.");
    }
}