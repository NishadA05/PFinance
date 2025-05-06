package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

import Backend.Transaction;
import Database.DBConnection;

/**
 * Data Access Object for Transaction entity.
 */
public class TransactionDAO {

    /**
     * Creates a new transaction in the database.
     *
     * @param transaction the Transaction object to insert
     * @return the generated transaction ID, or -1 if operation fails
     */
    public int createTransaction(Transaction transaction) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int transactionId = -1;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO Transactions (userId, accountId, categoryId, amount, type, description, date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, transaction.getUserId());
            stmt.setInt(2, transaction.getAccountId());
            stmt.setInt(3, transaction.getCategoryId());
            stmt.setBigDecimal(4, transaction.getAmount());
            stmt.setString(5, transaction.getType());
            stmt.setString(6, transaction.getDescription());
            stmt.setTimestamp(7, new Timestamp(transaction.getDate().getTime()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    transactionId = rs.getInt(1);
                    transaction.setId(transactionId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DBConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return transactionId;
    }

    /**
     * Gets a transaction by ID.
     *
     * @param transactionId the transaction ID
     * @param userId the user ID (for security check)
     * @return Transaction object if found, null otherwise
     */
    public Transaction getTransaction(int transactionId, int userId) {
        Transaction transaction = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT t.*, a.accountName, c.name as categoryName " +
                    "FROM Transactions t " +
                    "JOIN Accounts a ON t.accountId = a.id " +
                    "JOIN Categories c ON t.categoryId = c.id " +
                    "WHERE t.id = ? AND t.userId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transactionId);
            stmt.setInt(2, userId);

            rs = stmt.executeQuery();
            if (rs.next()) {
                transaction = mapTransaction(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DBConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return transaction;
    }

    /**
     * Gets all transactions for a user.
     *
     * @param userId the user ID
     * @return List of Transaction objects
     */
    public List<Transaction> getUserTransactions(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT t.*, a.accountName, c.name as categoryName " +
                    "FROM Transactions t " +
                    "JOIN Accounts a ON t.accountId = a.id " +
                    "JOIN Categories c ON t.categoryId = c.id " +
                    "WHERE t.userId = ? " +
                    "ORDER BY t.date DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(mapTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DBConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return transactions;
    }

    /**
     * Gets transactions for a specific account.
     *
     * @param accountId the account ID
     * @param userId the user ID (for security check)
     * @return List of Transaction objects
     */
    public List<Transaction> getAccountTransactions(int accountId, int userId) {
        List<Transaction> transactions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT t.*, a.accountName, c.name as categoryName " +
                    "FROM Transactions t " +
                    "JOIN Accounts a ON t.accountId = a.id " +
                    "JOIN Categories c ON t.categoryId = c.id " +
                    "WHERE t.accountId = ? AND t.userId = ? " +
                    "ORDER BY t.date DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);
            stmt.setInt(2, userId);

            rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(mapTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DBConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return transactions;
    }

    /**
     * Gets transactions for a specific category.
     *
     * @param categoryId the category ID
     * @param userId the user ID (for security check)
     * @return List of Transaction objects
     */
    public List<Transaction> getCategoryTransactions(int categoryId, int userId) {
        List<Transaction> transactions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT t.*, a.accountName, c.name as categoryName " +
                    "FROM Transactions t " +
                    "JOIN Accounts a ON t.accountId = a.id " +
                    "JOIN Categories c ON t.categoryId = c.id " +
                    "WHERE t.categoryId = ? AND t.userId = ? " +
                    "ORDER BY t.date DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, categoryId);
            stmt.setInt(2, userId);

            rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(mapTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DBConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return transactions;
    }

    /**
     * Updates a transaction in the database.
     *
     * @param transaction the Transaction object to update
     * @return true if update succeeds, false otherwise
     */
    public boolean updateTransaction(Transaction transaction) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE Transactions SET accountId = ?, categoryId = ?, amount = ?, " +
                    "type = ?, description = ?, date = ? WHERE id = ? AND userId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transaction.getAccountId());
            stmt.setInt(2, transaction.getCategoryId());
            stmt.setBigDecimal(3, transaction.getAmount());
            stmt.setString(4, transaction.getType());
            stmt.setString(5, transaction.getDescription());
            stmt.setTimestamp(6, new Timestamp(transaction.getDate().getTime()));
            stmt.setInt(7, transaction.getId());
            stmt.setInt(8, transaction.getUserId());

            int affectedRows = stmt.executeUpdate();
            success = (affectedRows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DBConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    /**
     * Deletes a transaction from the database.
     *
     * @param transactionId the transaction ID
     * @param userId the user ID (for security check)
     * @return true if deletion succeeds, false otherwise
     */
    public boolean deleteTransaction(int transactionId, int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM Transactions WHERE id = ? AND userId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transactionId);
            stmt.setInt(2, userId);

            int affectedRows = stmt.executeUpdate();
            success = (affectedRows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DBConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    /**
     * Gets account balance (sum of all transactions).
     *
     * @param accountId the account ID
     * @param userId the user ID (for security check)
     * @return the account balance as BigDecimal
     */
    public BigDecimal getAccountBalance(int accountId, int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        BigDecimal balance = BigDecimal.ZERO;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT " +
                    "SUM(CASE WHEN type = 'INCOME' THEN amount ELSE -amount END) as balance " +
                    "FROM Transactions " +
                    "WHERE accountId = ? AND userId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);
            stmt.setInt(2, userId);

            rs = stmt.executeQuery();
            if (rs.next()) {
                BigDecimal result = rs.getBigDecimal("balance");
                if (result != null) {
                    balance = result;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DBConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return balance;
    }

    /**
     * Helper method to map ResultSet row to Transaction object.
     *
     * @param rs the ResultSet
     * @return Transaction object
     * @throws SQLException if a database access error occurs
     */
    private Transaction mapTransaction(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(rs.getInt("id"));
        transaction.setUserId(rs.getInt("userId"));
        transaction.setAccountId(rs.getInt("accountId"));
        transaction.setCategoryId(rs.getInt("categoryId"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setType(rs.getString("type"));
        transaction.setDescription(rs.getString("description"));
        transaction.setDate(new Date(rs.getTimestamp("date").getTime()));

        // Additional joined fields
        transaction.setAccountName(rs.getString("accountName"));
        transaction.setCategoryName(rs.getString("categoryName"));

        return transaction;
    }
}
