package com.example.jetpack1;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class User {

    public ObservableField<String> phone;
    public ObservableField<String> password;
    public ObservableInt age = new ObservableInt(2);


    public User(ObservableField<String> phone ,ObservableField<String> password){
        this.phone = phone;
        this.password = password;
    }

}
