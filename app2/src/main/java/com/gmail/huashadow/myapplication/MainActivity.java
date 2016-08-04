package com.gmail.huashadow.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String APK_PATH = "/sdcard/shuame.apk";
    private Button mBtnInstall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnInstall = (Button) findViewById(R.id.btn_install);
        mBtnInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                install(APK_PATH);
            }
        });
    }

    private void install(String filePath) {
        String command = "pm install " + filePath;
        Log.v(TAG, command);
        try {
            java.lang.Process process = Runtime.getRuntime().exec(command);
            String result = readFromInputStream(process.getInputStream());
            Log.d(TAG, result);
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }
//        ShellUtils.CommandResult commandResult = ShellUtils.execCommand(command.toString(), false, true);
    }

    private String readFromInputStream(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

/*    private void installPackage()
    {
        PackageInstallObserver installObserver = new PackageInstallObserver();
        try {
            String apkPath = sdPath.concat("/").concat(apkName).concat(".apk");
            Class<?> ServiceManager = Class.forName("android.os.ServiceManager");
            Method getService = ServiceManager.getDeclaredMethod("getService", String.class);
            getService.setAccessible(true);
            IBinder packAgeBinder = (IBinder) getService.invoke(null, "package");
            IPackageManager iPm = IPackageManager.Stub.asInterface(packAgeBinder);
            iPm.installPackage(Uri.fromFile(new File(apkPath)), installObserver,INSTALL_REPLACE_EXISTING, new File(apkPath).getPath());

        }catch (Exception e) {
            e.printStackTrace();
            try {
                installObserver.packageInstalled(null, -1);
            } catch (RemoteException ignore) {

            }
        }
    }*/

   /* *//**
     * 安装监听
     *//*
    public class PackageInstallObserver extends IPackageInstallObserver.Stub{

        @Override
        public void packageInstalled(String packageName, int returnCode)throws RemoteException {
            if(returnCode==1) //返回1表示安装成功，否则安装失败
            {
                Toast.makeText(MainActivity.this, "安装成功！", Toast.LENGTH_SHORT).show();
                Log.e("Installed", "packageName="+packageName+",returnCode="+returnCode);
            }else{
                Toast.makeText(MainActivity.this, "安装失败！", Toast.LENGTH_SHORT).show();
            }
        }

    }*/
}
