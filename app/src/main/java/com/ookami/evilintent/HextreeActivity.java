package com.ookami.evilintent;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HextreeActivity extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK){
                    setResult(RESULT_OK,result.getData());
                    finish();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hextree);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent received = getIntent();
        assert received != null;

        String type = received.getStringExtra("type");
        assert type != null;

        if (type.equals("Flag8")){
            Intent payload = new Intent();
            payload.setClassName(
                    "io.hextree.attacksurface",
                    "io.hextree.attacksurface.activities.Flag8Activity"
            );
            launcher.launch(payload);
        }
        else if(type.equals("Flag9")){
            Intent payload = new Intent();
            payload.setClassName(
                    "io.hextree.attacksurface",
                    "io.hextree.attacksurface.activities.Flag9Activity"
            );
            launcher.launch(payload);
        }

    }
}