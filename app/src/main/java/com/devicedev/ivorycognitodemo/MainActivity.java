package com.devicedev.ivorycognitodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.devicedev.ivorycognitodemo.utils.CognitoSettings;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView nameTextView;

    private Button signoutButton;


    private CognitoSettings cognitoSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTextView = findViewById(R.id.nameTextView);

        signoutButton = findViewById(R.id.signoutButton);

        cognitoSettings = new CognitoSettings(this);


        final CognitoUser cognitoUser = cognitoSettings.getUserPool().getCurrentUser();



        cognitoUser.getDetailsInBackground(new GetDetailsHandler() {
            @Override
            public void onSuccess(CognitoUserDetails cognitoUserDetails) {
                nameTextView.setText("Hello " + cognitoUserDetails.getAttributes().getAttributes().get("name"));
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getApplicationContext(), exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });


        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cognitoUser.signOut();
                startSignInActivity();
            }
        });
    }

    private void startSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);

        startActivity(intent);

        finish();
    }
}
