package com.lx2td.myapplication.signature;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.lx2td.myapplication.R;
import com.lx2td.myapplication.ViewPagerAdapter;

public class SignatureActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        viewPager = (ViewPager) findViewById(R.id.sign_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.sign_tabs);

        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RSAFragment(), "RSA");
        adapter.addFragment(new DSAFragment(), "DSA");
        viewPager.setAdapter(adapter);
    }
}