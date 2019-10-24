package com.devicedev.ivorycognitodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.devicedev.ivorycognitodemo.utils.CognitoSettings;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = SignInActivity.class.getSimpleName();

    private EditText emailEditText;

    private EditText passwordEditText;

    private Button loginButton;

    private Button signUpButton;

    private CognitoSettings cognitoSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);

        emailEditText.setText("devicedem@gmail.com");
        passwordEditText.setText("sameRoad$ 123");

        cognitoSettings = new CognitoSettings(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailEditText.getText().toString();
                final String password = passwordEditText.getText().toString();


                AuthenticationHandler handler = new AuthenticationHandler() {
                    @Override
                    public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                        Toast.makeText(getApplicationContext(), "Successful signin", Toast.LENGTH_SHORT).show();
                        startMainActivity();
                    }

                    @Override
                    public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String UserId) {
                        AuthenticationDetails details = new AuthenticationDetails(email, password, null);

                        authenticationContinuation.setAuthenticationDetails(details);

                        authenticationContinuation.continueTask();
                    }

                    @Override
                    public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

                    }

                    @Override
                    public void authenticationChallenge(ChallengeContinuation continuation) {

                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                };
                CognitoUser cognitoUser = cognitoSettings.getUserPool().getUser(email);

                cognitoUser.getSessionInBackground(handler);

            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUpActivity();
            }
        });
    }

    private void startSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);

        startActivity(intent);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        finish();

    }
}
