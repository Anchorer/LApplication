package org.anchorer.l.c01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import l.anchorer.org.lapplication.R;

/**
 * Created by Anchorer/duruixue on 2015/9/29.
 */
public class ThirdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        findViewById(R.id.third_textview).setOnClickListener(view -> startActivity(new Intent(ThirdActivity.this, MainActivity.class)));
    }
}
