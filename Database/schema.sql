CREATE DATABASE pfinance_db;
USE pfinance_db;
-- Users Table
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50)
);

-- Accounts Table
CREATE TABLE Accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    account_name VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- Categories Table
CREATE TABLE Categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- Transactions Table
CREATE TABLE Transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    account_id INT NOT NULL,
    category_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    type ENUM('income', 'expense') NOT NULL,
    description VARCHAR(255),
    date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (account_id) REFERENCES Accounts(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES Categories(id) ON DELETE CASCADE
);
INSERT INTO Users (username, password, email, first_name, last_name) VALUES
('Ahsan_Ali', 'lemon123', 'ahsanali@gmail.com', 'Ahsan', 'Ali'),
('Nishad_Arumugam', 'orange589', 'nishadarumugam@gmail.com', 'Nishad', 'arumugam'),
('Dalen_Martin', 'yellow125', 'dalenmartin@gmail.com', 'Dalen', 'Martin'),
('Dana_White', 'ufc400', 'danawhite@gmail.com', 'Dana', 'White'),
('Brock_Purdy', 'niners49', 'brockpurdy@gmail.com', 'Brock', 'Purdy'),
('Virat_Kholi', 'ICC2024', 'viratkholi@gmail.com', 'Virat', 'Kholi'),
('Brad_Pitt', 'actor101', 'bradpitt@gmail.com', 'Brad', 'Pit'),
('The_Weekend', 'songs123', 'theweekend@gmail.com', 'Abel', 'Tesfaye'),
('Cristiano_Ronaldo', 'worldcup2025', 'cristianoronaldo@gmail.com', 'Cristiano', 'Ronaldo'),
('Babar_Azam', 'zalmi2025', 'babarazam@gmail.com', 'Babar', 'Azam'),
('Barack_Obama', 'potus2016', 'barackobama@gmail.com', 'Barack', 'Obama'),
('Muhammad_Ali', 'goatboxer', 'muhammadali@gmail.com', 'Muhammad', 'Ali'),
('Lebron_James', 'nbafinals2016', 'lebronjames@gmail.com', 'Lebron', 'James'),
('Mike_Tyson', 'tiger11', 'miketyson@gmail.com', 'Mike', 'Tyson'),
('Stephen_Curry', 'warriors30', 'stephencurry@gmail.com', 'Stephen', 'Curry');

-- Insert Accounts
INSERT INTO Accounts (user_id, account_name) VALUES
(1, 'Checking'),
(1, 'Savings'),
(2, 'Business'),
(3, 'Investment'),
(4, 'Vacation Fund'),
(5, 'Joint Account'),
(6, 'Crypto Wallet'),
(7, 'Emergency Fund'),
(8, 'College Savings'),
(9, 'Paypal'),
(10, 'Cash'),
(11, 'Chase Checking'),
(12, 'BOA Savings'),
(13, 'Venmo'),
(14, 'HSA Account'),
(15, 'Goldman Sachs');

-- Insert Categories
INSERT INTO Categories (user_id, name) VALUES
(1, 'Groceries'),
(1, 'Utilities'),
(2, 'Salary'),
(3, 'Investments'),
(4, 'Travel'),
(5, 'Dining Out'),
(6, 'Healthcare'),
(7, 'Education'),
(8, 'Subscriptions'),
(9, 'Entertainment'),
(10, 'Phone Bill'),
(11, 'Gym'),
(12, 'Insurance'),
(13, 'Transportation'),
(14, 'Gifts'),
(15, 'Pet Supplies');

-- Insert Transactions
INSERT INTO Transactions (user_id, account_id, category_id, amount, type, description, date) VALUES
(1, 1, 1, 150.0, 'expense', 'Grocery shopping at Target', '2024-04-01'),
(1, 2, 2, 80.0, 'expense', 'Electric Bill', '2024-04-03'),
(2, 3, 3, 4000.0, 'income', 'Monthly Salary', '2024-04-05'),
(3, 4, 4, 500.0, 'income', 'Stock Shares', '2024-04-06'),
(4, 5, 5, 250.0, 'expense', 'Hotel Booking', '2024-04-08'),
(5, 6, 6, 20.0, 'expense', 'Lunch at Halal Shack', '2024-04-09'),
(6, 7, 7, 150.0, 'expense', 'Doctor appointment', '2024-04-11'),
(7, 8, 8, 450.0, 'expense', 'Course Enrollment Fee', '2024-04-12'),
(8, 9, 9, 30.0, 'expense', 'Apple Music Subscription', '2024-04-14'),
(9, 10, 10, 45.0, 'expense', 'Phone Bill', '2024-04-15'),
(10, 11, 11, 30.0, 'expense', 'Monthly Gym Membership', '2024-04-17'),
(11, 12, 12, 150.0, 'expense', 'Health Insurance', '2024-04-18'),
(12, 13, 13, 90.0, 'expense', 'Uber Rides', '2024-04-20'),
(13, 14, 14, 150.0, 'expense', 'DoorDash', '2024-04-21'),
(14, 15, 15, 250.0, 'expense', 'Birthday Gift', '2024-04-23'),
(15, 15, 3, 3000.0, 'income', 'Freelance project', '2024-04-25');