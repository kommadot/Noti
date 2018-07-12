package com.example.simhyobin.noti;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link fragment_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_1 extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    private DBHelper dbhelper;

    // newInstance constructor for creating fragment with arguments
    public static fragment_1 newInstance(int page, String title) {
        fragment_1 fragmentFirst = new fragment_1();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_1, container, false);

        Listing_User((LinearLayout)view.findViewById(R.id.user_common), (LinearLayout)view.findViewById(R.id.user_favorite));
        return view;
    }

    public void Listing_User(LinearLayout list_com, LinearLayout list_fav){

        dbhelper = new DBHelper(getActivity(), "data", null, 1);
        ArrayList<String[]> data = dbhelper.ReadFriendsData();

        Iterator iterator =  data.iterator();
        while(iterator.hasNext()){
            String[] temp = (String[])iterator.next();
            if(temp[2].equals("0")){
                //일반 친구
                Create_Usertap(list_com, temp[0], temp[1], temp[3]);
            }else if(temp[2].equals("1")){
                //즐겨찾기 등록 친구
                Create_Usertap(list_fav, temp[0], temp[1], temp[3]);
            }else{
            }
        }
    }
    public void Create_Usertap(LinearLayout parent, String user_id, String name, String cnt){
        RelativeLayout head = new RelativeLayout(getActivity());
        TextView name_tap = new TextView(getActivity());
        TextView cnt_tap = new TextView(getActivity());

        LinearLayout.LayoutParams params1;
        RelativeLayout.LayoutParams params2;

        params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getDP(50));
        head.setPadding(getDP(10),getDP(5), getDP(10), getDP(5));
        head.setLayoutParams(params1);

        params2 = new RelativeLayout.LayoutParams(getDP(150), ViewGroup.LayoutParams.MATCH_PARENT);
        params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        name_tap.setTextSize(getDP(10));
        name_tap.setGravity(Gravity.CENTER_VERTICAL);
        name_tap.setLayoutParams(params2);
        name_tap.setTextColor(Color.BLACK);
        name_tap.setText(name);


        params2 = new RelativeLayout.LayoutParams(getDP(40), ViewGroup.LayoutParams.MATCH_PARENT);
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        cnt_tap.setGravity(Gravity.CENTER);
        cnt_tap.setLayoutParams(params2);
        cnt_tap.setText(cnt);

        head.addView(name_tap);
        head.addView(cnt_tap);
        String[] dat = {user_id, name};
        head.setTag(dat);

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] res = (String [])view.getTag();
                final String user_id = String.valueOf(res[0]);
                String user_name = String.valueOf(res[1]);
                CharSequence[] select_item = {"메시지 보내기", "삭제", "닫기"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle(user_name)
                        .setItems(select_item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("button", String.valueOf(i));
                                if(i==0){
                                    Intent intent = new Intent(getActivity(), frag1_sendmsg.class);
                                    intent.putExtra("userid", String.valueOf(user_id));
                                    startActivity(intent);

                                }else if(i==1){

                                }else{
                                    dialogInterface.dismiss();
                                }
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        parent.addView(head);
    }
    public int getDP(int num){
        int dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        return dp;
    }
}