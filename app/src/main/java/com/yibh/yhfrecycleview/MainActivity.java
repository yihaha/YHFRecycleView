package com.yibh.yhfrecycleview;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yibh.yhfrecycleview.adapter.ReAdapter;
import com.yibh.yhfrecycleview.adapter.YAdapter;
import com.yibh.yhfrecycleview.http.GankApi;
import com.yibh.yhfrecycleview.http.GankService;
import com.yibh.yhfrecycleview.model.AllData;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycleview;
    private GankService gankService= GankApi.getSingleService();
    private YAdapter<ReAdapter> mYAdapter;
    private ReAdapter reAdapter;
    private List<AllData.GankData> gankDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycleview = (RecyclerView) findViewById(R.id.recycleview_layout);

        gankDatas=new ArrayList<>();
        reAdapter = new ReAdapter(gankDatas);
        mYAdapter = new YAdapter<>(reAdapter);

        mRecycleview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mYAdapter.setConutSpan(mRecycleview);
        mRecycleview.setAdapter(mYAdapter);

        loadData(1);
    }

    private void loadData(int page) {
        gankService.getMeiziList(10,page)
                .map(new Func1<AllData, List<AllData.GankData>>() {
                    @Override
                    public List<AllData.GankData> call(AllData allData) {
                        return allData.results;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<AllData.GankData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w("出错",e.toString());
                    }

                    @Override
                    public void onNext(List<AllData.GankData> list) {
                        Log.w("有数据",list.get(0).url);
                        gankDatas.addAll(list);
                        Log.w("有数据1",gankDatas.get(0).url);
                        mYAdapter.getAdapter().setRefre(gankDatas);
                        mYAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainactivity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addheader:
                addHeaderView();

                break;

            case R.id.addfooter:
                addFooterView();
                break;


        }

        return super.onOptionsItemSelected(item);
    }

    private void addFooterView() {
    }

    private int mPreviousPoint; //上一个点的位置
    private void addHeaderView() {
        //内容
        final String[] titles = {"静水流深，沧笙踏歌；三生阴晴圆缺，一朝悲欢离合"
                , "蓄起亘古的情丝，揉碎殷红的相思"
                , "用我三生烟火，换你一世迷离"
                , "任他凡事清浊，为你一笑间轮回甘堕"
        };

        View layout = LayoutInflater.from(this).inflate(R.layout.header_layout, null);
        final YRollViewpager yRollViewpager= (YRollViewpager) layout.findViewById(R.id.viewpager);
        final TextView title= (TextView) layout.findViewById(R.id.title);
        final LinearLayout mLL= (LinearLayout) layout.findViewById(R.id.point_ll);
        mYAdapter.addHeaderView(layout);

        ArrayList<String> urls = new ArrayList<>();
        for (int i=0;i<4;i++){
            urls.add(gankDatas.get(i).url);
        }
        yRollViewpager.initData(urls);
        title.setText(titles[0]); //设置默认标题
        //初始化点
        for (int i = 0; i < titles.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.viewpager_selector);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT);

            if (i != 0) {
                layoutParams.leftMargin = 9;
                imageView.setEnabled(false);
            }
            imageView.setLayoutParams(layoutParams);
            mLL.addView(imageView);

        }
        yRollViewpager.startRollPage();


        yRollViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int pos = position % titles.length;
//                  //改变点的颜色
                yRollViewpager.mPreviousPoint = pos;
                mLL.getChildAt(pos).setEnabled(true);
                mLL.getChildAt(mPreviousPoint).setEnabled(false);

                title.setText(titles[pos]);
                mPreviousPoint = pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        yRollViewpager.setOnpageItemClickListener(new YRollViewpager.OnpageItemClickListener() {
            @Override
            public void clickPage(int position) {
                Toast.makeText(MainActivity.this, titles[position], Toast.LENGTH_SHORT).show();
            }
        });

    }


}
