package com.lx2td.myapplication.publickey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lx2td.myapplication.R;
import com.lx2td.myapplication.modulo.Modulo;

import java.util.List;

public class PublicKeyActivity extends AppCompatActivity {

    EditText ed_q, ed_a, ed_xA, ed_xB, ed_k, ed_M;
    LinearLayout ll_xB, ll_k, ll_M;
    TextView infor;
    Button calc;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_key);

        ed_q = findViewById(R.id.ed_public_q);
        ed_a = findViewById(R.id.ed_public_a);
        ed_xA = findViewById(R.id.ed_public_xA);
        ed_xB = findViewById(R.id.ed_public_xB);
        ed_k = findViewById(R.id.ed_public_k);
        ed_M = findViewById(R.id.ed_public_M);

        ll_xB = findViewById(R.id.xB);
        ll_k = findViewById(R.id.k);
        ll_M = findViewById(R.id.M);

        infor = findViewById(R.id.tv_public_infor);

        calc = findViewById(R.id.btn_public_calc);

        rg = findViewById(R.id.rg_publickey);

        rg.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            // This will get the radiobutton that has changed in its check state
            RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
            // This puts the value (true/false) into the variable
            boolean isChecked = checkedRadioButton.isChecked();
            // If the radiobutton that has changed in check state is now checked...
            if (isChecked) {
                ed_q.setText("");
                ed_a.setText("");
                ed_xA.setText("");
                ed_xB.setText("");
                ed_k.setText("");
                ed_M.setText("");

                ll_k.setVisibility(View.GONE);
                ll_xB.setVisibility(View.GONE);
                ll_M.setVisibility(View.GONE);

                infor.setText("");
                infor.setTextSize(28);
                infor.setVisibility(View.GONE);

                switch (checkedId) {
                    case R.id.rb_dif_hel:
                        ll_xB.setVisibility(View.VISIBLE);
                        calc.setOnClickListener(v -> {
                            if (validateDifHel()) {
                                String text = "";

                                int q = Integer.parseInt(ed_q.getText().toString());
                                int a = Integer.parseInt(ed_a.getText().toString());
                                int xA = Integer.parseInt(ed_xA.getText().toString());
                                int xB = Integer.parseInt(ed_xB.getText().toString());
                                int total = xA*xB;

                                text += "\nThuật toán Diffie-Hellman :\n";
                                text += "\nKhóa công khai yA:\nyA = a ^ xA mod q \n     = " + a + " ^ " + xA + " mod " + q + "\n     = " + Modulo.simplify(a,xA,q) + "\n";
                                text += "\nKhóa công khai yB:\nyB = a ^ xB mod q \n     = " + a + " ^ " + xB + " mod " + q + "\n     = " + Modulo.simplify(a,xB,q) + "\n";
                                text += "\nKhóa phiên K:\nK = a ^ (xA * xB) mod q \n    = " + a + " ^ " + total + " mod " + q + "\n     = " + Modulo.simplify(a,total,q) + "\n";

                                infor.setText(text);
                                infor.setVisibility(View.VISIBLE);
                            }
                        });
                        break;
                    case R.id.rb_elgamal:
                        ll_k.setVisibility(View.VISIBLE);
                        ll_M.setVisibility(View.VISIBLE);
                        calc.setOnClickListener(v -> {
                            if (validateElGa()) {
                                String text = "";

                                int q = Integer.parseInt(ed_q.getText().toString());
                                int a = Integer.parseInt(ed_a.getText().toString());
                                int xA = Integer.parseInt(ed_xA.getText().toString());
                                int k = Integer.parseInt(ed_k.getText().toString());
                                int M = Integer.parseInt(ed_M.getText().toString());
                                long yA = Modulo.simplify(a,xA,q);
                                text += "yA = a ^ xA mod q\n     =" + a + " ^ " + xA + " mod " + q + "\n     = " + yA + "\n";
                                text += "==> Khóa công khai của An :\nPU = {q, a, yA} = {" + q + ", " + a + ", " + yA+ "}\n";

                                //K = (yA)^k mod q
                                long K = Modulo.simplify(yA,k,q);
                                text += "\nKhi Ba gửi tin nhắn :\nK = yA ^ k mod q\n    = " + yA + " ^ " + k + " mod " + q + "\n    = " + K + "\n";

                                long C1 = Modulo.simplify(a,k,q);
                                text += "\nC1 = a ^ k mod q";
                                text += "\n     = " + a + " ^ " + k + " mod " + q;
                                text += "\n     = " + C1;
                                long C2 = (K*M)%q;
                                text += "\nC2 = K * M mod q";
                                text += "\n     = " + K + " * " + M + " mod " + q;
                                text += "\n     = " + C2;

                                text += "\n\nBản mã (C1, C2) = (" + C1 + ", " + C2 + ")"+"\n";

                                long kC=Modulo.simplify(C1,xA,q);
                                text += "\nKhi An giải mã :\nK = C1 ^ xA mod q\n    = " + C1 + " ^ " + xA + " mod " + q + "\n    = " + kC + "\n";

                                text += "\nM = (C2 * K^-1) mod q\n";
                                text += "    = (C2 mod q * K^-1 mod q) mod q\n";
                                text += "    = (   M1    *     M2    ) mod q\n\n";

                                long M1 = C2%q;
                                long M2 = Modulo.extendedEuclid(kC,q);
                                text += "Kết quả : M = " + (M1*M2)%q;


                                infor.setText(text);
                                infor.setVisibility(View.VISIBLE);
                            }
                        });
                        break;

                }
            }
        });





    }

    private boolean validateElGa() {
        if(!check(ed_q) || !check(ed_a) || !check(ed_xA) || !check(ed_k) || !check(ed_M))
            return false;
        return true;
    }

    private boolean validateDifHel() {
        if(!check(ed_q) || !check(ed_a) || !check(ed_xA) || !check(ed_xB))
            return false;
        return true;
    }

    private boolean check(EditText a){
        if(a.getText().toString().equals(""))
        {
            a.setError("Không được để trống");
            return false;
        }
        if(a.getText().toString().equals("0"))
        {
            a.setError("Phải khác 0");
            return false;
        }
        return true;
    }
}