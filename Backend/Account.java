package Backend;

/**
 * Model class for Account entity.
 */
public class Account {
    private int id;
    private int userId;
    private String accountName;

    public Account() {
    }

    public Account(int id, int userId, String accountName) {
        this.id = id;
        this.userId = userId;
        this.accountName = accountName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", userId=" + userId + ", accountName=" + accountName + "]";
    }
}
