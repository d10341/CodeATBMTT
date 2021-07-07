package com.lx2td.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lx2td.myapplication.classical.ClassicalActivity;
import com.lx2td.myapplication.modulo.ModuloActivity;
import com.lx2td.myapplication.publickey.PublicKeyActivity;
import com.lx2td.myapplication.signature.SignatureActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private String[] chapters = new String[] {
            "Mã hóa cổ điển",
            "Trường hữu hạn",
            "Mã công khai",
            "Xác thực thông điệp"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv_chapter);

        final List<String> chapters_list = new ArrayList<String>(Arrays.asList(chapters));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.listview_chapter, chapters_list);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent myintent = new Intent(view.getContext(), ClassicalActivity.class);
                    startActivity(myintent);
                }
                if (position == 1) {
                    Intent myintent = new Intent(view.getContext(), ModuloActivity.class);
                    startActivity(myintent);
                }
                if (position == 2) {
                    Intent myintent = new Intent(view.getContext(), PublicKeyActivity.class);
                    startActivity(myintent);
                }
                if (position == 3) {
                    Intent myintent = new Intent(view.getContext(), SignatureActivity.class);
                    startActivity(myintent);
                }
            }
        });
    }
}