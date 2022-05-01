package com.example.jetpack1
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.example.jetpack1.databinding.AcitivityLoginBinding

class LoginActivity: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<AcitivityLoginBinding>(this,R.layout.acitivity_login)

        var user = User(ObservableField(""), ObservableField(""))
//        user!!.phone.addOnPropertyChangedCallback(object :
//            Observable.OnPropertyChangedCallback() {
//            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//                Log.i(this@LoginActivity::class.java.simpleName,"{${binding.user!!.phone.get()}}")
//            }
//
//        })
        binding.user = user
        binding.button.visibility = View.VISIBLE



//        binding.setClickListener {
//            user.firstName.set("san")
//            user.lastName.set("zhang")
//            viewModel.pass("12344334545","123456")
//        }

    }
}