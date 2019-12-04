package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import model.MenuItem;
import model.Order;
import model.Store;

public class DatabaseHelper extends SQLiteOpenHelper {
    // SQLite database and table names are not case sensitive
    public static final String DATABASE_NAME = "BeanAndLeaf.db";
    public static final String SALT = "password-salt";

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
                "Gender TEXT NOT NULL)");
        db.execSQL("CREATE TABLE Stores(" +
                "StoreID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "UserID INTEGER NOT NULL," +
                "StoreLat TEXT NOT NULL," +
                "StoreLong TEXT NOT NULL," +
                "StoreName TEXT NOT NULL," +
                "VerifPic BLOB NOT NULL," +
                "isVerified INT NOT NULL," + // no boolean type in SQLite -> 1 = verified, 0 = not verified
                "FOREIGN KEY (UserID) REFERENCES Users(UserID))");
        db.execSQL("CREATE TABLE MenuItems(" +
                "MenuItemID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "StoreID INTEGER NOT NULL," +
                "ItemName TEXT NOT NULL," +
                "Calories INTEGER NOT NULL," +
                "Size TEXT NOT NULL," +
                "Caffeine INTEGER NOT NULL," +
                "Price TEXT NOT NULL," +
                "TimeCreated TEXT NOT NULL," +
                "FOREIGN KEY (StoreID) REFERENCES Stores(StoreID))");
        db.execSQL("CREATE TABLE Orders(" +
                "OrderID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "UserID INTEGER NOT NULL," +
                "MenuItemID INTEGER NOT NULL," +
                "StoreID INTEGER NOT NULL," +
                "Quantity INTEGER NOT NULL," +
                "CaffeineLogged INTEGER NOT NULL, " +
                "CaloriesLogged INTEGER NOT NULL, " +
                "PriceLogged TEXT NOT NULL, " +
                "Name TEXT NOT NULL, " +
                "OrderTime TEXT NOT NULL," +
                "FOREIGN KEY (UserID) REFERENCES Users(UserID)," +
                "FOREIGN KEY (MenuItemID) REFERENCES MenuItems(MenuItemID)," +
                "FOREIGN KEY (StoreID) REFERENCES Stores(StoreID))");
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

    public boolean insertUser(String name, String email, String password, String userType, String gender) {
        ContentValues cv = new ContentValues();
        cv.put("Username", name);
        cv.put("Password", password);
        cv.put("Email", email);
        cv.put("UserType", userType);
        cv.put("Gender", gender);
        long result = db.insert("Users", null, cv);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public String verifyUser(String email, String password, String userType) {
        String whereClause = "SELECT Username, Password FROM Users WHERE Email=? AND UserType=?";
        String whereArgs[] = {email, userType};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        if (res.moveToNext()) {
            String dbPass = res.getString(1);
            if (dbPass.contentEquals(password)) {
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

    public void removeUser(String email, String userType) {
        String whereClause = "DELETE FROM Users WHERE Email=? AND UserType=?";
        String whereArgs[] = {email, userType};
        db.execSQL(whereClause, whereArgs);

    }

    public int getUserId(String email, String userType) {
        String whereClause = "SELECT UserID FROM Users WHERE Email=? AND UserType=?";
        String whereArgs[] = {email, userType};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        if (res.moveToNext()) {
            return res.getInt(0);
        }
        return -1;
    }

    public String getUserGender(String email, String userType) {
        String whereClause = "SELECT Gender FROM Users WHERE Email=? AND UserType=?";
        String whereArgs[] = {email, userType};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        if (res.moveToNext()) {
            return res.getString(0);
        }
        return null;
    }

    public String getUserName(String email, String userType) {
        String whereClause = "SELECT Username FROM Users WHERE Email=? AND UserType=?";
        String whereArgs[] = {email, userType};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        if (res.moveToNext()) {
            return res.getString(0);
        }
        return null;
    }

    public String getUserPassword(String email, String userType) {
        String whereClause = "SELECT Password FROM Users WHERE Email=? AND UserType=?";
        String whereArgs[] = {email, userType};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        if (res.moveToNext()) {
            return res.getString(0);
        }
        return null;
    }

    public String getUserEmail(String userID) {
        String whereClause = "SELECT Email FROM Users WHERE UserID=?";
        String whereArgs[] = {userID};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        if (res.moveToNext()) {
            return res.getString(0);
        }
        return null;
    }

    public Integer getUserIDfromOrderID(Integer orderID) {
        String whereClause = "SELECT UserID FROM Orders WHERE OrderID=?";
        String whereArgs[] = {orderID.toString()};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        if (res.moveToNext()) {
            return res.getInt(0);
        }
        return null;
    }

    public boolean updateUserName(String email, String userType, String newName) {
        ContentValues cv = new ContentValues();
        cv.put("Username", newName);
        String whereClause = "Email=? AND UserType=?";
        String whereArgs[] = {email, userType};
        long result = db.update("Users", cv, whereClause, whereArgs);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateUserEmail(String email, String userType, String newEmail) {
        ContentValues cv = new ContentValues();
        cv.put("Email", newEmail);
        String whereClause = "Email=? AND UserType=?";
        String whereArgs[] = {email, userType};
        long result = db.update("Users", cv, whereClause, whereArgs);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateUserGender(String email, String userType, String newGender) {
        ContentValues cv = new ContentValues();
        cv.put("Gender", newGender);
        String whereClause = "Email=? AND UserType=?";
        String whereArgs[] = {email, userType};
        long result = db.update("Users", cv, whereClause, whereArgs);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateUserPassword(Integer userID, String newPass) {
        ContentValues cv = new ContentValues();
        cv.put("Password", newPass);
        String whereClause = "UserID=?";
        String whereArgs[] = {userID.toString()};
        long result = db.update("Users", cv, whereClause, whereArgs);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateStoreName(Integer userID, String lat, String lon, String newName) {
        ContentValues cv = new ContentValues();
        cv.put("StoreName", newName);
        String whereClause = "UserID=? AND StoreLat=? AND StoreLong=?";
        String whereArgs[] = {userID.toString(), lat, lon};
        long result = db.update("Stores", cv, whereClause, whereArgs);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateStoreLat(Integer userID, String lat, String lon, String newLat) {
        ContentValues cv = new ContentValues();
        cv.put("StoreLat", newLat);
        String whereClause = "UserID=? AND StoreLat=? AND StoreLong=?";
        String whereArgs[] = {userID.toString(), lat, lon};
        long result = db.update("Stores", cv, whereClause, whereArgs);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateStoreLong(Integer userID, String lat, String lon, String newLat) {
        ContentValues cv = new ContentValues();
        cv.put("StoreLong", newLat);
        String whereClause = "UserID=? AND StoreLat=? AND StoreLong=?";
        String whereArgs[] = {userID.toString(), lat, lon};
        long result = db.update("Stores", cv, whereClause, whereArgs);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateStoreVerification(Integer storeID) {
        ContentValues cv = new ContentValues();
        cv.put("isVerified", 1);
        String whereClause = "StoreID=?";
        String whereArgs[] = {storeID.toString()};
        long result = db.update("Stores", cv, whereClause, whereArgs);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertStore(Integer userId, Float lat, Float lon, String storeName, Bitmap photo) {
        ContentValues cv = new ContentValues();
        cv.put("UserID", userId);
        cv.put("StoreLat", Float.toString(lat));
        cv.put("StoreLong", Float.toString(lon));
        cv.put("StoreName", storeName);
        cv.put("isVerified",0);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if(photo != null) {
            photo.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        }
        cv.put("VerifPic", outputStream.toByteArray());


        long result = db.insert("Stores", null, cv);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Store getStore(Integer storeId) {
        Store store = null;
        String whereClause = "SELECT * FROM Stores WHERE storeID=?";
        String whereArgs[] = {Integer.toString(storeId)};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        if (res.moveToNext()) {
            byte[] picBytes = res.getBlob(5);
            boolean isVerified = res.getInt(6) == 1 ? true : false;

            store = new Store(
                    res.getInt(0),
                    Float.parseFloat(res.getString(3)),
                    Float.parseFloat(res.getString(2)),
                    res.getString(4),
                    isVerified,
                    BitmapFactory.decodeByteArray(picBytes, 0, picBytes.length)
            );
        }
        return store;
    }


    //Note: Should probably have more checks bc of Stores with the same name
    public int getStoreId(String storeName) {
        String whereClause = "SELECT StoreID FROM Stores WHERE StoreName=?";
        String whereArgs[] = {storeName};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        if (res.moveToNext()) {
            return res.getInt(0);
        }
        return -1;

    }

    public String getStoreName(Integer itemID) {
        String name = null;
        String whereClause = "SELECT StoreID FROM MenuItems WHERE MenuItemID=?";
        String whereArgs[] = {Integer.toString(itemID)};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        if (res.moveToNext()) {
            String whereClause2 = "SELECT StoreName FROM Stores WHERE StoreID=?";
            String whereArgs2[] = {Integer.toString(res.getInt(0))};
            res = db.rawQuery(whereClause2, whereArgs2);
            if (res.moveToNext()) {
                name = res.getString(0);
            }
        }
        return name;
    }

    public ArrayList<Store> getStores() {
        ArrayList<Store> stores = new ArrayList<>();
        String whereClause = "SELECT * FROM Stores";

        Cursor res = db.rawQuery(whereClause, null);
        while (res.moveToNext()) {
            byte[] picBytes = res.getBlob(5);
            boolean isVerified = res.getInt(6) == 1 ? true : false;

            stores.add(new Store(
                    res.getInt(0),
                    Float.parseFloat(res.getString(3)),
                    Float.parseFloat(res.getString(2)),
                    res.getString(4),
                    isVerified,
                    BitmapFactory.decodeByteArray(picBytes, 0, picBytes.length)
            ));
        }
        return stores;
    }

    public ArrayList<Store> getStores(Integer userID) {
        ArrayList<Store> stores = new ArrayList<>();
        String whereClause = "SELECT * FROM Stores WHERE UserID=?";
        String[] whereArgs = {userID.toString()};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        while (res.moveToNext()) {
            byte[] picBytes = res.getBlob(5);
            boolean isVerified = res.getInt(6) == 1 ? true : false;

            stores.add(new Store(
                    res.getInt(0),
                    Float.parseFloat(res.getString(3)),
                    Float.parseFloat(res.getString(2)),
                    res.getString(4),
                    isVerified,
                    BitmapFactory.decodeByteArray(picBytes, 0, picBytes.length)
            ));
        }
        return stores;
    }

    public boolean removeStore(Integer userId, String storeLat, String storeLong, String storeName) {
        String whereClause = "UserID=? AND StoreLat=? AND StoreName=? AND StoreLong=?";
        String whereArgs[] = {userId.toString(), storeLat, storeName, storeLong};
        long result = db.delete("Stores", whereClause, whereArgs);
        if (result > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeOrders(Integer userId) {
        String whereClause = "UserID=?";
        String whereArgs[] = {userId.toString()};
        long result = db.delete("Orders", whereClause, whereArgs);
        if (result > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeStores(Integer userId) {
        String whereClause = "UserID=?";
        String whereArgs[] = {userId.toString()};
        long result = db.delete("Stores", whereClause, whereArgs);
        if (result > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<MenuItem> getMenu(Integer storeId) {
        ArrayList<MenuItem> menu = new ArrayList<>();
        String whereClause = "SELECT * FROM MenuItems WHERE storeID=?";
        String whereArgs[] = {Integer.toString(storeId)};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        while (res.moveToNext()) {
            menu.add(new MenuItem(res.getInt(0),
                    res.getString(2),
                    res.getInt(3),
                    res.getString(4),
                    res.getInt(5),
                    Double.parseDouble(res.getString(6)),
                    res.getString(7)));
        }
        return menu;
    }

    public int getMenuItemId(Integer storeID, String name, String size) {
        String whereClause = "SELECT MenuItemID FROM MenuItems WHERE StoreID=? AND ItemName=? AND Size=?";
        String whereArgs[] = {Integer.toString(storeID), name, size};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        if (res.moveToNext()) {
            return res.getInt(0);
        }
        return -1;

    }

    public MenuItem getMenuItem(Integer storeID, String name, String size) {
        MenuItem item = null;
        String whereClause = "SELECT * FROM MenuItems WHERE StoreID=? AND ItemName=? AND Size=?";
        String whereArgs[] = {Integer.toString(storeID), name, size};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        if (res.moveToNext()) {
            item = new MenuItem(res.getInt(0),
                    res.getString(2),
                    res.getInt(3),
                    res.getString(4),
                    res.getInt(5),
                    Double.parseDouble(res.getString(6)),
                    res.getString(7));
        }
        return item;
    }

    public boolean checkMenuItemNameExists(Integer storeID, String name) {
        String whereClause = "SELECT * FROM MenuItems WHERE StoreID=? AND ItemName=?";
        String whereArgs[] = {Integer.toString(storeID), name};

        Cursor res = db.rawQuery(whereClause, whereArgs);
        if (res.moveToNext()) {
            return true;
        }
        return false;
    }

    public boolean insertMenuItem(Integer storeId, String name, String calories, String size, String caffeine, String price, String timeCreated) {
        ContentValues cv = new ContentValues();
        cv.put("StoreID", storeId);
        cv.put("Price", price);
        cv.put("ItemName", name);
        cv.put("Calories", calories);
        cv.put("Size", size);
        cv.put("Caffeine", caffeine);
        cv.put("TimeCreated", timeCreated);
        long result = db.insert("MenuItems", null, cv);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean removeMenuItem(Integer storeID, String name, String size) {
        String whereClause = "StoreID=? AND ItemName=? AND Size=?";
        String whereArgs[] = {storeID.toString(), name, size};
        long result = db.delete("MenuItems", whereClause, whereArgs);
        if (result > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeStoreMenu(Integer storeID) {
        String whereClause = "StoreID=?";
        String whereArgs[] = {storeID.toString()};
        long result = db.delete("MenuItems", whereClause, whereArgs);
        if (result > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean updateMenuItem(Integer menuItemID, String newName, String newCal, String newCaf, String newPrice) {
        ContentValues cv = new ContentValues();
        cv.put("Price", newPrice);
        cv.put("ItemName", newName);
        cv.put("Calories", newCal);
        cv.put("Caffeine", newCaf);
        String whereClause = "MenuItemID=?";
        String whereArgs[] = {menuItemID.toString()};
        long result = db.update("MenuItems", cv, whereClause, whereArgs);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertOrder(Integer userId, Integer menuItemId, Integer storeId, Integer quantity, Integer caffeine, Integer calories, String price, String name, String time) {
        ContentValues cv = new ContentValues();
        cv.put("UserID", userId);
        cv.put("MenuItemID", menuItemId);
        cv.put("StoreID", storeId);
        cv.put("Quantity", quantity);
        cv.put("CaffeineLogged", caffeine);
        cv.put("CaloriesLogged", calories);
        cv.put("PriceLogged", price);
        cv.put("Name", name);
        cv.put("OrderTime", time);
        long result = db.insert("Orders", null, cv);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public ArrayList<Order> getUserOrders(Integer userId) {
        ArrayList<Order> orders = new ArrayList<>();
        String whereClause = "SELECT * FROM Orders WHERE UserID=?";
        String whereArgs[] = {userId.toString()};
        Cursor res = db.rawQuery(whereClause, whereArgs);
        while (res.moveToNext()) {
            orders.add(new Order(
                    res.getInt(0),
                    res.getInt(2),
                    res.getInt(4),
                    res.getInt(5),
                    res.getInt(6),
                    Double.parseDouble(res.getString(7)),
                    res.getString(8),
                    res.getInt(9)
            ));
        }
        return orders;
    }

    public ArrayList<Order> getStoreOrders(Integer storeID) {
        ArrayList<Order> orders = new ArrayList<>();
        String whereClause = "SELECT * FROM Orders WHERE StoreID=?";
        String whereArgs[] = {storeID.toString()};
        Cursor res = db.rawQuery(whereClause, whereArgs);
        while (res.moveToNext()) {
            orders.add(new Order(
                    res.getInt(0),
                    res.getInt(2),
                    res.getInt(4),
                    res.getInt(5),
                    res.getInt(6),
                    Double.parseDouble(res.getString(7)),
                    res.getString(8),
                    res.getInt(9)
            ));
        }
        return orders;
    }

    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();
        input = SALT + input;
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

            for (int i = 0; i < hashedBytes.length; ++i) {
                byte b = hashedBytes[i];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException nsae) {
            System.out.println("Error generating hash: " + nsae.getMessage());
        }
        String hashString = hash.toString();
        return hashString;
    }
}
