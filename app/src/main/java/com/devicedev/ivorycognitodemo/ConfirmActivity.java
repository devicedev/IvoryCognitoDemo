package com.devicedev.ivorycognitodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;

public class ConfirmActivity extends AppCompatActivity {

    private EditText confirmationEditText;

    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        confirmationEditText = findViewById(R.id.confirmationEditText);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = confirmationEditText.getText().toString();

                Intent intent = new Intent();

                intent.putExtra(SignUpActivity.CONFIRMATION_EXTRA, code);

                setResult(RESULT_OK, intent);

                finish();
            }
        });

    }
}
