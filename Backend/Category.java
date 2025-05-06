package Backend;

/**
 * Model class for Category entity.
 */
public class Category {
    private int id;
    private int userId;
    private String name;

    public Category() {
    }

    public Category(int id, int userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", userId=" + userId + ", name=" + name + "]";
    }
}
