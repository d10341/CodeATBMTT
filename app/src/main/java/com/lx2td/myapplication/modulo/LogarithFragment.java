package com.lx2td.myapplication.modulo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lx2td.myapplication.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogarithFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogarithFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText log_a, log_b, log_n, log_result, prim_a, prim_n;
    Button btn_calc;
    RadioGroup group;
    TextView infor;
    ImageView prim_result;
    RelativeLayout prim, log;

    public LogarithFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogarithFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogarithFragment newInstance(String param1, String param2) {
        LogarithFragment fragment = new LogarithFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logarith, container, false);

        log_a = view.findViewById(R.id.ed_log_a);
        log_b = view.findViewById(R.id.ed_log_b);
        log_n = view.findViewById(R.id.ed_log_n);
        log_result = view.findViewById(R.id.ed_log_result);

        prim_a = view.findViewById(R.id.ed_prim_a);
        prim_n = view.findViewById(R.id.ed_prim_n);
        prim_result = view.findViewById(R.id.iv_prim_result);

        btn_calc = view.findViewById(R.id.btn_log_calc);

        group = view.findViewById(R.id.rg_logarith);

        infor = view.findViewById(R.id.tv_log_info);

        prim = view.findViewById(R.id.rl_primroot);
        log = view.findViewById(R.id.rl_logarith);

        prim_result.setImageResource(R.drawable.ic_baseline_panorama_fish_eye_24);

        if (group != null) {
            group.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    log_a.setText("");
                    log_b.setText("");
                    log_n.setText("");
                    log_result.setText("");

                    prim_a.setText("");
                    prim_n.setText("");
                    prim_result.setImageResource(R.drawable.ic_baseline_panorama_fish_eye_24);
                    prim_result.setBackgroundColor(0xFFFFFFFF);

                    infor.setText("");
                    infor.setTextSize(28);
                    infor.setVisibility(View.GONE);

                    prim.setVisibility(View.GONE);
                    log.setVisibility(View.GONE);

                    switch (checkedId) {
                        case R.id.rb_primroot:
                            prim.setVisibility(View.VISIBLE);
                            btn_calc.setOnClickListener(v -> {
                                if (validatePrime()) {
                                    String text = "";
                                    long a = Long.parseLong(prim_a.getText().toString());
                                    long mod = Long.parseLong(prim_n.getText().toString());
                                    if(Modulo.primRoot(a, mod) == true)
                                    {
                                        prim_result.setImageResource(R.drawable.ic_baseline_check_circle_24);
                                        prim_result.setBackgroundColor(0xFF3CD184);
                                    }
                                    else{
                                        prim_result.setImageResource(R.drawable.ic_baseline_cancel_24);
                                        prim_result.setBackgroundColor(0xFFFF4646);
                                    }
                                    if(Modulo.gcd(a,mod)!=1) text += a + " và " + mod  + " không nguyên tố cùng nhau";
                                    else{
                                        long t = Modulo.phi(mod);
                                        text += "Phi(" + mod + ") = " + t + "\n";
                                        List<Long> primes;
                                        primes = Modulo.primeFactors(t);
                                        int size = primes.size();
                                        text += "Thừa số nguyên tố : ";
                                        for(int i = 0; i < primes.size(); i ++){
                                            text += primes.get(i) + " ";
                                        }
                                        text += "\n\na ^ ø(n)/primes[i] mod n\n\n";

                                        for (int i = 0; i<size;i++)
                                        {
                                            text += a + " ^ " + t/primes.get(i) + " mod " + mod + " = " + Modulo.simplify(a,t/primes.get(i),mod) + "\n";
                                            if (Modulo.simplify(a,t/primes.get(i),mod) == 1)
                                            {
                                                text += "\n\ni = " + t/primes.get(i) + " < " + t + " = ø(" + mod + ") là số mũ nhỏ nhất có tính " +
                                                        "chất " + a + " ^ i mod " + mod + " = 1, nên " + a + " không là căn nguyên thủy" +
                                                        " của " + mod + "\n";
                                                break;
                                            }
                                        }
                                    }
                                    infor.setText(text);
                                    infor.setVisibility(View.VISIBLE);
                                }
                            });
                            break;
                        case R.id.rb_logarith:
                            log.setVisibility(View.VISIBLE);
                            btn_calc.setOnClickListener(v -> {
                                if (validateLog()) {
                                    String text = "";
                                    int a = Integer.parseInt(log_a.getText().toString());
                                    int b = Integer.parseInt(log_b.getText().toString());
                                    int n = Integer.parseInt(log_n.getText().toString());
                                    if(Modulo.findLog(a, b, n) != -1)
                                    {
                                        log_result.setText("" + Modulo.findLog(a, b, n));
                                        for(int i=0;i<n;i++){
                                            text += a + " ^ " + i + " mod " + n + " = " + Modulo.simplify(a,i,n) + "\n";
                                            if(Modulo.simplify(a,i,n)==b){
                                                text += "\n\nĐã tìm thấy tại i = " + i + "\ni thỏa" +
                                                        "mãn b ≡ a ^ i (mod n) với 0 <= m <= (n — 1) với :\n";
                                                text += "a = " + a + "\nb = " + b + "\nn = " + n;
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        log_result.setText("x");
                                        text += a + " không là căn nguyên thủy của " + n;
                                    }

                                    infor.setText(text);
                                    infor.setVisibility(View.VISIBLE);
                                }
                            });
                            break;

                    }
                }
            });
        }

        ((RadioButton) view.findViewById(R.id.rb_primroot)).performClick();

        return view;
    }

    private boolean validatePrime() {
        if(prim_a.getText().toString().equals(""))
        {
            prim_a.setError("Không được để trống");
            return false;
        }
        if(prim_n.getText().toString().equals(""))
        {
            prim_n.setError("Không được để trống");
            return false;
        }
        if(prim_a.getText().toString().equals("0"))
        {
            log_a.setError("Phải khác 0");
            return false;
        }
        if(prim_n.getText().toString().equals("0"))
        {
            prim_n.setError("Phải khác 0");
            return false;
        }
        return true;
    }

    private boolean validateLog() {
        if(log_a.getText().toString().equals(""))
        {
            log_a.setError("Không được để trống");
            return false;
        }
        if(log_b.getText().toString().equals(""))
        {
            log_b.setError("Không được để trống");
            return false;
        }
        if(log_n.getText().toString().equals(""))
        {
            log_n.setError("Không được để trống");
            return false;
        }
        if(log_a.getText().toString().equals("0"))
        {
            log_a.setError("Phải khác 0");
            return false;
        }
        if(log_b.getText().toString().equals("0"))
        {
            log_b.setError("Phải khác 0");
            return false;
        }
        if(log_n.getText().toString().equals("0"))
        {
            log_n.setError("Phải khác 0");
            return false;
        }
        return true;
    }
}