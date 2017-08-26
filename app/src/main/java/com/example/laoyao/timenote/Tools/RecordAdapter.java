package com.example.laoyao.timenote.Tools;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.laoyao.timenote.DbMode.NoteRecord ;
import com.example.laoyao.timenote.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Laoyao on 2017/8/24.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RViewHolder>
{
    private List<NoteRecord> DisplayNoteRecords ;
    private Context GiveContext ;

    static class RViewHolder extends RecyclerView.ViewHolder
    {
        public View RootView ;
        public TextView ShortTag ;
        public TextView DeadTime ;
        public TextView WarningTime ;
        public ImageView MessageHelpImage ;
        public ImageButton DeleteButton ;

        public RViewHolder(View view)
        {
            super(view);

            RootView = view ;
            ShortTag = (TextView)view.findViewById(R.id.short_tag_TextView) ;
            DeadTime = (TextView)view.findViewById(R.id.dead_time_TextView) ;
            WarningTime = (TextView)view.findViewById(R.id.warning_time_TextView) ;
            MessageHelpImage = (ImageView)view.findViewById(R.id.is_today_and_dead_Image) ;
            DeleteButton = (ImageButton)view.findViewById(R.id.delete_record_button) ;
        }
    }

    public RecordAdapter(List<NoteRecord> records , Context context )
    {
        DisplayNoteRecords = records ;
        GiveContext = context ;

    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item , parent , false) ;
        RViewHolder holder = new RViewHolder(view) ;
        return holder ;
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position)
    {
        final NoteRecord record = DisplayNoteRecords.get(position) ;
        String wDate = record.getWYear() + "-" +String.format("%02d" , record.getWMonth())
                + "-" + String.format("%02d" ,record.getWDay()) ;
        String dDate = record.getDYear() + "-" + String.format("%02d" ,record.getDMonth())
                + "-" + String.format("%02d" ,record.getDDay());
        //String dTime = record.getDHour() + ":" + String.format("%02d" ,record.getDMinute()) ;

        //设置最后时间
        holder.DeadTime.setText( dDate /*+ "   " + dTime*/);

        //设置开始提醒时间
        holder.WarningTime.setText(wDate);

        //设置简短标签内容
        holder.ShortTag.setText(record.getShortTag());

        DateAndTime.CompareResult wDateResult = DateAndTime.StructedDate.Compare(wDate , DateAndTime.GetDateString() );
        DateAndTime.CompareResult dDateResult = DateAndTime.StructedDate.Compare(dDate , DateAndTime.GetDateString() );

        //已经到了提醒日期
        if(wDateResult == DateAndTime.CompareResult.Smaller || wDateResult == DateAndTime.CompareResult.Equal)
        {
            //已经到了提醒期间，但是没有到终结日期
            if(dDateResult == DateAndTime.CompareResult.Bigger)
            {
                holder.MessageHelpImage.setImageResource(R.drawable.little_star);
            }
            //已经到了提醒期间，正好或者超过了终结日期
            else
            {
                holder.MessageHelpImage.setImageResource(R.drawable.little_star_pink);
            }
        }
        //还没有到提醒日期
        else
        {
            holder.MessageHelpImage.setImageResource(R.drawable.little_star_gray);
        }

        holder.DeleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(GiveContext) ;

                builder.setTitle("Warning") ;
                builder.setMessage("Sure to delete this record ?" + "\n\n" + "Record Tag : " +
                        record.getShortTag().substring(0 , record.getShortTag().length() / 2) + "..." ) ;
                builder.setCancelable(true) ;
                builder.setIcon(R.drawable.warning_cricle) ;

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        record.delete() ;
                        int position = DisplayNoteRecords.indexOf(record) ;
                        DisplayNoteRecords.remove(position) ;
                        notifyItemRemoved(position);
                    }
                }) ;

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                }) ;

                builder.show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return DisplayNoteRecords.size();
    }
}
