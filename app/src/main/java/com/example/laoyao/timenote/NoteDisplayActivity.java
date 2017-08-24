package com.example.laoyao.timenote;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.laoyao.timenote.DbMode.NoteRecord;
import com.example.laoyao.timenote.Tools.DateAndTime;
import com.example.laoyao.timenote.Tools.RecordAdapter;
import com.example.laoyao.timenote.network.BingEveryDayImage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class NoteDisplayActivity extends AppCompatActivity {

    private List<NoteRecord> DisplayRecords ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_display);

        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);

        /*
        TextView test = (TextView)findViewById(R.id.testText) ;
        StringBuilder bulider = new StringBuilder() ;
        for(int i = 0 ; i < 1000 ; i ++)
        {
            bulider.append("test    Loayao   Test Laoyao") ;
        }

        test.setText(bulider.toString());

        */

        final ImageView viewImage = (ImageView)findViewById(R.id.collapsing_Image) ;


        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.noteDisplay_RecyclerView) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) ;
        recyclerView.setLayoutManager(linearLayoutManager);

        DisplayRecords = testRecord() ;
        recyclerView.setAdapter(new RecordAdapter(DisplayRecords));


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar , menu);
        return true ;
    }

    public List<NoteRecord> testRecord()
    {
        List<NoteRecord> records = new ArrayList<>() ;

        //正好test
        for(int i = 0 ; i < 5 ; i++)
        {
            NoteRecord record = new NoteRecord(2017 , 8 , 25 ,
                    23 , 0 ,
                    2017 , 8 , 23 ,
                    "This is a test" , "Total Message") ;
            records.add(record);
        }

        //过期test
        for(int i = 0 ; i < 5 ; i++)
        {
            NoteRecord record = new NoteRecord(2017 , 8 , 23 ,
                    6 , 0 ,
                    2017 , 8 , 20 ,
                    "This is a test" , "Total Message") ;
            records.add(record);
        }

        //没到test
        for(int i = 0 ; i < 5 ; i++)
        {
            NoteRecord record = new NoteRecord(2017 , 8 , 30 ,
                    6 , 0 ,
                    2017 , 8 , 25 ,
                    "This is a test" , "Total Message") ;
            records.add(record);
        }

        return records ;
    }
}
