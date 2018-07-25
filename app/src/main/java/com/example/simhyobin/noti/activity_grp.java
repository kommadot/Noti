package com.example.simhyobin.noti;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by insec on 2018-07-24.
 */

public class activity_grp extends AppCompatActivity{

    private String bf_grp_name;
    private int idx;
    private String bf_grp_num;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grp);
        /*
        String[] dat = {user_id, name, group_num};
        */
        Intent intent = getIntent();

        ArrayList<String[]> data = (ArrayList<String[]>)intent.getSerializableExtra("data");

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsingtoolbar_grp);
        android.support.design.widget.FloatingActionButton floatingActionButton = (android.support.design.widget.FloatingActionButton)findViewById(R.id.fab_grp);


        idx = (int)intent.getSerializableExtra("idx");

        if(idx == 0){
            collapsingToolbarLayout.setTitle("새 그룹 만들기");
            bf_grp_name = "";
        }else{
            collapsingToolbarLayout.setTitle("그룹 정보 수정");
            floatingActionButton.setImageResource(R.drawable.baseline_edit_white_48);
            String grp_name = (String)intent.getSerializableExtra("grp_name");
            EditText editText_grpname = (EditText)findViewById(R.id.grp_name);
            editText_grpname.setText(grp_name);
            bf_grp_num = (String)intent.getSerializableExtra("grp_num");
            bf_grp_name = grp_name;
        }

        int user_cnt = data.size();

        final Iterator<String[]> iterator = data.iterator();
        while(iterator.hasNext()){
            String[] tempdata = iterator.next();

            CreateUserList(tempdata[0], tempdata[1]);
        }



        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.title_primary_text, null));


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.name_Toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout head = (LinearLayout)findViewById(R.id.grp_userlist);
                DBHelper dbHelper = new DBHelper(activity_grp.this, "data", null, 1);
                ArrayList<String[]> data = new ArrayList<String[]>();

                ArrayList<View> child_views = new ArrayList<>();
                head.findViewsWithText(child_views, "selected", View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);

                for(int i=0; i<child_views.size(); i++){
                    LinearLayout temp = (LinearLayout)child_views.get(i);
                    data.add((String[])temp.getTag());
                }
                EditText editText = (EditText)findViewById(R.id.grp_name);
                String grp_name = editText.getText().toString();

                if(idx == 0){
                    dbHelper.group_user(data, grp_name);
                }else{
                    if(grp_name.equals(bf_grp_name)){
                        dbHelper.modify_group(data, bf_grp_num, grp_name, 0);
                    }else{
                        dbHelper.modify_group(data, bf_grp_num, grp_name, 1);
                    }
                }




            }
        });
    }

    public void CreateUserList(String user_id, String user_name){
        LinearLayout head = (LinearLayout)findViewById(R.id.grp_userlist);

        LinearLayout frag_head = new LinearLayout(activity_grp.this);
        final android.support.v7.widget.AppCompatCheckBox checkBox = new android.support.v7.widget.AppCompatCheckBox(activity_grp.this);
        TextView name_view = new TextView(activity_grp.this);

        String[] tag_data = {user_name, user_id};

        LinearLayout.LayoutParams params1;
        params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getDP(60));

        frag_head.setLayoutParams(params1);
        frag_head.setOrientation(LinearLayout.HORIZONTAL);
        frag_head.setContentDescription("selected");

        params1 = new LinearLayout.LayoutParams(getDP(60), ViewGroup.LayoutParams.MATCH_PARENT);
        checkBox.setLayoutParams(params1);
        checkBox.setChecked(true);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    LinearLayout parent = (LinearLayout)view.getParent();
                    parent.setBackgroundColor(getResources().getColor(R.color.white, null));
                    TextView textView = (TextView)parent.getChildAt(1);
                    textView.setTextColor(getResources().getColor(R.color.black, null));
                    parent.setContentDescription("selected");

                }else{
                    LinearLayout parent = (LinearLayout)view.getParent();
                    parent.setBackgroundColor(getResources().getColor(R.color.divider, null));
                    TextView textView = (TextView)parent.getChildAt(1);
                    textView.setTextColor(getResources().getColor(R.color.white, null));
                    parent.setContentDescription("unselect");
                }
            }
        });

        params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        name_view.setLayoutParams(params1);
        name_view.setGravity(Gravity.CENTER_VERTICAL);
        name_view.setTextColor(getResources().getColor(R.color.black, null));
        name_view.setText(user_name);

        frag_head.addView(checkBox);
        frag_head.addView(name_view);
        frag_head.setTag(tag_data);


        head.addView(frag_head);

    }
    public int getDP(int num){
        int dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
        return dp;
    }
}
