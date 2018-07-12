package com.example.simhyobin.noti;


import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;


import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

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
        dbhelper.insert();
        final ArrayList<MessageItem> list;
        ArrayList<String[]> data = dbhelper.ReadReceiveMessage();
        list=MessageItem.createContactsList(data);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter = new MessageCardFragment(getActivity(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        sortSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id){
                switch(position){
                    case 0:
                        sortBynoti(list);
                        break;
                    case 1:
                        sortByrec(list);
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
        Arrays.sort(sortlist, new Comparator<MessageItem>(){
            public int compare(MessageItem o1,MessageItem o2){
                if(o1.getNoti_date()>o2.getNoti_date()){
                    return 1;
                }
            }
        });

    }
    public void sortByrec(ArrayList<MessageItem> sortlist){

    }
}
