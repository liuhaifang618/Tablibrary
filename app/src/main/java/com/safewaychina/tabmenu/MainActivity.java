package com.safewaychina.tabmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.widget.FrameLayout;

import com.safewaychina.basecommon.base.widget.listener.NetValidator;
import com.safewaychina.tabmenu.bubbleview.CoverManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private TabLayout tb;
    private TabViewPager idviewpager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    // private FragmentPagerAdapter mAdapter;

    private String[] mTitles = new String[]{"First Fragment!",
            "Second Fragment!", "Third Fragment!", "Fourth Fragment!", "Five Fragment"};
    private android.widget.FrameLayout idframelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.idframelayout = (FrameLayout) findViewById(R.id.id_framelayout);
        // this.idviewpager = (TabViewPager) findViewById(R.id.id_viewpager);
        this.tb = (TabLayout) findViewById(R.id.tb);
//        final TabImageView tabImageView = (TabImageView) findViewById(R.id.tab_image);
//        tabImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tabImageView.setIconSelect(!tabImageView.isIconSelect());
//            }
//        });

        CoverManager.getInstance().init(this);
        CoverManager.getInstance().setMaxDragDistance(150);
        CoverManager.getInstance().setExplosionTime(150);


        TabItem item = new TabItem(false, "111222", true, true);
        TabItem item2 = new TabItem("2222222", true);
        TabItem item3 = new TabItem("", false);
        TabItem item4 = new TabItem("3332222", true);
        TabItem item5 = new TabItem("3332221", true);
        List<TabItem> items = new ArrayList<>();
        items.add(item);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        initDatas();

        BaseTabAdapter adapter = new DeafultTabAdapter(getApplicationContext(), items);
        // tb.setAdapter(adapter);
        //tb.setViewPager(idviewpager);
        //idviewpager.setAdapter(mAdapter);

        tb.setSelectValidator(new NetValidator(getApplicationContext()));

        tb.setFragmentAdapter(adapter, getSupportFragmentManager(), mTabs, R.id.id_framelayout);
        tb.setCurrentFragment(1);

        tb.setBubbleText(0, 1);
    }

    private void initDatas() {

        for (String title : mTitles) {
            TabFragment tabFragment = new TabFragment();
            Bundle args = new Bundle();
            args.putString("title", title);
            tabFragment.setArguments(args);
            mTabs.add(tabFragment);
        }

//        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
//
//            @Override
//            public int getCount() {
//                return mTabs.size();
//            }
//
//            @Override
//            public Fragment getItem(int arg0) {
//                return mTabs.get(arg0);
//            }
//        };

    }

}
