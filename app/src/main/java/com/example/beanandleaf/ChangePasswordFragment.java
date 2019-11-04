package com.example.beanandleaf;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import database.DatabaseHelper;
import model.Store;

public class ChangePasswordFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return inflater.inflate(R.layout.fragment_change_password, null);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        final TextView oldPassEdit = view.findViewById(R.id.old_password_edit);
        final TextView newPassEdit = view.findViewById(R.id.new_password_edit);
        final TextView newPassConfEdit = view.findViewById(R.id.new_password_conf_edit);
        final Button updateBtn = view.findViewById(R.id.change_password_button);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = oldPassEdit.getText().toString();
                String newPass = newPassEdit.getText().toString();
                String newPassConf = newPassConfEdit.getText().toString();

                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
                String curPass = pref.getString("Password", null);
                String email = pref.getString("email", null);
                String userType = pref.getString("userType", null);

                if (!curPass.contentEquals(oldPass)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Old password is incorrect", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!newPass.contentEquals(newPassConf)) {
                    Toast.makeText(getActivity().getApplicationContext(), "New passwords do not match", Toast.LENGTH_LONG).show();
                    return;
                }
                if (curPass.contentEquals(newPass)) {
                    Toast.makeText(getActivity().getApplicationContext(), "New password cannot be the same as the old password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (isValidPassword(newPass)) {
                    DatabaseHelper db = new DatabaseHelper(getActivity());
                    int userId = db.getUserId(email, userType);
                    if (db.updateUserPassword(userId, newPass)) {
                        int id;
                        if (userType.contentEquals("Customer")) {
                            id = R.id.fragment_container_customer;
                        }
                        else {
                            id = R.id.fragment_container_merchant;
                        }
                        Toast.makeText(getActivity().getApplicationContext(), "Password updated successfully", Toast.LENGTH_LONG).show();
                        Fragment profileFragment = new ProfileFragment();
                        getFragmentManager()
                                .beginTransaction()
                                .replace(id, profileFragment)
                                .commit();
                        return;
                    }
                    else {
                        Toast.makeText(getActivity().getApplicationContext(), "Error: could not update password right now", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Password must be 5 or more characters", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
        return;
    }

    private boolean isValidPassword(String password) {
        return password != null && password.trim().length() > 5;
    }
}




