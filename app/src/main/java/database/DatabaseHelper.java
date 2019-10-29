package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    // SQLite database and table names are not case sensitive
    public static final String DATABASE_NAME = "BeanAndLeaf.db";

    private SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Users(" +
                "UserID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Username TEXT NOT NULL," +
                "Password TEXT NOT NULL," +
                "Email TEXT NOT NULL," +
                "UserType TEXT NOT NULL," +
                "Gender TEXT," +
                "PicURL TEXT)");
        db.execSQL("CREATE TABLE Stores(" +
                "StoreID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "UserID INTEGER NOT NULL," +
                "StoreLoc TEXT NOT NULL," +
                "StoreName TEXT NOT NULL," +
                "FOREIGN KEY (UserID) REFERENCES Users(UserID))");
        db.execSQL("CREATE TABLE MenuItems(" +
                "MenuItemID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "StoreID INTEGER NOT NULL," +
                "Price REAL NOT NULL," +
                "ItemName TEXT NOT NULL," +
                "FOREIGN KEY (StoreID) REFERENCES Stores(StoreID))");
        db.execSQL("CREATE TABLE Orders(" +
                "OrderID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "UserID INTEGER NOT NULL," +
                "MenuItemID INTEGER NOT NULL," +
                "StoreID INTEGER NOT NULL," +
                "Quantity INTEGER NOT NULL," +
                "OrderTime TEXT NOT NULL," +
                "FOREIGN KEY (UserID) REFERENCES Users(UserID)," +
                "FOREIGN KEY (MenuItemID) REFERENCES MenuItems(MenuItemID)," +
                "FOREIGN KEY (StoreID) REFERENCES Stores(StoreID))");
        db.execSQL("CREATE TABLE Trips(" +
                "TripID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "UserID INTEGER NOT NULL," +
                "StartLoc TEXT NOT NULL," +
                "EndLoc TEXT NOT NULL," +
                "TripDuration INTEGER NOT NULL," +
                "FOREIGN KEY (UserID) REFERENCES Users(UserID))");
        db.execSQL("CREATE TABLE TripOrders(" +
                "TripID INTEGER NOT NULL," +
                "OrderID INTEGER NOT NULL," +
                "FOREIGN KEY (TripID) REFERENCES Trips(TripID)," +
                "FOREIGN KEY (OrderID) REFERENCES Orders(OrderID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Stores");
        db.execSQL("DROP TABLE IF EXISTS MenuItems");
        db.execSQL("DROP TABLE IF EXISTS Orders");
        db.execSQL("DROP TABLE IF EXISTS Trips");
        db.execSQL("DROP TABLE IF EXISTS TripOrders");
        onCreate(db);
    }

    public boolean insertUser(String name, String email, String password, String userType) {
        ContentValues cv = new ContentValues();
        cv.put("Username", name);
        cv.put("Password", password);
        cv.put("Email", email);
        cv.put("UserType", userType);
        long result = db.insert("Users", null, cv);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public String verifyUser(String email, String password, String userType) {
        Cursor res = db.rawQuery("SELECT Username, Password FROM Users WHERE Email='" + email + "' AND UserType='" + userType + "'", null);
        if (res.moveToNext()) {
            if (res.getString(1).contentEquals(password)) {
                return res.getString(0); // valid login, return username
            }
            else {
                return "INVALID"; // wrong password
            }
        }
        else {
            return "NULL"; // no user found
        }
    }
}
