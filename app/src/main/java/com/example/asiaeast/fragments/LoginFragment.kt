package com.example.asiaeast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.asiaeast.R
import com.example.asiaeast.models.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by activityViewModels()   //declaration allows access to root activity viewmodel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()

        //TODO: Logic for signing in with email/password as per https://firebase.google.com/docs/auth/android/password-auth
        //TODO: Update navigation graph and navigation menu, main activity controller visibility for fragments
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController() //Initialising navController
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        //Go to next fragment if ccurrentUser is not null, else don't as per private fun updateUI(user: FirebaseUser?) on the Github page
        goToInputs(currentUser)
    }

    private fun goToInputs(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(
                getActivity()!!.getBaseContext(),
                "Login successful.",
                Toast.LENGTH_SHORT
            ).show()
            dialogue.text = "Login successful."

            if (user.isEmailVerified) {
                verifyEmailButton.visibility = View.GONE
            } else {
                verifyEmailButton.visibility = View.VISIBLE
            }
        } else {
            Toast.makeText(
                getActivity()!!.getBaseContext(),
                "Error logging in. Try again.",
                Toast.LENGTH_SHORT
            ).show()
            dialogue.text = "Error logging in. Try again."
        }
    }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            login_button -> signIn(email.text.toString(), password.text.toString())
        }
    }
}