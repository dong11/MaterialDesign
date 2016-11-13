package com.dong.huang.materialdesign;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CollapsingToolbarLayout mToolbarLayout;
    private ListView mListView;
    private List<String> mList;
    private ArrayAdapter mAdapter;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();

        new Thread(){
            @Override
            public void run() {
                try {
                    http();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initInstances() {
        //ToolBar代替ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //设置导航抽屉按钮
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //使配置ToolBar参数生效
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //悬浮按钮设置
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //点击触发事件
                            }
                        }).show();
            }
        });

        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        mToolbarLayout.setTitle("Material Design");

        mListView = (ListView) findViewById(R.id.listView);

        mList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            mList.add("Hello Material Design!");
        }

        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mAdapter);
        getListViewHeight(mListView);

        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getItemId() == R.id.navItem3){
                    mDrawerLayout.closeDrawers();
                    Intent intent = new Intent(MainActivity.this, TabLayoutActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //与mDrawerLayout状态同步
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 重新计算ListView的高度
     * @param listView
     */
    private void getListViewHeight(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter == null){
            return;
        }
        int totalHeight = 0;
        for(int i = 0;i < listAdapter.getCount();i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void http() throws IOException, JSONException {
        URL url = new URL("http://114.215.249.55:9188/iphone/content/mmbMemberInfoAction!login");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");

        String str = "account=admin@163.com&password=admin";

        //3459548F41BA1A7315A28257A0E5F3B4
        //3459548F41BA1A7315A28257A0E5F3B4


        DataOutputStream out = new DataOutputStream(conn.getOutputStream());

        out.writeBytes(str);
        out.flush();
        out.close();

        String cookieval = conn.getHeaderField("set-cookie");

        Log.i("123","------>" + cookieval);

        String sessionid = "";
        if(cookieval != null) {
            sessionid = cookieval.substring(0, cookieval.indexOf(";"));
        }

        Log.i("123", "----->" + sessionid);

        Log.i("123","---->" + conn.getResponseCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        String response = "";
        String line;
        while ((line = reader.readLine()) != null) {
            response += line;
        }

        Log.i("123", "----->" + response);

        /*JSONObject jsonObject = new JSONObject(response);
        JSONObject object = jsonObject.optJSONArray("result").getJSONObject(0);
        sessionid = object.optString("sessionId");*/



        Log.i("123","----->" + sessionid);

        HttpURLConnection con = (HttpURLConnection) new URL("http://114.215.249.55:9188/iphone/content/mmbMemberInfoAction!logOut").openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setUseCaches(false);
        con.setRequestMethod("POST");

        con.setRequestProperty("Cookie", sessionid);

        Log.i("123", "----->" + sessionid);

        String str2 = "sessionId=" + sessionid;

       /* DataOutputStream out2 = new DataOutputStream(con.getOutputStream());

        Log.i("123","----->" + str2);

        out2.writeBytes(str2);
        out2.flush();
        out2.close();*/

        BufferedReader reader1 = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
        String response1 = "";
        String line1;
        while ((line1 = reader1.readLine()) != null) {
            response1 += line1;
        }

        Log.i("123","----->" + response1);
    }

}
