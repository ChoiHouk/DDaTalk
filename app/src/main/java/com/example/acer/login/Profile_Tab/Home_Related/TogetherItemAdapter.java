package com.example.acer.login.Profile_Tab.Home_Related;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acer.login.Login_Related.Constants;
import com.example.acer.login.Login_Related.SharedPrefManager;
import com.example.acer.login.R;
import com.example.acer.login.Profile_Tab.Home_reply.ReplyActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TogetherItemAdapter extends BaseAdapter {

    private ArrayList<TogetherItem> items = new ArrayList<>();
    private RequestQueue rq;
    private SharedPrefManager sharedPrefManager;

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(TogetherItem item){
        items.add(item);
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        sharedPrefManager = SharedPrefManager.getInstance(context);
        rq = Volley.newRequestQueue(context);

        TogetherItemView view;
        if(convertView == null){
            view = new TogetherItemView(context);
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.together_item, parent, false);
        }else{
            view = (TogetherItemView) convertView;
        }
        final TogetherItemView finalView = view;
        final TogetherItem item = items.get(position);
        Button together_button = (Button)view.findViewById(R.id.together_button);
        together_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int writing_no = item.getWriting_no();
                checkToWithButton(writing_no,context,sharedPrefManager,item,finalView);
                Toast.makeText(context,"함께타요 버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        Button comment_button = (Button)view.findViewById(R.id.comment_button);
        comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), ReplyActivity.class);
                intent.putExtra("email",item.getEmail());
                intent.putExtra("content",item.getContent());
                intent.putExtra("picture",item.getResId());
                intent.putExtra("writing_no",item.getWriting_no());
                context.startActivity(intent);
                finalView.setComment_Tv(item.getComment());
                Toast.makeText(context,"댓글달기 버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        view.setName(item.getEmail());
        view.setContent(item.getContent());
        view.setImageView(item.getResId());
        view.setTogether_tv(item.getTogether());
        view.setComment_Tv(item.getComment());

        return view;

    }

    private void checkToWithButton(int writing_no, Context context, SharedPrefManager sharedPrefManager, final TogetherItem item,final TogetherItemView togetherItemView){
        final String post_email = sharedPrefManager.getInstance(context).getUserEmail();
        final int post_writing_no = writing_no;
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ISCHECKED_WITH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(!obj.getBoolean("error")){
                        item.setTogether(obj.getInt("with_cnt"));
                        togetherItemView.setTogether_tv(item.getTogether());
                    }else{
                        item.setTogether(obj.getInt("with_cnt"));
                        togetherItemView.setTogether_tv(item.getTogether());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",post_email);
                params.put("writing_no",String.valueOf(post_writing_no));
                return params;
            }
        };
        rq.add(stringRequest);


    }


}
