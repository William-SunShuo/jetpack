package com.example.jetpack1;
import androidx.databinding.ObservableField;

public class User {

    private String number_id;
    public ObservableField<String> firstName;
    public ObservableField<String> lastName;

    public User(ObservableField<String> firstName ,ObservableField<String> lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
