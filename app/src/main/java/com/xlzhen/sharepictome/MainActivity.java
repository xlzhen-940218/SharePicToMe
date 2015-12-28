package com.xlzhen.sharepictome;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        List<String> pic=SharePicToMeUtils.getListPicPath(this);

        if(pic!=null)
            for(int i=0;i<pic.size();i++){
                ImageView imageView=new ImageView(this);
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                try {
                    imageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(pic.get(i))));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                linearLayout.addView(imageView);
            }
    }
}
