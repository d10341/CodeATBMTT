package com.lx2td.myapplication.modulo;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.lx2td.myapplication.R;
import com.lx2td.myapplication.classical.ClassicalActivity;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModuloCalcFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModuloCalcFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText a, m, n, result;
    Button btn_calc;
    RadioGroup group;
    TextView infor;
    TableLayout table;


    public ModuloCalcFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModuloCalcFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModuloCalcFragment newInstance(String param1, String param2) {
        ModuloCalcFragment fragment = new ModuloCalcFragment();
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_modulo_calc, container, false);
        
        a = view.findViewById(R.id.ed_a);
        m = view.findViewById(R.id.ed_m);
        n = view.findViewById(R.id.ed_n);
        result = view.findViewById(R.id.ed_result);
        btn_calc = view.findViewById(R.id.btn_calc);
        group = view.findViewById(R.id.rg_modulo);
        infor = view.findViewById(R.id.tv_infor);
        table = view.findViewById(R.id.tb_euclid);

        if (group!= null)
        {
            group.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    a.setText("");
                    m.setText("");
                    n.setText("");
                    result.setText("");
                    infor.setText("");
                    table.removeAllViewsInLayout();

                    infor.setTextSize(28);

                    m.setEnabled(true);

                    infor.setVisibility(View.GONE);

                    switch (checkedId) {
                        case R.id.rb_simplify:
                            btn_calc.setOnClickListener(v ->{
                                if(validate())
                                {
                                    result.setText(String.valueOf(Modulo.simplify(Long.parseLong(a.getText().toString()), Long.parseLong(m.getText().toString()), Long.parseLong(n.getText().toString()))));
                                }
                            });
                            break;
                        case R.id.rb_fermat:
                            btn_calc.setOnClickListener(v ->{
                                if(validate())
                                {
                                    String text = "";
                                    if (Modulo.fermat(Long.parseLong(a.getText().toString()), Long.parseLong(m.getText().toString()), Long.parseLong(n.getText().toString())) != -1)
                                    {
                                        result.setText(String.valueOf(Modulo.fermat(Long.parseLong(a.getText().toString()), Long.parseLong(m.getText().toString()), Long.parseLong(n.getText().toString()))));
                                        text = "Sau khi áp dụng định lý fermat nhỏ :\n" + m.getText().toString() + " % (" + n.getText().toString() + " - 1) = " + Long.parseLong(m.getText().toString())%(Long.parseLong(n.getText().toString())-1) + "\n" + a.getText().toString() + " ^ " + Long.parseLong(m.getText().toString())%(Long.parseLong(n.getText().toString())-1) + " mod " + n.getText().toString();
                                    }
                                    else {
                                        result.setText(String.valueOf(Modulo.simplify(Long.parseLong(a.getText().toString()), Long.parseLong(m.getText().toString()), Long.parseLong(n.getText().toString()))));
                                        text = "Không thể sử dụng định lý fermat nhỏ";
                                    }
                                    infor.setText(text);
                                    infor.setVisibility(View.VISIBLE);
                                }
                            });
                            break;
                        case R.id.rb_euler:
                            btn_calc.setOnClickListener(v ->{
                                if(validate())
                                {
                                    String text = "Phi(n) = " + Modulo.phi(Long.parseLong(n.getText().toString())) + "\n";
                                    if (Modulo.euler(Long.parseLong(a.getText().toString()), Long.parseLong(m.getText().toString()), Long.parseLong(n.getText().toString())) != -1)
                                    {
                                        result.setText(String.valueOf(Modulo.euler(Long.parseLong(a.getText().toString()), Long.parseLong(m.getText().toString()), Long.parseLong(n.getText().toString()))));
                                        text += "Sau khi áp dụng định lý euler :\n" + m.getText().toString() + " % " + Modulo.phi(Long.parseLong(n.getText().toString())) + " = " + Long.parseLong(m.getText().toString())%Modulo.phi(Long.parseLong(n.getText().toString())) + "\n" + a.getText().toString() + " ^ " + Long.parseLong(m.getText().toString())%Modulo.phi(Long.parseLong(n.getText().toString())) + " mod " + n.getText().toString();
                                    }
                                    else {
                                        result.setText(String.valueOf(Modulo.simplify(Long.parseLong(a.getText().toString()), Long.parseLong(m.getText().toString()), Long.parseLong(n.getText().toString()))));
                                        text += "Không thể sử dụng định lý euler";
                                    }
                                    infor.setText(text);
                                    infor.setVisibility(View.VISIBLE);
                                }
                            });
                            break;
                        case R.id.rb_chinese_remainder:
                            btn_calc.setOnClickListener(v ->{
                                if(validate())
                                {
                                    String text = "";
                                    if (Modulo.chineseRemainderTheorem(Long.parseLong(a.getText().toString()), Long.parseLong(m.getText().toString()), Long.parseLong(n.getText().toString())) != -1)
                                    {
                                        result.setText(String.valueOf(Modulo.chineseRemainderTheorem(Long.parseLong(a.getText().toString()), Long.parseLong(m.getText().toString()), Long.parseLong(n.getText().toString()))));
                                        long A = Long.parseLong(a.getText().toString());
                                        long k = Long.parseLong(m.getText().toString());
                                        long n1 = Long.parseLong(n.getText().toString());
                                        text += "A^k mod n\nĐịnh lý phần dư Trung Hoa :\n\nThừa số nguyên tố :\n\n";
                                        List<Long> tmpm = Modulo.primeFactors(n1);
                                        for(int i = 0; i < tmpm.size(); i ++){
                                            text += "m["+ (i+1) + "] = " + tmpm.get(i) + "\n";
                                        }
                                        List<Long> tmpa = new ArrayList<>();
                                        text += "\na[i] = A^k mod m[i] :\n\n";
                                        for(int i = 0; i < tmpm.size(); i ++){
                                            text += "a["+ (i+1) + "] = " + A + " ^ " + k + " mod " + tmpm.get(i) + " = " + Modulo.simplify(A,k,tmpm.get(i)) + "\n";
                                            tmpa.add(Modulo.simplify(A,k,tmpm.get(i)));
                                        }
                                        List<Long> tmpM = new ArrayList<>();
                                        text += "\nM[i] = n / m[i] :\n\n";
                                        for(int i = 0; i < tmpm.size(); i ++){
                                            text += "M["+ (i+1) + "] = " + n1 + " / " + tmpm.get(i) +  " = " + n1/tmpm.get(i) + "\n";
                                            tmpM.add(n1/tmpm.get(i));
                                        }
                                        List<Long> tmpC = new ArrayList<>();
                                        text += "\nC[i] = M[i] x (M[i] ^ -1 mod m[i]) :\n\n";
                                        for(int i = 0; i < tmpm.size(); i ++){
                                            text += "C["+ (i+1) + "] = " + tmpM.get(i) + " x (" + tmpM.get(i) + " ^ -1 mod " + tmpm.get(i) + ") = " + tmpM.get(i) * Modulo.extendedEuclid(tmpM.get(i), tmpm.get(i)) + "\n";
                                            tmpC.add(tmpM.get(i) * Modulo.extendedEuclid(tmpM.get(i), tmpm.get(i)));
                                        }

                                        text += "\nTotal += a[i] x C[i]\n";
                                        int total = 0;
                                        for(int i = 0;i<tmpm.size();i++){
                                            total+=tmpa.get(i)*tmpC.get(i);
                                        }
                                        text += "\n==> Total = " + total + "\n";
                                        text += "Result = Total mod n = " + total + " mod " + n1 + " = " + result.getText().toString();
                                    }
                                    else {
                                        result.setText(String.valueOf(Modulo.simplify(Long.parseLong(a.getText().toString()), Long.parseLong(m.getText().toString()), Long.parseLong(n.getText().toString()))));
                                        text += "Không thể sử dụng định lý phần dư Trung Hoa";
                                    }
                                    infor.setText(text);
                                    infor.setVisibility(View.VISIBLE);
                                }
                            });
                            break;
                        case R.id.rb_euclid:
                            m.setText("-1");
                            m.setEnabled(false);
                            btn_calc.setOnClickListener(v ->{
                                table.removeAllViewsInLayout();
                                if(validate())
                                {
                                    String text = "";
                                    List<Long> table = Modulo.euclidList(Long.parseLong(a.getText().toString()), Long.parseLong(n.getText().toString()));
                                    if (Modulo.extendedEuclid(Long.parseLong(a.getText().toString()), Long.parseLong(n.getText().toString())) != -1)
                                    {
                                        result.setText(String.valueOf(Modulo.extendedEuclid(Long.parseLong(a.getText().toString()), Long.parseLong(n.getText().toString()))));
                                        text = "Bảng Euclid mở rộng :\n\n";

                                        List<String> str = new ArrayList<>();
                                        str.add("Q");
                                        str.add("A1");
                                        str.add("A2");
                                        str.add("A3");
                                        str.add("B1");
                                        str.add("B2");
                                        str.add("B3");
                                        drawRow(str);

                                        str.clear();
                                        str.add("_");
                                        str.add("1");
                                        str.add("0");
                                        str.add(n.getText().toString());
                                        str.add("0");
                                        str.add("1");
                                        str.add(a.getText().toString());
                                        drawRow(str);

                                        int c = 0;
                                        List<Long> row = new ArrayList<>();
                                        for(int i = 0; i < table.size(); i++){
                                            if(c == 7)
                                            {
                                                drawRow(row);
                                                c = 0;
                                                row.clear();
                                            }
                                            row.add(table.get(i));
                                            c++;
                                        }
                                        drawRow(row);
                                    }
                                    else {
                                        result.setText("x");
                                        text += "Không có nghịch đảo :\n\n";

                                        List<String> str = new ArrayList<>();
                                        str.add("Q");
                                        str.add("A1");
                                        str.add("A2");
                                        str.add("A3");
                                        str.add("B1");
                                        str.add("B2");
                                        str.add("B3");
                                        drawRow(str);

                                        str.clear();
                                        str.add("_");
                                        str.add("1");
                                        str.add("0");
                                        str.add(n.getText().toString());
                                        str.add("0");
                                        str.add("1");
                                        str.add(a.getText().toString());
                                        drawRow(str);

                                        int c = 0;
                                        List<Long> row = new ArrayList<>();
                                        for(int i = 0; i < table.size(); i++){
                                            if(c == 7)
                                            {
                                                drawRow(row);
                                                c = 0;
                                                row.clear();
                                            }
                                            row.add(table.get(i));
                                            c++;
                                        }
                                        drawRow(row);
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

        ((RadioButton) view.findViewById(R.id.rb_simplify)).performClick();

        return view;
    }

    private boolean validate() {
        if(a.getText().toString().equals(""))
        {
            a.setError("Không được để trống");
            return false;
        }
        if(m.getText().toString().equals(""))
        {
            m.setError("Không được để trống");
            return false;
        }
        if(n.getText().toString().equals(""))
        {
            n.setError("Không được để trống");
            return false;
        }
        if(a.getText().toString().equals("0"))
        {
            a.setError("Phải khác 0");
            return false;
        }
        if(m.getText().toString().equals("0"))
        {
            m.setError("Phải khác 0");
            return false;
        }
        if(n.getText().toString().equals("0"))
        {
            n.setError("Phải khác 0");
            return false;
        }
        return true;
    }

    void drawRow(List<?> list){
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(0x00000000); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(0);
        gd.setStroke(1, 0xFF000000);

        TableRow row = new TableRow(getContext());

        for (int i = 0; i < list.size(); i++) {
            TextView cell = new TextView(getContext());
            cell.setText(""+list.get(i).toString());
            cell.setTextSize(14);
            cell.setWidth(150);
            cell.setHeight(150);
            cell.setBackground(gd);
            cell.setGravity(Gravity.CENTER);
            row.addView(cell);
        }

        table.addView(row);
    }
}