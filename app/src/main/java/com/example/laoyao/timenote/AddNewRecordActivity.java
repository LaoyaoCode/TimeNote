package com.example.laoyao.timenote;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.laoyao.timenote.DbMode.NoteRecord;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView ;

import com.example.laoyao.timenote.Tools.DateAndTime ;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

public class AddNewRecordActivity extends AppCompatActivity
{

    /*
    public int SelectedHour ;
    public int SelectMinute ;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        final MaterialCalendarView calendarView = (MaterialCalendarView)findViewById(R.id.noteCalendarView) ;


        //范围选择
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
        calendarView.state().edit().setFirstDayOfWeek(Calendar.SATURDAY).
                setCalendarDisplayMode(CalendarMode.WEEKS).commit();


        Calendar instance = Calendar.getInstance();
        calendarView.setSelectedDate(instance.getTime());

        final EditText shortTagEditText = (EditText)findViewById(R.id.shortTag_EditText) ;

        Button createButton = (Button) findViewById(R.id.createNewNoteButton) ;
        createButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                List<CalendarDay> selectedDays =  calendarView.getSelectedDates() ;

                //确保选择了日期
                if(selectedDays.size() == 0 )
                {
                    Toast.makeText(AddNewRecordActivity.this , "Please select the date range" , Toast.LENGTH_SHORT).show();
                    return ;
                }

                //确保输入了tag
                String shortTag = shortTagEditText.getText().toString() ;
                if(shortTag == null || shortTag.isEmpty())
                {
                    Toast.makeText(AddNewRecordActivity.this , "Please enter the tag" , Toast.LENGTH_SHORT).show();
                    return ;
                }

                //获取提醒日期和最后日期
                CalendarDay warningDate = selectedDays.get(0) ;
                CalendarDay deadDate = selectedDays.get(selectedDays.size() - 1) ;


                NoteRecord newRecord = new NoteRecord(deadDate.getYear() , deadDate.getMonth() + 1 , deadDate.getDay() ,
                        //SelectedHour , SelectMinute ,
                        warningDate.getYear() , warningDate.getMonth() + 1 , warningDate.getDay() , shortTag) ;

                newRecord.save() ;

                Intent back = new Intent(AddNewRecordActivity.this , NoteDisplayActivity.class) ;
                startActivity(back);
                AddNewRecordActivity.this.finish();
            }
        });
    }
}
