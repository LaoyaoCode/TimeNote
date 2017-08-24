package com.example.laoyao.timenote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.laoyao.timenote.network.BingEveryDayImage;

public class NoteDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_display);

        TextView test = (TextView)findViewById(R.id.testText) ;
        StringBuilder bulider = new StringBuilder() ;

        for(int i = 0 ; i < 1000 ; i ++)
        {
            bulider.append("test    Loayao   Test Laoyao") ;
        }

        test.setText(bulider.toString());

        final ImageView viewImage = (ImageView)findViewById(R.id.collapsing_Image) ;

        BingEveryDayImage bingImage = new BingEveryDayImage(this) ;
        bingImage.GetImage(new BingEveryDayImage.IBingImage(){
            @Override
            public void Begin (BingEveryDayImage.BeginState state, String imagePath)
            {
                switch (state)
                {
                    case HadImage:
                        Glide.with(NoteDisplayActivity.this).load(imagePath).into(viewImage);
                        break;
                    case NotHave:
                        break;
                }
            }

            @Override
            public void DownFinished (final BingEveryDayImage.GettingResult state, final String imagePath)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        switch (state)
                        {
                            case NoNetConnect:
                                Toast.makeText(NoteDisplayActivity.this ,
                                        "No Net Connected" , Toast.LENGTH_SHORT).show();
                                break;
                            case Success :
                                Glide.with(NoteDisplayActivity.this).load(imagePath).into(viewImage);
                                break;
                            case Failed :
                                Toast.makeText(NoteDisplayActivity.this ,
                                        "UpDate Bing Image Failed" , Toast.LENGTH_SHORT).show();
                                break;
                            case AlreadyDownlaoded :
                                break;
                        }
                    }
                });
            }
        });
    }
}
