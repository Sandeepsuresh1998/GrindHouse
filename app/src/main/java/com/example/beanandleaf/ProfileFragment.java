package com.example.beanandleaf;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {




    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return inflater.inflate(R.layout.fragment_profile, null);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        EditText usernameText = view.findViewById(R.id.name_edit);
        EditText emailText = view.findViewById(R.id.email_edit);
        RadioGroup genderOptions = view.findViewById(R.id.genderOptions);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);

        String username = pref.getString("username", null);
        String email = pref.getString("email", null);
        String gender = pref.getString("gender", null);

        usernameText.setText(username);
        emailText.setText(email);

        if (gender.contentEquals("Male")) {
            genderOptions.check(R.id.male);
        }
        else if (gender.contentEquals("Female")) {
            genderOptions.check(R.id.female);
        }
        else {
            genderOptions.check(R.id.other);
        }

        Button updateButton = view.findViewById(0);

    }

}

