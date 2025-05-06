package Database;

import Backend.Category;
import Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Category entity
 */
public class CategoryDAO {

    /**
     * Create a new category in the database
     *
     * @param category The category to create
     * @return The created category with ID populated
     * @throws SQLException If database error occurs
     */
    public Category createCategory(Category category) throws SQLException {
        String sql = "INSERT INTO Categories (userId, name) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, category.getUserId());
            stmt.setString(2, category.getName());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating category failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    category.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating category failed, no ID obtained.");
                }
            }

            return category;
        }
    }

    /**
     * Get a category by its ID
     *
     * @param categoryId The ID of the category to retrieve
     * @return The category, or null if not found
     * @throws SQLException If database error occurs
     */
    public Category getCategoryById(int categoryId) throws SQLException {
        String sql = "SELECT * FROM Categories WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategory(rs);
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * Get all categories for a specific user
     *
     * @param userId The user ID to get categories for
     * @return List of categories
     * @throws SQLException If database error occurs
     */
    public List<Category> getCategoriesByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM Categories WHERE userId = ? ORDER BY name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Category> categories = new ArrayList<>();

                while (rs.next()) {
                    categories.add(mapResultSetToCategory(rs));
                }

                return categories;
            }
        }
    }

    /**
     * Update an existing category
     *
     * @param category The category to update
     * @return true if updated successfully
     * @throws SQLException If database error occurs
     */
    public boolean updateCategory(Category category) throws SQLException {
        String sql = "UPDATE Categories SET name = ? WHERE id = ? AND userId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getId());
            stmt.setInt(3, category.getUserId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Delete a category
     *
     * @param categoryId The ID of the category to delete
     * @param userId The user ID (for security)
     * @return true if deleted successfully
     * @throws SQLException If database error occurs
     */
    public boolean deleteCategory(int categoryId, int userId) throws SQLException {
        // First, set categoryId to NULL for all transactions with this category
        String updateTransactionsSql = "UPDATE Transactions SET categoryId = NULL WHERE categoryId = ? AND userId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateTransactionsSql)) {

            // Begin transaction
            conn.setAutoCommit(false);

            try {
                updateStmt.setInt(1, categoryId);
                updateStmt.setInt(2, userId);
                updateStmt.executeUpdate();

                // Then delete the category
                String deleteSql = "DELETE FROM Categories WHERE id = ? AND userId = ?";
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setInt(1, categoryId);
                    deleteStmt.setInt(2, userId);

                    int affectedRows = deleteStmt.executeUpdate();

                    // Commit transaction
                    conn.commit();

                    return affectedRows > 0;
                }
            } catch (SQLException e) {
                // Rollback transaction on error
                conn.rollback();
                throw e;
            } finally {
                // Restore auto-commit
                conn.setAutoCommit(true);
            }
        }
    }

    /**
     * Helper method to map ResultSet to Category object
     */
    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setUserId(rs.getInt("userId"));
        category.setName(rs.getString("name"));
        return category;
    }
}
