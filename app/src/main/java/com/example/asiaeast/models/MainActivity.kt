package com.example.asiaeast.models

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asiaeast.R
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseException
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GithubAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PlayGamesAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import java.util.concurrent.TimeUnit

/**
 * Created by harshitdwivedi on 14/03/18.
 */
abstract class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun checkCurrentUser() {
        // [START check_current_user]
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }
        // [END check_current_user]
    }

    private fun getUserProfile() {
        // [START get_user_profile]
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }
        // [END get_user_profile]
    }

    private fun getProviderData() {
        // [START get_provider_data]
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            for (profile in it.providerData) {
                // Id of the provider (ex: google.com)
                val providerId = profile.providerId

                // UID specific to the provider
                val uid = profile.uid

                // Name, email address, and profile photo Url
                val name = profile.displayName
                val email = profile.email
                val photoUrl = profile.photoUrl
            }
        }
        // [END get_provider_data]
    }

    private fun sendEmailVerification() {
        // [START send_email_verification]
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }
        // [END send_email_verification]
    }

    private fun sendEmailVerificationWithContinueUrl() {
        // [START send_email_verification_with_continue_url]
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        val url = "http://www.example.com/verify?uid=" + user?.uid
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl(url)
            .setIOSBundleId("com.example.ios")
            // The default for this is populated with the current android package name.
            .setAndroidPackageName("com.example.android", false, null)
            .build()

        user?.sendEmailVerification(actionCodeSettings)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }

        // [END send_email_verification_with_continue_url]
        // [START localize_verification_email]
        auth.setLanguageCode("fr")
        // To apply the default app language instead of explicitly setting it.
        // auth.useAppLanguage()
        // [END localize_verification_email]
    }

    private fun reauthenticate() {
        // [START reauthenticate]
        val user = FirebaseAuth.getInstance().currentUser

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        val credential = EmailAuthProvider
            .getCredential("user@example.com", "password1234")

        // Prompt the user to re-provide their sign-in credentials
        user?.reauthenticate(credential)
            ?.addOnCompleteListener { Log.d(TAG, "User re-authenticated.") }
        // [END reauthenticate]
    }

    private fun buildActionCodeSettings() {
        // [START auth_build_action_code_settings]
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            // URL you want to redirect back to. The domain (www.example.com) for this
            // URL must be whitelisted in the Firebase Console.
            .setUrl("https://www.example.com/finishSignUp?cartId=1234")
            // This must be true
            .setHandleCodeInApp(true)
            .setIOSBundleId("com.example.ios")
            .setAndroidPackageName(
                "com.example.android",
                true, /* installIfNotAvailable */
                "12" /* minimumVersion */)
            .build()
        // [END auth_build_action_code_settings]
    }

    private fun sendSignInLink(email: String, actionCodeSettings: ActionCodeSettings) {
        // [START auth_send_sign_in_link]
        val auth = FirebaseAuth.getInstance()
        auth.sendSignInLinkToEmail(email, actionCodeSettings)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }
        // [END auth_send_sign_in_link]
    }

    private fun verifySignInLink() {
        // [START auth_verify_sign_in_link]
        val auth = FirebaseAuth.getInstance()
        val intent = intent
        val emailLink = intent.data!!.toString()

        // Confirm the link is a sign-in with email link.
        if (auth.isSignInWithEmailLink(emailLink)) {
            // Retrieve this from wherever you stored it
            val email = "someemail@domain.com"

            // The client SDK will parse the code from the link for you.
            auth.signInWithEmailLink(email, emailLink)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Successfully signed in with email link!")
                        val result = task.result
                        // You can access the new user via result.getUser()
                        // Additional user info profile *not* available via:
                        // result.getAdditionalUserInfo().getProfile() == null
                        // You can check if the user is new or existing:
                        // result.getAdditionalUserInfo().isNewUser()
                    } else {
                        Log.e(TAG, "Error signing in with email link", task.exception)
                    }
                }
        }
        // [END auth_verify_sign_in_link]
    }

    private fun linkWithSignInLink(email: String, emailLink: String) {
        val auth = FirebaseAuth.getInstance()

        // [START auth_link_with_link]
        // Construct the email link credential from the current URL.
        val credential = EmailAuthProvider.getCredentialWithLink(email, emailLink)

        // Link the credential to the current user.
        auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Successfully linked emailLink credential!")
                    val result = task.result
                    // You can access the new user via result.getUser()
                    // Additional user info profile *not* available via:
                    // result.getAdditionalUserInfo().getProfile() == null
                    // You can check if the user is new or existing:
                    // result.getAdditionalUserInfo().isNewUser()
                } else {
                    Log.e(TAG, "Error linking emailLink credential", task.exception)
                }
            }
        // [END auth_link_with_link]
    }

    private fun reauthWithLink(email: String, emailLink: String) {
        val auth = FirebaseAuth.getInstance()

        // [START auth_reauth_with_link]
        // Construct the email link credential from the current URL.
        val credential = EmailAuthProvider.getCredentialWithLink(email, emailLink)

        // Re-authenticate the user with this credential.
        auth.currentUser!!.reauthenticateAndRetrieveData(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User is now successfully reauthenticated
                } else {
                    Log.e(TAG, "Error reauthenticating", task.exception)
                }
            }
        // [END auth_reauth_with_link]
    }

    private fun differentiateLink(email: String) {
        val auth = FirebaseAuth.getInstance()

        // [START auth_differentiate_link]
        auth.fetchSignInMethodsForEmail(email)
            .addOnSuccessListener { result ->
                val signInMethods = result.signInMethods
                if (signInMethods!!.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
                    // User can sign in with email/password
                } else if (signInMethods.contains(EmailAuthProvider.EMAIL_LINK_SIGN_IN_METHOD)) {
                    // User can sign in with email/link
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting sign in methods for user", exception)
            }
        // [END auth_differentiate_link]
    }

    private fun getEmailCredentials() {
        val email = ""
        val password = ""
        // [START auth_email_cred]
        val credential = EmailAuthProvider.getCredential(email, password)
        // [END auth_email_cred]
    }

    private fun signOut() {
        // [START auth_sign_out]
        FirebaseAuth.getInstance().signOut()
        // [END auth_sign_out]
    }

}