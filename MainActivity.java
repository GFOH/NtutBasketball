package com.example.eric.dialuripicture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView IVotput;
    private Button B1;
    private Button B2;
    EditText ED1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        B1 =(Button)findViewById(R.id.button);
        B2 =(Button)findViewById(R.id.button2);
        ED1=(EditText)findViewById(R.id.editText);




      IVotput =(ImageView)findViewById(R.id.imageView);
    }
    //1.拍照
    //Button 開啟相機
    //onActivityResult  if work之後，用bundle 抓intent 的物件
    //                            再用Bitmap將bundle 轉成 ImageView
    //                            ImageView 設定
    public void Camera(View aa)
    {
        Intent it=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(it,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0&&resultCode==RESULT_OK)
        {
            Bundle bdl =data.getExtras();
            Bitmap btp=(Bitmap) bdl.get("data");
            IVotput.setImageBitmap(btp);

        }


    }
    //2.網址
    //Button設定Uri ，用Intent開啟ACTION_VIEW
    public void Uri(View aa)
    {
        Uri uri= Uri.parse("https://nportal.ntut.edu.tw/index.do?thetime=1526983076086");
        Intent it =new Intent(Intent.ACTION_VIEW,uri);
        startActivity(it);
    }
    //3.撥號
    public void Dial(View aa)
    {
        Uri uri=Uri.parse("tel:0981885952");
        Intent it=new Intent(Intent.ACTION_DIAL,uri);
        startActivity(it);
    }

    //4.直接撥號
}
