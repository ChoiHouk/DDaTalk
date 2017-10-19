package com.example.acer.login.Profile_Tab;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acer.login.Login_Related.SharedPrefManager;
import com.example.acer.login.Profile_Tab.Home_Related.Writing;
import com.example.acer.login.Profile_Tab.MyPage_Related.MyPage_Fragment_Sub;
import com.example.acer.login.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPage_Fragment extends Fragment{

    ViewGroup rootView;

    private static final String JSON_URL = "http://104.198.211.126/getwriting.php";
    String HttpUrl2 = "http://104.198.211.126/getUserimgUri.php";

    ListView listView;
    List<Writing> writingList;

    ArrayList<HashMap<String, String>> mArrayList;

    ImageView imageView, user_profile;
    ImageButton mimageButton;

    TextView textView;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    String username, userbirth, useremail, user_imagePath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_mypage,container,false);

        listView = (ListView) rootView.findViewById(R.id.listview);

        writingList = new ArrayList<>();
        mArrayList = new ArrayList<>();

        user_profile = (ImageView)rootView.findViewById(R.id.user_profile);
        imageView = (ImageView) rootView.findViewById(R.id.imageView4);
        mimageButton = (ImageButton) rootView.findViewById(R.id.imageButton11);

        textView = (TextView) rootView.findViewById(R.id.textView);

        requestQueue = Volley.newRequestQueue(rootView.getContext());
        progressDialog = new ProgressDialog(rootView.getContext());

        username = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUsername();
        userbirth = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUserBirthday();
        useremail = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUserEmail();

        ReceiveImg();


        textView.setText(username);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment mypage_sub = new MyPage_Fragment_Sub();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, mypage_sub);
                transaction.addToBackStack(null);
                transaction.commit();
                InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);

            }
        });

        mimageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment writeFrag = new Write_Fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, writeFrag);
                transaction.addToBackStack(null);
                transaction.commit();
                InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });

        progressDialog.setMessage("Please Wait, We are getting Your Data on Server");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        View listViewItem = inflater.inflate(R.layout.list_items, null, true);

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray writingArray = obj.getJSONArray("writing");

                            if(writingArray.get(0) != "") {
                                mimageButton.setVisibility(View.GONE);
                            }

                            for (int i = 0; i < writingArray.length(); i++) {

                                JSONObject writingObject = writingArray.getJSONObject(i);
                                DateFormat trans = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                                String content = writingObject.getString("content");
                                String with_cnt = writingObject.getString("with_cnt");
                                String date = writingObject.getString("date");

                                Date tr1 = trans.parse(date);
                                date = format.format(tr1);

                                HashMap<String,String> hashMap = new HashMap<>();

                                hashMap.put("content", content);
                                hashMap.put("with_cnt", with_cnt);
                                hashMap.put("date", date);


                                try{ mArrayList.add(hashMap);}
                                catch (Exception e){e.printStackTrace(); Toast.makeText(rootView.getContext(), e.toString(), Toast.LENGTH_LONG).show();}

                            }

                            ListAdapter adapter = new SimpleAdapter(
                                    getActivity().getApplication(), mArrayList, R.layout.list_items,
                                    new String[]{"content", "with_cnt", "date"},
                                    new int[]{R.id.textViewContent, R.id.textViewWith_cnt, R.id.textViewDate}
                            );

                            listView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",useremail);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplication());
        requestQueue.add(stringRequest);


        return rootView;
    }

    public Bitmap rotate(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }


    public int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    //디비에서 유저이미지 가져오기 메소드
    public void ReceiveImg(){

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject = new JSONObject(response);
                    JSONArray jsonArray = jsonobject.getJSONArray("user");
                    JSONObject data = jsonArray.getJSONObject(0);

                    String userimg = data.getString("userimg");

                    //이미지 셋팅
                    //서버에서 가져온 이미지 셋팅
                    File imgFile = new File(userimg);
                    if(imgFile.exists())
                    {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        user_profile.setImageBitmap(myBitmap);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("email", useremail);
                return parameters;
            }
        };
        queue.add(stringRequest);
    }



}
