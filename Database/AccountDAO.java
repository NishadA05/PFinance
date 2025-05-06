package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Backend.Account;
import Database.DBConnection;

/**
 * Data Access Object for Account entity.
 */
public class AccountDAO {

    /**
     * Creates a new account in the database.
     *
     * @param account the Account object to insert
     * @return the generated account ID, or -1 if operation fails
     */
    public int createAccount(Account account) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int accountId = -1;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO Accounts (userId, accountName) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, account.getUserId());
            stmt.setString(2, account.getAccountName());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    accountId = rs.getInt(1);
                    account.setId(accountId);
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

        return accountId;
    }

    /**
     * Gets an account by ID.
     *
     * @param accountId the account ID
     * @return Account object if found, null otherwise
     */
    public Account getAccount(int accountId) {
        Account account = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Accounts WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);

            rs = stmt.executeQuery();
            if (rs.next()) {
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setUserId(rs.getInt("userId"));
                account.setAccountName(rs.getString("accountName"));
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

        return account;
    }

    /**
     * Gets all accounts for a user.
     *
     * @param userId the user ID
     * @return List of Account objects
     */
    public List<Account> getUserAccounts(int userId) {
        List<Account> accounts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Accounts WHERE userId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            rs = stmt.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUserId(rs.getInt("userId"));
                account.setAccountName(rs.getString("accountName"));
                accounts.add(account);
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

        return accounts;
    }

    /**
     * Updates an account in the database.
     *
     * @param account the Account object to update
     * @return true if update succeeds, false otherwise
     */
    public boolean updateAccount(Account account) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE Accounts SET accountName = ? WHERE id = ? AND userId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, account.getAccountName());
            stmt.setInt(2, account.getId());
            stmt.setInt(3, account.getUserId());

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
     * Deletes an account from the database.
     *
     * @param accountId the account ID
     * @param userId the user ID (for security check)
     * @return true if deletion succeeds, false otherwise
     */
    public boolean deleteAccount(int accountId, int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM Accounts WHERE id = ? AND userId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);
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
}
