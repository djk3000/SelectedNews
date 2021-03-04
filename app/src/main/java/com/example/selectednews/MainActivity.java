package com.example.selectednews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import entity.DataModel;
import utiliy.NewsAdapter;
import utiliy.SmartHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            lastOffset = savedInstanceState.getInt("offset");
            lastPosition = savedInstanceState.getInt("position");
        }
        context = this;
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        for (int i = 0; i < mTitles.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setTag(i);
            tab.setText(mTitles[i]);
            tabLayout.addTab(tab);
        }
        String address = getResources().getString(R.string.address);
        sh = new SmartHttpClient();
        try {
            sh.SyncGet(address, handler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.setOnNavigationItemSelectedListener((item -> {
            Toast.makeText(context, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
            return true;
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_title, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    Gson gson = new Gson();
                    DataModel dataModel = gson.fromJson(msg.obj.toString(), DataModel.class);
                    RecyclerView recyclerView = findViewById(R.id.newsRecycle);
                    int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
                    GridLayoutManager manager = new GridLayoutManager(context, gridColumnCount);
                    recyclerView.setLayoutManager(manager);
                    List<DataModel.DetialData> data = dataModel.getDetialData();
                    NewsAdapter adapter = new NewsAdapter(data, context);
                    adapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
                    recyclerView.setAdapter(adapter);
                    manager.scrollToPositionWithOffset(lastPosition, lastOffset);
                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            View topView = manager.getChildAt(0);          //获取可视的第一个view
                            lastOffset = topView.getTop();                                   //获取与该view的顶部的偏移量
                            lastPosition = manager.getPosition(topView);  //得到该View的数组位置
                        }
                    });
                    adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String url = data.get(position).getUrl();
                            Log.i("itemclick", url);
                            Intent it = new Intent(context, NewsDetialActivity.class);
                            it.putExtra("url", url);
                            startActivity(it);
                        }
                    });
                    break;
                case 1001:
                    Log.i("msgfailed", msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("offset", lastOffset);
        outState.putInt("position", lastPosition);
    }

    private int lastPosition = 0;
    private int lastOffset = 0;
    private Context context;
    protected SmartHttpClient sh;
    private String mTitles[] = {
            "头条推荐", "上海", "生活", "娱乐八卦", "体育",
            "段子", "美食", "电影", "科技", "搞笑",
            "社会", "财经", "时尚", "汽车", "军事",
            "小说", "育儿", "职场", "萌宠", "游戏",
            "健康", "动漫", "互联网"};
}