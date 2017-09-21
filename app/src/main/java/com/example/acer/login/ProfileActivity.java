package com.example.acer.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.acer.login.Login_Related.LoginActivity;
import com.example.acer.login.Login_Related.SharedPrefManager;
import com.example.acer.login.Profile_Tab.DD119;
import com.example.acer.login.Profile_Tab.Home;
import com.example.acer.login.Profile_Tab.Mypage;
import com.example.acer.login.Profile_Tab.Stamp;
import com.example.acer.login.Profile_Tab.Write;

public class ProfileActivity extends AppCompatActivity {

    //    private TextView textViewUsername, textViewUserEmail;
    TabLayout tabs;
    Home home;
    Stamp stamp;
    Write write;
    DD119 dd119;
    Mypage mypage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        home = new Home();
        stamp = new Stamp();
        write = new Write();
        dd119 = new DD119();
        mypage = new Mypage();


        getSupportFragmentManager().beginTransaction().add(R.id.container,home).commit();

        tabinit();

//        textViewUserEmail = (TextView)findViewById(R.id.textViewUserEmail);
//        textViewUsername = (TextView)findViewById(R.id.textViewUsername);
//
//        textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
//        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.menuSettings:
                Toast.makeText(this, "You clicked settings", Toast.LENGTH_LONG).show();
                break;

        }
        return true;
    }


    void tabinit() {
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.getTabAt(0).setIcon(R.drawable.home_active);
        tabs.getTabAt(1).setIcon(R.drawable.check_disabled);
        tabs.getTabAt(2).setIcon(R.drawable.add_disabled);
        tabs.getTabAt(3).setIcon(R.drawable.em_119_disabled);
        tabs.getTabAt(4).setIcon(R.drawable.my_page_disabled);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                Fragment selected = null;
                if (position == 0) {
                    selected = home;
                    tabs.getTabAt(0).setIcon(R.drawable.home_active);
                    tabs.getTabAt(1).setIcon(R.drawable.check_disabled);
                    tabs.getTabAt(2).setIcon(R.drawable.add_disabled);
                    tabs.getTabAt(3).setIcon(R.drawable.em_119_disabled);
                    tabs.getTabAt(4).setIcon(R.drawable.my_page_disabled);
                } else if (position == 1) {
                    selected = stamp;
                    tabs.getTabAt(0).setIcon(R.drawable.home_disabled);
                    tabs.getTabAt(1).setIcon(R.drawable.check_active);
                    tabs.getTabAt(2).setIcon(R.drawable.add_disabled);
                    tabs.getTabAt(3).setIcon(R.drawable.em_119_disabled);
                    tabs.getTabAt(4).setIcon(R.drawable.my_page_disabled);
                } else if (position == 2) {
                    selected = write;
                    tabs.getTabAt(0).setIcon(R.drawable.home_disabled);
                    tabs.getTabAt(1).setIcon(R.drawable.check_disabled);
                    tabs.getTabAt(2).setIcon(R.drawable.add_active);
                    tabs.getTabAt(3).setIcon(R.drawable.em_119_disabled);
                    tabs.getTabAt(4).setIcon(R.drawable.my_page_disabled);
                } else if (position == 3) {
                    selected = dd119;
                    tabs.getTabAt(0).setIcon(R.drawable.home_disabled);
                    tabs.getTabAt(1).setIcon(R.drawable.check_disabled);
                    tabs.getTabAt(2).setIcon(R.drawable.add_disabled);
                    tabs.getTabAt(3).setIcon(R.drawable.em_119_active);
                    tabs.getTabAt(4).setIcon(R.drawable.my_page_disabled);
                } else if (position == 4) {
                    selected = mypage;
                    tabs.getTabAt(0).setIcon(R.drawable.home_disabled);
                    tabs.getTabAt(1).setIcon(R.drawable.check_disabled);
                    tabs.getTabAt(2).setIcon(R.drawable.add_disabled);
                    tabs.getTabAt(3).setIcon(R.drawable.em_119_disabled);
                    tabs.getTabAt(4).setIcon(R.drawable.my_page_active);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
