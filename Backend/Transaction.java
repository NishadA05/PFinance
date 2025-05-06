package Backend;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Model class for Transaction entity.
 */
public class Transaction {
    private int id;
    private int userId;
    private int accountId;
    private int categoryId;
    private BigDecimal amount;
    private String type; // "INCOME" or "EXPENSE"
    private String description;
    private Date date;

    // Additional fields for joining with other tables
    private String accountName;
    private String categoryName;

    public Transaction() {
    }

    public Transaction(int id, int userId, int accountId, int categoryId, BigDecimal amount,
                       String type, String description, Date date) {
        this.id = id;
        this.userId = userId;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.date = date;
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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", userId=" + userId + ", accountId=" + accountId +
                ", categoryId=" + categoryId + ", amount=" + amount + ", type=" + type +
                ", description=" + description + ", date=" + date + "]";
    }
}
