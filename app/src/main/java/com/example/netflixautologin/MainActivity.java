package com.example.netflixautologin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.netflix.mediaclient");
                startActivity(intent);

                /*if(hasPermission())
                {

                }
                else
                {
                    askPermission();
                }*/
            }
            }
        );

    }
    boolean hasPermission()
    {
        return Settings.canDrawOverlays(this);
    }
    public void askPermission()
    {
        if(!hasPermission())
        {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivityForResult(intent,20);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(hasPermission())
        {
            login.callOnClick();
        }
        else
        {
            Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
        }
    }
}