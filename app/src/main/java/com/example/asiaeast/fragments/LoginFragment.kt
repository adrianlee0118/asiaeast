package com.example.asiaeast.fragments

import android.os.Bundle
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

        //TODO: Logic for signing in with email/password as per https://firebase.google.com/docs/auth/android/password-auth
        //TODO: Update navigation graph and navigation menu, main activity controller visibility for fragments
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

        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                    // [START_EXCLUDE]
                    checkForMultiFactorFailure(task.exception!!)
                    // [END_EXCLUDE]
                }

                if (!task.isSuccessful) {
                    status.setText(R.string.auth_failed)
                }
            }
        // [END sign_in_with_email]
    }

    private fun signOut() {
        auth.signOut()
        //TODO: Make a signout button that becomes visible when sign in happens, whereupon the sign in buttons become invisible, this function will make the sign in buttons appear visible again and auth becomes null
    }
}