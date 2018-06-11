package com.example.eric.gpsspeechtotext;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {
    LocationManager LM;
    TextView tv_Location;
    private long min_longTime = 0;
    private float min_floatDistance = 0 ;
    private TextView tv_STT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_Location = (TextView) findViewById(R.id.tv_location);
        LM = (LocationManager) getSystemService(LOCATION_SERVICE);//LM拿到系統服務"位置服務"
        tv_STT =(TextView)findViewById(R.id.tv_STT);
    }
    //Button 副程式(開啟定位服務)
    public void Btv(View aa) {
        Intent it =new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivities(it);

    }

    private void startActivities(Intent it) {
    }
    //當跳出視窗時，停止更新在this
    @Override
    protected void onPause() {
        super.onPause();
        LM.removeUpdates(this);
    }

    @Override
    //當回到視窗，If LM找到最好提供者，就tv_Location="Location"  and   定位超過時間或超過距離就會更新GPS資訊
    //                     ,If LM沒找到，就tv_Location="falled"
    protected void onResume() {
        super.onResume();
        String best = LM.getBestProvider(new Criteria(), true);
        if (best != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            tv_Location.setText("Location........");
            LM.requestLocationUpdates(best, min_longTime, min_floatDistance, this);
        }else
        {
            tv_Location.setText("falled");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //Location 改變就傳送GPS訊息
        String str = "定位來源"+location.getProvider();
        str+=String.format("經度:%.6f\n緯度:%.6f\n高度:%.2f",location.getLatitude(),location.getLongitude(),location.getAltitude());
        tv_Location.setText(str);
            }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void SpeakButton(View aa)
    {
        Intent it=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        try
        {
            startActivityForResult(it,0);
        }
        catch (ActivityNotFoundException a)
        {
            Toast.makeText(this,"你的裝置沒有支援語音辨識",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0&&resultCode==RESULT_OK&&data!=null)
        {
            List<String> Result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            tv_STT.setText(Result.get(0));
        }
    }
    //(          Button ==>副程式(用Intent 開啟 google speak)
    //intent語音辨識設定語系
    ///Activityforresult


}
