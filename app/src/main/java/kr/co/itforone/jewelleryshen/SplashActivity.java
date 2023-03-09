package kr.co.itforone.jewelleryshen;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class SplashActivity extends AppCompatActivity {
    Context context, mainContext;
    Button btnConfirm;
    LinearLayout layout1;
    LinearLayout layout2;
    RelativeLayout layout3;
    ActivityManager am;
    String isInitCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this.getBaseContext();
        mainContext = ((MainActivity) MainActivity.mainContext);
        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (RelativeLayout) findViewById(R.id.layout3);
        am = ActivityManager.getInstance();

        isInitCheck = getIntent().getStringExtra("isInit");
        Log.d("로그", "splash onCreate:" + isInitCheck);

        //checkPermissions();
        //initView();

        btnConfirm = (Button) findViewById(R.id.btnSplash);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });

        if (isInitCheck.equals("F")) {
            checkPermissions();
            layout3.setVisibility(View.VISIBLE);
        } else {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
        }
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT > 23) {
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {}
            TedPermission.with(context)
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("앱 사용을 위해 권한 설정이 필요합니다.\n [설정] > [권한] 에서 사용으로 변경해 주세요.")
                    .setGotoSettingButton(true)
                    .setPermissions(
                            Manifest.permission.CALL_PHONE,
                            //Manifest.permission.READ_PHONE_STATE,
                            //Manifest.permission.READ_PHONE_NUMBERS, // 안드로이드11부터 변경
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    )
                    .check();
        } else {
            initView();
        }
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            initView(); // 권한이 승인되었을 때 실행할 함수
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Log.d("로그 //A_Main//", "[onPermissionGranted() 메소드]" + " [" + "실행 : 전체 퍼미션 부여 확인 실패 - " + String.valueOf(deniedPermissions.toString()) + "]");
            //Log.d("로그 checkSelfPermission :", String.valueOf(ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED));


            Toast.makeText(context, "권한 요청에 동의 해주셔야 이용 가능합니다. [설정] > [권한] 에서 사용으로 변경해 주세요.", Toast.LENGTH_SHORT).show();
            // 앱종료
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    };

    // 핸들러로 이용해서 1초간 머물고 메인으로 이동
    private void initView() {
        Log.d("로그", "initView()");
        int delay = 1000;

        // 권한설정화면 제거
        if (!isInitCheck.equals("F")) {
            ((MainActivity) MainActivity.mainContext).pEditor.putString("isInitApp", "F");
            ((MainActivity) MainActivity.mainContext).pEditor.apply();
            delay = 0;
        }

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, delay);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        am.finishAllActivity();
    }
}