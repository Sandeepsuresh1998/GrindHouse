import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DATABASE = "BeanAndLeaf";
    private static final String URL = "jdbc:mysql://localhost:3306/";


    public static Connection createConnection() {
        verifyDBExists();
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL + DATABASE, USER, PASSWORD);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return con;
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    private static void verifyDBExists() {
        boolean found = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            rs = con.getMetaData().getCatalogs();
            while (rs.next()) {
                if (rs.getString(1).equals(DATABASE)) {
                  found = true;
                }
            }
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        if (!found) {
            createDB(con);
        }
        closeConnection(con);
    }

    private static void createDB(Connection con) {
        String createDB = "CREATE DATABASE BeanAndLeaf;" +
                "USE BeanAndLeaf;" +
                "CREATE TABLE Users (" +
                "UserID INT(11) PRIMARY KEY AUTO_INCREMENT," +
                "Username VARCHAR(50) NOT NULL," +
                "PasswordSHA VARCHAR(50) NOT NULL," +
                "Email VARCHAR(50) NOT NULL," +
                "Gender VARCHAR(10)," +
                "PicURL VARCHAR(61)," +
                "UserType VARCHAR(8) NOT NULL);" +
                "CREATE TABLE Stores (" +
                "StoreID INT(11) PRIMARY KEY AUTO_INCREMENT," +
                "UserID INT(11) NOT NULL," +
                "StoreLoc VARCHAR(100) NOT NULL," +
                "StoreName VARCHAR(32) NOT NULL," +
                "FOREIGN KEY (UserId) REFERENCES Users(UserID));" +
                "CREATE TABLE MenuItems (" +
                "MenuItemID INT(11) PRIMARY KEY AUTO_INCREMENT," +
                "StoreID INT(11) NOT NULL," +
                "Price FLOAT(5,2) NOT NULL," +
                "ItemName VARCHAR(50) NOT NULL," +
                "FOREIGN KEY (StoreID) REFERENCES Stores(StoreID));" +
                "CREATE TABLE Orders (" +
                "OrderID INT(11) PRIMARY KEY AUTO_INCREMENT," +
                "UserID INT(11) NOT NULL," +
                "MenuItemID INT(11) NOT NULL," +
                "StoreID INT(11) NOT NULL," +
                "Quantity INT(3) NOT NULL," +
                "OrderTime DATETIME NOT NULL," +
                "FOREIGN KEY (UserID) REFERENCES Users(UserID)," +
                "FOREIGN KEY (MenuItemID) REFERENCES MenuItems(MenuItemID)," +
                "FOREIGN KEY (StoreID) REFERENCES Stores(StoreID));" +
                "CREATE TABLE Trips (" +
                "TripID INT(11) PRIMARY KEY AUTO_INCREMENT," +
                "UserID INT(11) NOT NULL," +
                "StartLoc VARCHAR(100) NOT NULL," +
                "EndLoc VARCHAR(100) NOT NULL," +
                "TripDuration INT(5) NOT NULL," +
                "FOREIGN KEY (UserID) REFERENCES Users(UserID));" +
                "CREATE TABLE TripOrders(" +
                "TripID INT(11)," +
                "OrderID INT(11)," +
                "FOREIGN KEY (TripID) REFERENCES Trips(TripID)," +
                "FOREIGN KEY (OrderID) REFERENCES Orders(OrderID));";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(createDB);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }
}
