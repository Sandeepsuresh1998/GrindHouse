package com.example.beanandleaf.data;

import com.example.beanandleaf.data.model.LoggedInUser;

import java.io.IOException;

import database.DatabaseHelper;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password, String userType, DatabaseHelper db) {
        String result = db.verifyUser(username, password, userType);
        if (result.contentEquals("NULL")) {
            return new Result.Error(new IOException("That email isn't registered to a " + userType + " account yet"));
        }
        else if (result.contentEquals("INVALID")) {
            return new Result.Error(new IOException("Invalid password. Please try again"));
        }
        LoggedInUser liu = new LoggedInUser(java.util.UUID.randomUUID().toString(), result);
        return new Result.Success<>(liu);
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
