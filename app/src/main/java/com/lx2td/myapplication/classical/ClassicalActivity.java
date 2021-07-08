package com.lx2td.myapplication.classical;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.lx2td.myapplication.R;

import java.util.regex.Pattern;

public class ClassicalActivity extends AppCompatActivity {

    EditText plain, key, cipher;
    Button btn_cipher, btn_decipher;
    RadioGroup group;
    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classical);

        cipher = findViewById(R.id.ed_cipher);
        plain = findViewById(R.id.ed_plain);
        key = findViewById(R.id.ed_key);
        btn_cipher = findViewById(R.id.btn_cipher);
        btn_decipher = findViewById(R.id.btn_decipher);
        group = findViewById(R.id.rg_classical);
        table = findViewById(R.id.table);

        group.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            // This will get the radiobutton that has changed in its check state
            RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
            // This puts the value (true/false) into the variable
            boolean isChecked = checkedRadioButton.isChecked();
            // If the radiobutton that has changed in check state is now checked...
            if (isChecked)
            {
                table.removeAllViewsInLayout();
                cipher.setText("");
                plain.setText("");
                key.setText("");
                switch (checkedId) {
                    case R.id.rb_ceasar:
                        btn_cipher.setOnClickListener(v ->{
                            if(validate())
                            {
                                cipher.setText(Classical.ceasarCipher(plain.getText().toString().replaceAll("\\s+",""),Integer.parseInt(key.getText().toString())));
                            }

                        });
                        btn_decipher.setOnClickListener(v ->{
                            if(validate())
                            {
                                cipher.setText(Classical.ceasarDecipher(plain.getText().toString().replaceAll("\\s+",""),Integer.parseInt(key.getText().toString())));
                            }
                        });
                        break;
                    case R.id.rb_vigenere_loop:
                        btn_cipher.setOnClickListener(v ->{
                            if(validate())
                            {
                                cipher.setText(Classical.vigenereLoopCipher(plain.getText().toString().replaceAll("\\s+",""),key.getText().toString()));
                            }
                        });
                        btn_decipher.setOnClickListener(v ->{
                            if(validate())
                            {
                                cipher.setText(Classical.vigenereLoopDecipher(plain.getText().toString().replaceAll("\\s+",""),key.getText().toString()));
                            }
                        });
                        break;
                    case R.id.rb_vigenere_auto:
                        btn_cipher.setOnClickListener(v ->{
                            if(validate())
                            {
                                cipher.setText(Classical.vigenereAutoCipher(plain.getText().toString().replaceAll("\\s+",""),key.getText().toString()));
                            }
                        });
                        btn_decipher.setOnClickListener(v ->{
                            if(validate())
                            {
                                cipher.setText(Classical.vigenereAutoDecipher(plain.getText().toString().replaceAll("\\s+",""),key.getText().toString()));
                            }
                        });
                        break;
                    case R.id.rb_singlecharacter:
                        btn_cipher.setOnClickListener(v ->{
                            if(validate())
                            {
                                cipher.setText(Classical.singlecharacterCipher(plain.getText().toString().replaceAll("\\s+",""),key.getText().toString()));
                            }
                        });
                        btn_decipher.setOnClickListener(v ->{
                            if(validate())
                            {
                                cipher.setText(Classical.singlecharacterDecipher(plain.getText().toString().replaceAll("\\s+",""),key.getText().toString()));
                            }
                        });
                        break;
                    case R.id.rb_rail_fence:
                        btn_cipher.setOnClickListener(v ->{
                            table.removeAllViewsInLayout();
                            if(validate())
                            {
                                char[][] matrix = new char[20][20];
                                cipher.setText(Classical.railfenceCipher(plain.getText().toString().replaceAll("\\s+",""),Integer.parseInt(key.getText().toString()), matrix).replaceAll("\\s+",""));
                                int rows = Integer.parseInt(key.getText().toString());
                                int columns = plain.getText().toString().replaceAll("\\s+","").length() / Integer.parseInt(key.getText().toString());
                                if(plain.getText().toString().replaceAll("\\s+","").length() % Integer.parseInt(key.getText().toString()) != 0) columns += 1;
                                drawTable(matrix, rows, columns);
                            }
                        });
                        btn_decipher.setOnClickListener(v ->{
                            table.removeAllViewsInLayout();
                            if(validate())
                            {
                                char[][] matrix = new char[20][20];
                                cipher.setText(Classical.railfenceDecipher(plain.getText().toString().replaceAll("\\s+",""),Integer.parseInt(key.getText().toString()), matrix).replaceAll("\\s+",""));
                                int columns = Integer.parseInt(key.getText().toString());
                                int rows = plain.getText().toString().replaceAll("\\s+","").length() / Integer.parseInt(key.getText().toString());
                                if(plain.getText().toString().replaceAll("\\s+","").length() % Integer.parseInt(key.getText().toString()) != 0) rows += 1;
                                drawTable(matrix, rows, columns);
                            }
                        });
                        break;
                    case R.id.rb_playfair:
                        btn_cipher.setOnClickListener(v ->{
                            if(validate())
                            {
                                char[][] matrix = new char[5][5];
                                cipher.setText(Classical.playfairCipher(plain.getText().toString().replaceAll("\\s+",""),key.getText().toString(), matrix));
                                table.removeAllViewsInLayout();
                                drawTable(matrix,5,5);
                            }
                        });
                        btn_decipher.setOnClickListener(v ->{
                            if(validate())
                            {
                                char[][] matrix = new char[5][5];
                                cipher.setText(Classical.playfairDecipher(plain.getText().toString().replaceAll("\\s+",""),key.getText().toString(), matrix));
                                table.removeAllViewsInLayout();
                                drawTable(matrix,5,5);
                            }
                        });
                        break;
                }
            }
        });

        ((RadioButton) findViewById(R.id.rb_ceasar)).performClick();

    }

    void drawTable(char[][] matrix, int rows, int columns){
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(0x00000000); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(0);
        gd.setStroke(1, 0xFF000000);
        for (int i = 0; i < rows; i++) {
            TableRow row = new TableRow(ClassicalActivity.this);
            for (int j = 0; j < columns; j++) {
                TextView cell = new TextView(ClassicalActivity.this);
                cell.setText(""+matrix[i][j]);
                cell.setTextSize(32);
                cell.setWidth(150);
                cell.setHeight(150);
                cell.setBackground(gd);
                cell.setGravity(Gravity.CENTER);
                row.addView(cell);
            }
            table.addView(row);
        }
    }

    boolean validate(){
        if(plain.getText().toString().equals(""))
        {
            plain.setError("Không được để trống");
            return false;
        }
        if(key.getText().toString().equals(""))
        {
            key.setError("Không được để trống");
            return false;
        }
        if(((RadioButton) findViewById(R.id.rb_ceasar)).isChecked() || ((RadioButton) findViewById(R.id.rb_rail_fence)).isChecked())
        {
            if(Pattern.matches("[a-zA-Z]+", key.getText().toString()) == true)
            {
                key.setError("Chỉ được nhập số");
                return false;
            }
        }
        else if(((RadioButton) findViewById(R.id.rb_singlecharacter)).isChecked())
        {
            if(Pattern.matches("[a-zA-Z]+", key.getText().toString()) == false)
            {
                key.setError("Chỉ được nhập chữ");
                return false;
            }
            if(key.getText().toString().length() != 26)
            {
                key.setError("Key chữ đơn phải đủ 26 ký tự từ bảng Alphabet (Tạm được thay thế bằng key cho sẵn)");
                key.setText("LYFGMKNERXJPQIVATOHSZDBUCW");
            }
        }
        else
            if(Pattern.matches("[a-zA-Z]+", key.getText().toString()) == false)
            {
                key.setError("Chỉ được nhập chữ");
                return false;
            }
        return true;
    }

}