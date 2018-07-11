package com.example.simhyobin.noti;


import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;


import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageCardFragment extends RecyclerView.Adapter<MessageCardFragment.ItemViewHolder> {
    ArrayList<MessageItem> mItems;
    Context context;

    public MessageCardFragment(Context context, ArrayList<MessageItem> items){
        this.context = context;
        this.mItems = items;
    }
    // 새로운 뷰 홀더 생성
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }


    // View 의 내용을 해당 포지션의 데이터로 바꿉니다.
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.content.setText(mItems.get(position).getContent());
        holder.username.setText(mItems.get(position).getUsername());
        holder.noti_date.setText(mItems.get(position).getNoti_date());
        holder.rec_date.setText(mItems.get(position).getRec_date());
    }

    // 데이터 셋의 크기를 리턴해줍니다.
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    // 커스텀 뷰홀더
// item layout 에 존재하는 위젯들을 바인딩합니다.
    class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView content;
        public TextView username;
        public TextView rec_date;
        public TextView noti_date;
        public ItemViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
            username = (TextView) itemView.findViewById(R.id.username);
            rec_date = (TextView) itemView.findViewById(R.id.rec_date);
            noti_date = (TextView) itemView.findViewById(R.id.noti_date);
        }
    }
}