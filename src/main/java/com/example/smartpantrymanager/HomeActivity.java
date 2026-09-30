package com.example.smartpantrymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeActivity extends Activity {

    // Button used to open the Pantry screen
    private Button open_pantry_buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Connect the Pantry button
        open_pantry_buttonView =
                findViewById(R.id.open_pantry_button);

        // Open the Pantry screen
        open_pantry_buttonView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    HomeActivity.this,
                    MainActivity.class
            );

            startActivity(intent);
        });
    }
}