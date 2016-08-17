package krystleandkori.kittycollector;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by Krystle on 8/13/2016.
 */
public class LoginActivity extends AppCompatActivity implements  View.OnClickListener{
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private  EditText loginEmail;
    private EditText loginPassword;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView mStatusTextView;
    private TextView mDetailTextView;


   @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_login);

       //login edit texts
        loginEmail =  (EditText) findViewById(R.id.field_email);
        loginPassword = (EditText) findViewById(R.id.field_password);
       mStatusTextView = (TextView) findViewById(R.id.status);
       mDetailTextView = (TextView) findViewById(R.id.detail);

       //various sign in buttons. They do something in the onClick method.
     //  findViewById(R.id.facebook_login_button).setOnClickListener(this);
       findViewById(R.id.email_sign_in_button).setOnClickListener(this);
       //findViewById(R.id.create_account_button).setOnClickListener(this);
       mAuth = FirebaseAuth.getInstance();

       mAuthListener = new FirebaseAuth.AuthStateListener() {
           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               FirebaseUser user = firebaseAuth.getCurrentUser();
               if (user != null) {
                   // User is signed in
                   Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
               } else {
                   // User is signed out
                   Log.d(TAG, "onAuthStateChanged:signed_out");
               }
               // [START_EXCLUDE]
               updateUI(user);
               // [END_EXCLUDE]
           }
       };



   }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_create_account_button) {
            createAccount(loginEmail.getText().toString(), loginPassword.getText().toString());
        } else if (i == R.id.email_sign_in_button) {
            signIn(loginEmail.getText().toString(), loginPassword.getText().toString());
        }
        else if (i == R.id.sign_out_button) {
            signOut();
        }
    }
    private void updateUI(FirebaseUser user) {
      //  hideProgressDialog();

        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt, user.getEmail()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText("signed out");
            mDetailTextView.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

      //  showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                    //    hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

       // showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            mStatusTextView.setText(R.string.auth_failed);
                        }
                    //    hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = loginEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            loginEmail.setError("Required.");
            valid = false;
        } else {
            loginEmail.setError(null);
        }

        String password = loginPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            loginPassword.setError("Required.");
            valid = false;
        } else {
            loginPassword.setError(null);
        }

        return valid;
    }

    /*@Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.facebook_login_button) {
            facebookSignIn();
        } else if (i == R.id.email_sign_in_button) {
            emailSignIn();
        } else if (i == R.id.create_account_button) {
            gotoCreateAccount();
        }
    }*/

    private void gotoCreateAccount() {

    }

    private void emailSignIn() {

    }

    private void facebookSignIn() {

    }
}
