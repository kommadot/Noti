package com.example.simhyobin.noti;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
    private int idx_select = 0;
    private boolean idx_select_grp = false;
    private boolean idx_optionbar = false;
    private boolean idx_optionbar_grp = false;
    private ArrayList<View> list_selectedview = new ArrayList<View>();

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
        Listing_Group((LinearLayout)view.findViewById(R.id.user_group));

        LinearLayout btn_msg = (LinearLayout)view.findViewById(R.id.btn_sendmsg);
        LinearLayout btn_addfav = (LinearLayout)view.findViewById(R.id.btn_addfav);
        LinearLayout btn_rmuser = (LinearLayout)view.findViewById(R.id.btn_rmuser);
        LinearLayout btn_unselect = (LinearLayout)view.findViewById(R.id.btn_unselect);
        LinearLayout btn_group = (LinearLayout)view.findViewById(R.id.btn_grping);

        LinearLayout btn_grp_info = (LinearLayout)view.findViewById(R.id.btn_info_grp);
        LinearLayout btn_grp_msg = (LinearLayout)view.findViewById(R.id.btn_message_grp);
        LinearLayout btn_grp_modify = (LinearLayout)view.findViewById(R.id.btn_modify_grp);
        LinearLayout btn_grp_rm = (LinearLayout)view.findViewById(R.id.btn_rm_grp);
        LinearLayout btn_grp_unselect = (LinearLayout)view.findViewById(R.id.btn_unselect_grp);



        btn_grp_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_grp_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout head_layout = (LinearLayout)getActivity().findViewById(R.id.user_group);
                String[] tag_data = new String[3];
                for(int i=0; i<head_layout.getChildCount(); i++){
                    RelativeLayout target = (RelativeLayout) head_layout.getChildAt(i);
                    if(target.isSelected()){
                        tag_data = (String[])target.getTag();
                    }
                }
                Intent intent = new Intent(getActivity(), activity_sendmsg.class);
                intent.putExtra("idx", 0);
                intent.putExtra("data",tag_data);
                intent.putExtra("grp_name", tag_data[1]);
                startActivity(intent);

            }
        });
        btn_grp_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LinearLayout head_layout = (LinearLayout)getActivity().findViewById(R.id.user_group);
                String grp_num = new String();
                dbhelper = new DBHelper(getActivity(), "data", null, 1);
                String[] tag_data = new String[3];
                for(int i=0; i<head_layout.getChildCount(); i++){
                    RelativeLayout target = (RelativeLayout) head_layout.getChildAt(i);
                    if(target.isSelected()){
                        tag_data = (String[])target.getTag();
                        grp_num = tag_data[0];
                    }
                }

                ArrayList<String[]> send_data = dbhelper.getUserListfromGRPNUM(grp_num);
                String grp_name = dbhelper.getGRPNAME(grp_num);
                Intent intent = new Intent(getActivity(), activity_grp.class);
                intent.putExtra("data",send_data);
                intent.putExtra("idx", 1);
                intent.putExtra("grp_name", grp_name);
                intent.putExtra("grp_num", grp_num);
                startActivityForResult(intent, 1);
            }
        });
        btn_grp_rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout head_layout = (LinearLayout)getActivity().findViewById(R.id.user_group);
                String[] tag_data = new String[3];
                for(int i=0; i<head_layout.getChildCount(); i++){
                    RelativeLayout target = (RelativeLayout) head_layout.getChildAt(i);
                    if(target.isSelected()){
                        tag_data = (String[])target.getTag();
                        dbhelper = new DBHelper(getActivity(), "data", null, 1);
                        dbhelper.rm_group(tag_data[0]);
                    }
                }
                refresh();
            }
        });
        btn_grp_unselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout head_layout = (LinearLayout)getActivity().findViewById(R.id.user_group);
                for(int i=0; i<head_layout.getChildCount(); i++){
                    RelativeLayout target = (RelativeLayout) head_layout.getChildAt(i);
                    if(target.isSelected()){
                        target.setSelected(false);
                        target.setBackground(getResources().getDrawable(R.drawable.border, null));
                    }
                }

                Animation bottomDown = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
                LinearLayout hiddenPanel = (LinearLayout)getActivity().findViewById(R.id.hidden_panel_grp);
                hiddenPanel.startAnimation(bottomDown);
                hiddenPanel.setVisibility(View.GONE);

                Animation bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
                FloatingActionButton btn_adduser = (FloatingActionButton)getActivity().findViewById(R.id.fab);
                btn_adduser.startAnimation(bottomUp);
                btn_adduser.setVisibility(View.VISIBLE);
                idx_select_grp = false;
                idx_optionbar_grp = false;
            }
        });




        FloatingActionButton btn_adduser = (FloatingActionButton)view.findViewById(R.id.fab);
        btn_adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_adduser.class);
                startActivityForResult(intent, 3);
            }
        });

        btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                메시지 전송 액티비티로 list_selectedview를 넘겨줌
                 */
                ArrayList<String[]> send_data = new ArrayList<String[]>();
                Iterator iterator = list_selectedview.iterator();
                while(iterator.hasNext()){
                    String[] tempdata = new String[2];
                    RelativeLayout target = (RelativeLayout)iterator.next();
                    tempdata = (String[])target.getTag();
                    send_data.add(tempdata);
                }
                Intent intent = new Intent(getActivity(), activity_sendmsg.class);
                intent.putExtra("idx", 1);
                intent.putExtra("data",send_data);
                startActivity(intent);
            }
        });

        btn_addfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelper = new DBHelper(getActivity(), "data", null, 1);
                Iterator iterator = list_selectedview.iterator();
                while(iterator.hasNext()){
                    RelativeLayout target = (RelativeLayout)iterator.next();
                    String user_id = ((String[])target.getTag())[0];
                    dbhelper.fav_user(user_id);
                }
                Animation bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
                FloatingActionButton btn_adduser = (FloatingActionButton)getActivity().findViewById(R.id.fab);
                btn_adduser.startAnimation(bottomUp);
                btn_adduser.setVisibility(View.VISIBLE);
                idx_select = 0;
                idx_optionbar = false;
                refresh();
            }
        });

        btn_rmuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelper = new DBHelper(getActivity(), "data", null, 1);
                Iterator iterator = list_selectedview.iterator();
                while(iterator.hasNext()){
                    RelativeLayout target = (RelativeLayout)iterator.next();
                    String user_id = ((String[])target.getTag())[0];
                    dbhelper.rm_user(user_id);
                }
                Animation bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
                FloatingActionButton btn_adduser = (FloatingActionButton)getActivity().findViewById(R.id.fab);
                btn_adduser.startAnimation(bottomUp);
                btn_adduser.setVisibility(View.VISIBLE);
                idx_select = 0;
                idx_optionbar = false;
                refresh();
            }
        });

        btn_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                그루핑 액티비티로 list_selectedview
                 */
                ArrayList<String[]> send_data = new ArrayList<String[]>();
                Iterator iterator = list_selectedview.iterator();
                while(iterator.hasNext()){
                    String[] tempdata = new String[2];
                    RelativeLayout target = (RelativeLayout)iterator.next();
                    tempdata = (String[])target.getTag();
                    send_data.add(tempdata);
                }
                Intent intent = new Intent(getActivity(), activity_grp.class);
                intent.putExtra("data",send_data);
                intent.putExtra("idx", 0);
                startActivityForResult(intent, 2);
            }
        });

        btn_unselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Iterator iterator = list_selectedview.iterator();
                while(iterator.hasNext()){
                    RelativeLayout target = (RelativeLayout)iterator.next();
                    target.setSelected(false);
                    target.setBackground(getResources().getDrawable(R.drawable.border, null));
                }
                Animation bottomDown = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
                LinearLayout hiddenPanel = (LinearLayout)getActivity().findViewById(R.id.hidden_panel);
                hiddenPanel.startAnimation(bottomDown);
                hiddenPanel.setVisibility(View.GONE);

                Animation bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
                FloatingActionButton btn_adduser = (FloatingActionButton)getActivity().findViewById(R.id.fab);
                btn_adduser.startAnimation(bottomUp);
                btn_adduser.setVisibility(View.VISIBLE);
                idx_select = 0;
                idx_optionbar = false;
            }
        });
        return view;
    }
    public void Listing_Group(LinearLayout list_group){
        dbhelper = new DBHelper(getActivity(), "data", null, 1);
        //dbhelper.test_group();
        ArrayList<String[]> data = dbhelper.ReadGroupData();

        Iterator iterator =  data.iterator();
        while(iterator.hasNext()){
            String[] temp = (String[])iterator.next();
            Create_Grouptap(list_group, temp[0], temp[1], temp[2], temp[3]);
        }
    }
    public void Create_Grouptap(LinearLayout parent, String group_num, String group_name, String mem_cnt, String msg_cnt){
        final RelativeLayout head = new RelativeLayout(getActivity());
        TextView name_tap = new TextView(getActivity());
        TextView cnt_tap = new TextView(getActivity());

        LinearLayout.LayoutParams params1;
        RelativeLayout.LayoutParams params2;

        params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getDP(50));
        head.setPadding(getDP(10),getDP(5), getDP(10), getDP(5));
        head.setBackground(getResources().getDrawable(R.drawable.border, null));
        head.setLayoutParams(params1);

        params2 = new RelativeLayout.LayoutParams(getDP(150), ViewGroup.LayoutParams.MATCH_PARENT);
        params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        name_tap.setTextSize(getDP(10));
        name_tap.setGravity(Gravity.CENTER_VERTICAL);
        name_tap.setLayoutParams(params2);
        name_tap.setTextColor(Color.BLACK);
        name_tap.setText(group_name);


        params2 = new RelativeLayout.LayoutParams(getDP(40), ViewGroup.LayoutParams.MATCH_PARENT);
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        cnt_tap.setGravity(Gravity.CENTER);
        cnt_tap.setLayoutParams(params2);
        cnt_tap.setText(msg_cnt);

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idx_optionbar == true){

                    list_selectedview.clear();

                    LinearLayout head_layout = (LinearLayout)getActivity().findViewById(R.id.user_favorite);
                    for(int i=0; i<head_layout.getChildCount(); i++){
                        RelativeLayout target = (RelativeLayout) head_layout.getChildAt(i);
                        if(target.isSelected()){
                            target.setSelected(false);
                            target.setBackground(getResources().getDrawable(R.drawable.border, null));
                        }
                    }

                    head_layout = (LinearLayout)getActivity().findViewById(R.id.user_common);
                    for(int i=0; i<head_layout.getChildCount(); i++){
                        RelativeLayout target = (RelativeLayout) head_layout.getChildAt(i);
                        if(target.isSelected()){
                            target.setSelected(false);
                            target.setBackground(getResources().getDrawable(R.drawable.border, null));
                        }
                    }
                    Animation bottomDown = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
                    LinearLayout hiddenPanel = (LinearLayout)getActivity().findViewById(R.id.hidden_panel);
                    hiddenPanel.startAnimation(bottomDown);
                    hiddenPanel.setVisibility(View.GONE);

                    idx_select = 0;
                    idx_optionbar = false;
                }

                if(view.isSelected()){
                    idx_select_grp = false;
                    view.setSelected(false);
                    view.setBackground(getResources().getDrawable(R.drawable.border, null));

                    Animation bottomDown = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
                    LinearLayout hiddenPanel = (LinearLayout)getActivity().findViewById(R.id.hidden_panel_grp);
                    hiddenPanel.startAnimation(bottomDown);
                    hiddenPanel.setVisibility(View.GONE);

                    Animation bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
                    FloatingActionButton btn_adduser = (FloatingActionButton)getActivity().findViewById(R.id.fab);
                    if(!(btn_adduser.isSelected())){
                        btn_adduser.startAnimation(bottomUp);
                        btn_adduser.setVisibility(View.VISIBLE);
                        btn_adduser.setSelected(true);
                    }


                    idx_optionbar_grp = false;

                }else{
                    idx_select_grp = true;
                    view.setSelected(true);
                    view.setBackgroundColor(getResources().getColor(R.color.primary_light, null));

                    Animation bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
                    LinearLayout hiddenPanel = (LinearLayout)getActivity().findViewById(R.id.hidden_panel_grp);
                    hiddenPanel.startAnimation(bottomUp);

                    hiddenPanel.setVisibility(View.VISIBLE);

                    Animation bottomDown = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
                    FloatingActionButton btn_adduser = (FloatingActionButton)getActivity().findViewById(R.id.fab);

                    if(btn_adduser.isSelected()) {
                        btn_adduser.startAnimation(bottomDown);
                        btn_adduser.setVisibility(View.GONE);
                        btn_adduser.setSelected(false);
                    }

                    idx_optionbar_grp = true;
                }

            }
        });

        head.addView(name_tap);
        head.addView(cnt_tap);
        String[] dat = {group_num, group_name, mem_cnt, msg_cnt};
        head.setTag(dat);


        parent.addView(head);
    }
    public void Listing_User(LinearLayout list_com, LinearLayout list_fav){

        dbhelper = new DBHelper(getActivity(), "data", null, 1);
        //dbhelper.test_user();
        ArrayList<friendsData> data = dbhelper.ReadFriendsData();

        Iterator iterator =  data.iterator();
        while(iterator.hasNext()){
            friendsData temp = (friendsData)iterator.next();

            if( String.valueOf(temp.getFav()).equals("0")){
                Create_Usertap(list_com, temp.getID(), temp.getNickname(), String.valueOf(temp.getCnt()), temp.getProfile());
            }else if(String.valueOf(temp.getFav()).equals("1")){
                //즐겨찾기 등록 친구
                Create_Usertap(list_fav, temp.getID(), temp.getNickname(), String.valueOf(temp.getCnt()), temp.getProfile());
            }else{
                Log.d("test", "else");
            }
        }
        Create_Dumb(list_com);
    }
    public void Create_Dumb(LinearLayout parent){
        RelativeLayout head = new RelativeLayout(getActivity());
        LinearLayout.LayoutParams params1;

        params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getDP(60));
        head.setPadding(getDP(10),getDP(5), getDP(10), getDP(5));

        head.setBackgroundColor(getResources().getColor(R.color.list_background, null));
        head.setLayoutParams(params1);

        parent.addView(head);
    }
    public void Create_Usertap(LinearLayout parent, String user_id, String name, String cnt, Bitmap profile){

        RelativeLayout head = new RelativeLayout(getActivity());
        TextView name_tap = new TextView(getActivity());
        TextView cnt_tap = new TextView(getActivity());

        LinearLayout.LayoutParams params1;
        RelativeLayout.LayoutParams params2;

        params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getDP(50));
        head.setPadding(getDP(10),getDP(5), getDP(10), getDP(5));
        head.setBackground(getResources().getDrawable(R.drawable.border, null));
        head.setLayoutParams(params1);

        params1 = new LinearLayout.LayoutParams(getDP(35), getDP(35));
        ImageView profile_photo = new ImageView(getActivity());
        profile_photo.setLayoutParams(params1);
        profile_photo.setBackground(new ShapeDrawable(new OvalShape()));
        profile_photo.setClipToOutline(true);
        profile_photo.setImageBitmap(profile);


        params2 = new RelativeLayout.LayoutParams(getDP(150), ViewGroup.LayoutParams.MATCH_PARENT);
        params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        name_tap.setTextSize(getDP(8));
        name_tap.setGravity(Gravity.CENTER_VERTICAL);
        name_tap.setLayoutParams(params2);
        name_tap.setTextColor(Color.BLACK);
        name_tap.setText(name);


        params2 = new RelativeLayout.LayoutParams(getDP(40), ViewGroup.LayoutParams.MATCH_PARENT);
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        cnt_tap.setGravity(Gravity.CENTER);
        cnt_tap.setLayoutParams(params2);
        cnt_tap.setText(cnt);

        head.addView(profile_photo);
        head.addView(name_tap);
        head.addView(cnt_tap);

        String[] dat = {user_id, name};
        head.setTag(dat);

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idx_optionbar_grp == true){

                    LinearLayout head_layout = (LinearLayout)getActivity().findViewById(R.id.user_group);
                    for(int i=0; i<head_layout.getChildCount(); i++){
                        RelativeLayout target = (RelativeLayout) head_layout.getChildAt(i);
                        if(target.isSelected()){
                            target.setSelected(false);
                            target.setBackground(getResources().getDrawable(R.drawable.border, null));
                        }
                    }

                    Animation bottomDown = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
                    LinearLayout hiddenPanel = (LinearLayout)getActivity().findViewById(R.id.hidden_panel_grp);
                    hiddenPanel.startAnimation(bottomDown);
                    hiddenPanel.setVisibility(View.GONE);
                    idx_optionbar_grp = false;
                }


                if(view.isSelected()){
                    idx_select--;
                    view.setSelected(false);
                    view.setBackground(getResources().getDrawable(R.drawable.border, null));
                    list_selectedview.remove(view);
                }else{
                    idx_select++;
                    view.setSelected(true);
                    view.setBackgroundColor(getResources().getColor(R.color.primary_light, null));
                    list_selectedview.add(view);
                }

                if(idx_select > 0){

                    if(idx_optionbar == true){
                        return;
                    }
                    Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                            R.anim.bottom_up);
                    LinearLayout hiddenPanel = (LinearLayout)getActivity().findViewById(R.id.hidden_panel);
                    hiddenPanel.startAnimation(bottomUp);
                    hiddenPanel.setVisibility(View.VISIBLE);

                    Animation bottomDown = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
                    FloatingActionButton btn_adduser = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                    if(btn_adduser.isSelected()) {
                        btn_adduser.startAnimation(bottomDown);
                        btn_adduser.setVisibility(View.GONE);
                        btn_adduser.setSelected(false);
                    }


                    idx_optionbar = true;

                }else{
                    Animation bottomDown = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
                    LinearLayout hiddenPanel = (LinearLayout)getActivity().findViewById(R.id.hidden_panel);
                    hiddenPanel.startAnimation(bottomDown);
                    hiddenPanel.setVisibility(View.GONE);

                    Animation bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
                    FloatingActionButton btn_adduser = (FloatingActionButton)getActivity().findViewById(R.id.fab);
                    if(!(btn_adduser.isSelected())){
                        btn_adduser.startAnimation(bottomUp);
                        btn_adduser.setVisibility(View.VISIBLE);
                        btn_adduser.setSelected(true);
                    }

                    idx_optionbar = false;
                }
            }
        });

        parent.addView(head);
    }
    public void refresh(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }
    public int getDP(int num){
        int dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        return dp;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== 1){
            Snackbar.make(getActivity().findViewById(R.id.content), "그룹 수정 완료", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            refresh();
        }else if(requestCode == 2){
            Snackbar.make(getActivity().findViewById(R.id.content), "그룹 생성 완료", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            refresh();
        }else if(requestCode == 3){
            if(data.getStringExtra("idx").equals("Y")){
                Snackbar.make(getActivity().findViewById(R.id.content), "친구 추가 완료", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                refresh();
            }
        }
    }
}