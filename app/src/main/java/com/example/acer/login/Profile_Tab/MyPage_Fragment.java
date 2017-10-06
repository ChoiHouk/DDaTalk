package com.example.acer.login.Profile_Tab;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.acer.login.Login_Related.SharedPrefManager;
import com.example.acer.login.Profile_Tab.MyPage_Related.MyPage_SubActivity;
import com.example.acer.login.R;

public class MyPage_Fragment extends Fragment{

    //로그인놈 이름가져오기
    TextView name_view;
    String name;

    /*private static String TAG = "phptest_MainActivity";

    private static final String TAG_JSON = "webnautes";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_WITH_CNT = "with_cnt";
    private static final String TAG_DATE = "date";

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;*/
    ListView mlistView;
    ImageButton mimageButton;
    /*String mJsonString;
    private static String USER_TAG = "phptest_subActivity";

    private static final String USER_TAG_JSON = "webnautes";
    private static final String USER_TAG_NAME = "name";
    private static final String USER_TAG_BIRTHDAY = "birthday";
    private static final String USER_TAG_EMAIL = "email";

    ArrayList<String> mUserArrayList;
    String name, birthday, email;*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_mypage,container,false);

        //로그인놈 이름 가져오기 및 표기
        name = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUsername();
        name_view = (TextView)rootView.findViewById(R.id.textView);
        name_view.setText(name);


        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView4);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplication(), MyPage_SubActivity.class);
                startActivity(i);
            }
        });



//        mTextViewResult = (TextView)rootView.findViewById(R.id.textView_main_result);
        mlistView = (ListView) rootView.findViewById(R.id.listview);
//        mArrayList = new ArrayList<>();
//        mUserArrayList = new ArrayList<>();
        mimageButton = (ImageButton) rootView.findViewById(R.id.imageButton11);


        return rootView;
    }




}
