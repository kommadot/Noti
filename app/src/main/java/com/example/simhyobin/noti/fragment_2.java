package com.example.simhyobin.noti;


import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;


import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class fragment_2 extends Fragment {

    private RecyclerView recyclerView;
    private MessageCardFragment adapter;
    private ArrayList<MessageItem> list = new ArrayList<>();
    DBHelper dbhelper;
    public static fragment_2 newInstance(int page, String title) {
        fragment_2 fragmentFirst = new fragment_2();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_fragment_2, container, false);
        Spinner sortSpinner = (Spinner)view.findViewById(R.id.sortSpinner);
        ArrayAdapter sortAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.sortArray, android.R.layout.simple_spinner_item);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
        );
        sortSpinner.setAdapter(sortAdapter);
        //db
        dbhelper = new DBHelper(getActivity(), "data", null, 1);
        //dbhelper.insert();
        //dbhelper.test_group();
        //dbhelper.test_user();
        ArrayList<String[]> data = dbhelper.ReadReceiveMessage();
        list = MessageItem.createContactsList(data);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        setList();
        sortSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id){
                switch(position){
                    case 0:
                        sortByrec(list);
                        break;
                    case 1:
                        sortBynoti(list);
                        break;
                    default:
                        break;
                }
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        return view;
    }
    public void sortBynoti(ArrayList<MessageItem> sortlist){
        Collections.sort(sortlist, new Comparator<MessageItem>(){
            public int compare(MessageItem o1,MessageItem o2){
                if(o1.getNoti_date()>o2.getNoti_date()){
                    return -1;
                }
                else if(o1.getNoti_date()<o2.getNoti_date()){
                    return 1;

                }
                else return 0;
            }
        });
        list=sortlist;
        adapter.notifyItemRangeChanged(0,3);
    }
    public void sortByrec(ArrayList<MessageItem> sortlist){
        Collections.sort(sortlist, new Comparator<MessageItem>(){
            public int compare(MessageItem o1,MessageItem o2){
                if(o1.getRec_date()>o2.getRec_date()){
                    return -1;
                }
                else if(o1.getRec_date()<o2.getRec_date()){
                    return 1;

                }
                else return 0;
            }
        });
        list = sortlist;
        adapter.notifyItemRangeChanged(0,3);
    }
    public void setList(){

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MessageCardFragment(getActivity(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SimpleDateFormat transFormat = new SimpleDateFormat("MM-dd HH:mm");
                Log.d("komad",String.valueOf(position));
                Intent intent = new Intent(getActivity(), detail_msg.class);
                intent.putExtra("content",list.get(position).getContent());
                intent.putExtra("username",list.get(position).getUsername());
                intent.putExtra("title",list.get(position).getTitle());
                java.util.Date Notitime = new java.util.Date((long)list.get(position).getNoti_date()*1000);
                java.util.Date Rectime = new java.util.Date((long)list.get(position).getRec_date()*1000);
                String toNoti = transFormat.format(Notitime);
                String toRec = transFormat.format(Rectime);
                intent.putExtra("notidate",toNoti);
                intent.putExtra("recdate",toRec);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                SimpleDateFormat transFormat = new SimpleDateFormat("MM-dd HH:mm");
                Log.d("komad",String.valueOf(position));
                Intent intent = new Intent(getActivity(), detail_msg.class);
                intent.putExtra("content",list.get(position).getContent());
                intent.putExtra("username",list.get(position).getUsername());
                intent.putExtra("title",list.get(position).getTitle());
                java.util.Date Notitime = new java.util.Date((long)list.get(position).getNoti_date()*1000);
                java.util.Date Rectime = new java.util.Date((long)list.get(position).getRec_date()*1000);
                String toNoti = transFormat.format(Notitime);
                String toRec = transFormat.format(Rectime);
                intent.putExtra("notidate",toNoti);
                intent.putExtra("recdate",toRec);
                startActivity(intent);
            }
        }));

    }
}
class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

        public void onLongItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mListener != null) {
                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

    @Override
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
}
