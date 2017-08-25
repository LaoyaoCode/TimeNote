package com.example.laoyao.timenote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    private boolean IsDisplayToday = true ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_display);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.noteDisplay_RecyclerView) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) ;
        recyclerView.setLayoutManager(linearLayoutManager);

        DisplayRecords = testRecord() ;

        recyclerView.setAdapter(new RecordAdapter(DisplayRecords));


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar , menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.setting:
                break ;
            case R.id.display_all_or_today:
                DisplayExchange(item) ;
                break;
            case R.id.addNewRecord:
                Intent add = new Intent(this , AddNewRecordActivity.class) ;
                startActivity(add);
                break ;
            default:
        }
        return true ;
    }

    private void DisplayExchange(MenuItem item)
    {
        if(IsDisplayToday)
        {
            //显示所有
            IsDisplayToday = false ;
            item.setIcon(R.drawable.menu_icon_minimize) ;
            item.setTitle(R.string.toolBar_all_or_today_today_tittle) ;
        }
        else
        {
            //仅仅显示今天的
            IsDisplayToday = true ;
            item.setIcon(R.drawable.menu_icon_maximize) ;
            item.setTitle(R.string.toolBar_all_or_today_all_tittle) ;
        }
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
