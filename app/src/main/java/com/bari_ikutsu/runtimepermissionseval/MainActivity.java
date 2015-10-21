package com.bari_ikutsu.runtimepermissionseval;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 権限のリスト
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 各ボタンについて処理
        for (int i = 0; i < 9; i++) {
            // 各ボタンを取得
            int resId = getResources().getIdentifier("button" + i, "id", getPackageName());
            Button button = (Button) findViewById(resId);

            // ボタンのイベントハンドラ
            final int fIndex = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 権限チェック
                    int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, PERMISSIONS[fIndex]);
                    // 権限がすでに与えられている場合
                    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                        permissionGrantedAction(PERMISSIONS[fIndex]);
                    }
                    // 権限が与えられていなければ要求する
                    else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{PERMISSIONS[fIndex]}, fIndex);
                    }
                }
            });
        }
    }

    /**
     * 権限要求結果のハンドラ
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 権限が与えられている場合、与えられていない場合それぞれの処理を行う
        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionGrantedAction(permissions[0]);
            } else {
                permissionDeniedAction(permissions[0]);
            }
        }
    }

    /**
     * 権限が与えられている場合の処理
     * @param permission
     */
    private void permissionGrantedAction(String permission) {
        // Toastで表示するだけ
        Toast.makeText(this, "権限あり: " + permission, Toast.LENGTH_SHORT).show();
    }

    /**
     * 権限が与えられていない場合の処理
     * @param permission
     */
    private void permissionDeniedAction(String permission) {
        // Toastで表示するだけ
        Toast.makeText(this, "権限なし: " + permission, Toast.LENGTH_SHORT).show();
    }
}
