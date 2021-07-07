package com.lx2td.myapplication.modulo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.lx2td.myapplication.R;
import com.lx2td.myapplication.ViewPagerAdapter;

public class ModuloActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ModuloCalcFragment(), "Modulo");
        adapter.addFragment(new LogarithFragment(), "Logarith");
        adapter.addFragment(new SEquationsFragment(), "S.Equations");
        viewPager.setAdapter(adapter);
    }
}