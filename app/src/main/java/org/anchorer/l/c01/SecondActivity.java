package org.anchorer.l.c01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.anchorer.l.R;

/**
 * Created by Anchorer/duruixue on 2015/9/29.
 */
public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        findViewById(R.id.second_textview).setOnClickListener((v) -> startActivity(new Intent(SecondActivity.this, ThirdActivity.class)));
    }
}
