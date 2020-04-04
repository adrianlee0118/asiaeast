package com.example.asiaeast.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private val TAG = "LOGIN FRAGMENT"
    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by activityViewModels()   //declaration allows access to root activity viewmodel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController() //Initialising navController
        auth = FirebaseAuth.getInstance()

        login_btn.setOnClickListener { view ->
            signIn(email.text.toString(), password.text.toString())
        }

        //TODO: Fix logic for signing in with email/password in signIn() method below as per https://firebase.google.com/docs/auth/android/password-auth
        //TODO: Update navigation graph and navigation menu, main activity controller visibility for fragments
        //TODO: Add logout button and visibility changeability functions on buttons
    }

    override fun onStart() { //happens after oncreate, go to inputs fragment if authorization is already granted
        super.onStart()
        val currentUser = auth.currentUser
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

            //TODO: Intent or companion object action that directs NavController to go to EditInputsFragment
        } else {
            Toast.makeText(
                getActivity()!!.getBaseContext(),
                "Error logging in. Try again.",
                Toast.LENGTH_SHORT
            ).show()
            dialogue.text = "Error logging in. Try again."
        }
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    //TODO: Intent to go to EditInputs Activity
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(getActivity()!!.getBaseContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //TODO: Null intent? or just set the error message
                    checkForMultiFactorFailure(task.exception!!)
                    // [END_EXCLUDE]
                }
            }
        // [END sign_in_with_email]
    }

    private fun validateForm(): Boolean { //Check if inputs are empty
        var valid = true

        //TODO: add logic for checking if email meets email regulations possibly using a Regex expression

        if (TextUtils.isEmpty(email.text.toString())) {
            email.error = "Required."
            valid = false
        } else {
            email.error = null
        }

        if (TextUtils.isEmpty(password.text.toString())) {
            password.error = "Required."
            valid = false
        } else {
            password.error = null
        }

        return valid
    }

    private fun signOut() {
        auth.signOut()
        //TODO: Make a signout button that becomes visible when sign in happens, whereupon the sign in buttons become invisible, this function will make the sign in buttons appear visible again and auth becomes null
    }
}