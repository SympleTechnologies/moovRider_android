package com.moovapp.riderapp.preLogin;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.moovapp.riderapp.R;
import com.moovapp.riderapp.main.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmationPage extends AppCompatActivity {

    @BindView(R.id.continue_btn)
    FloatingActionButton continueButton;
    @BindView(R.id.user_logged_in)
    TextView userFirstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);

        ButterKnife.bind(this);

        userFirstName.setText("Continue as " + getIntent().getStringExtra("userFirstName") + "");
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
