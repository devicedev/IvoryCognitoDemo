package com.devicedev.ivorycognitodemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.devicedev.ivorycognitodemo.utils.CognitoSettings;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();

    public static final int REQUEST_CONFIRMATION = 1;

    public static final String CONFIRMATION_EXTRA = "CONFIRMATION_EXTRA";

    private EditText nameEditText;

    private EditText emailEditText;

    private EditText passwordEditText;

    private Button signUpButton;

    private Button signInButton;


    private CognitoSettings cognitoSettings;

    private CognitoUser cognitoUser;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        signInButton = findViewById(R.id.signInButton);

        nameEditText.setText("Radu");
        emailEditText.setText("devicedem@gmail.com");
        passwordEditText.setText("sameRoad$ 123");

        cognitoSettings = new CognitoSettings(this);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();

                CognitoUserAttributes attributes = new CognitoUserAttributes();

                attributes.addAttribute("name", name);

                attributes.addAttribute("email", email);

                SignUpHandler handler = new SignUpHandler() {
                    @Override
                    public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                        cognitoUser = user;
                        if (!signUpConfirmationState) {
                            startConfirmActivity();

                        } else {
                            startSignInActivity();

                        }
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                };

                cognitoSettings.getUserPool().signUpInBackground(email, password, attributes, null, handler);

            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startSignInActivity() {

        Intent intent = new Intent(this, SignInActivity.class);

        startActivity(intent);

        finish();
    }

    private void startConfirmActivity() {
        Intent intent = new Intent(this, ConfirmActivity.class);

        startActivityForResult(intent, REQUEST_CONFIRMATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == REQUEST_CONFIRMATION) {
            if (cognitoUser == null)
                return;
            cognitoUser.confirmSignUpInBackground(data.getStringExtra(CONFIRMATION_EXTRA), false, new GenericHandler() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(), "Successful confirmation", Toast.LENGTH_SHORT).show();
                    startSignInActivity();
                }

                @Override
                public void onFailure(Exception exception) {
                    startConfirmActivity();
                    Toast.makeText(getApplicationContext(), exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                }
            });


        }
    }
}
