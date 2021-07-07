package com.lx2td.myapplication.modulo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lx2td.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SEquationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SEquationsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText ed_a1, ed_a2, ed_a3, ed_m1, ed_m2, ed_m3, ed_x;
    Button calc, clear;
    TextView infor;

    public SEquationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SEquationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SEquationsFragment newInstance(String param1, String param2) {
        SEquationsFragment fragment = new SEquationsFragment();
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

        View view = inflater.inflate(R.layout.fragment_s_equations, container, false);

        ed_a1 = view.findViewById(R.id.ed_equa_a1);
        ed_a2 = view.findViewById(R.id.ed_equa_a2);
        ed_a3 = view.findViewById(R.id.ed_equa_a3);

        ed_m1 = view.findViewById(R.id.ed_equa_m1);
        ed_m2 = view.findViewById(R.id.ed_equa_m2);
        ed_m3 = view.findViewById(R.id.ed_equa_m3);

        ed_x = view.findViewById(R.id.ed_equa_x);

        calc = view.findViewById(R.id.btn_equa_calc);
        clear = view.findViewById(R.id.btn_equa_clear);
        infor = view.findViewById(R.id.tv_equa_infor);

        calc.setOnClickListener(v -> {
            if (validate()) {
                int a1 = Integer.parseInt(ed_a1.getText().toString());
                int a2 = Integer.parseInt(ed_a2.getText().toString());
                int a3 = Integer.parseInt(ed_a3.getText().toString());
                int m1 = Integer.parseInt(ed_m1.getText().toString());
                int m2 = Integer.parseInt(ed_m2.getText().toString());
                int m3 = Integer.parseInt(ed_m3.getText().toString());
                String text = "";

                ed_x.setText(Modulo.solveWithChineseRemainderTheorem(m1, m2, m3, a1, a2, a3) + "");

                List<Long> M = new ArrayList<>();
                List<Long> C = new ArrayList<>();

                M.add((long) (m2 * m3));
                M.add((long) (m1 * m3));
                M.add((long) (m1 * m2));

                text += "M[1] = " + m2 + " x " + m3 +  " = " + M.get(0) + "\n";
                text += "M[2] = " + m1 + " x " + m3 +  " = " + M.get(1) + "\n";
                text += "M[3] = " + m1 + " x " + m2 +  " = " + M.get(2) + "\n\n";

                C.add(M.get(0)*Modulo.extendedEuclid(M.get(0),m1));
                C.add(M.get(1)*Modulo.extendedEuclid(M.get(1),m2));
                C.add(M.get(2)*Modulo.extendedEuclid(M.get(2),m3));

                text += "C[i] = M[i] x (M[i] ^ -1 mod mi) :\n\n";

                text += "C[1] = " + M.get(0) + " x " + Modulo.extendedEuclid(M.get(0),m1) +  " = " + C.get(0) + "\n";
                text += "C[2] = " + M.get(1) + " x " + Modulo.extendedEuclid(M.get(1),m2) +  " = " + C.get(1) + "\n";
                text += "C[3] = " + M.get(2) + " x " + Modulo.extendedEuclid(M.get(2),m3) +  " = " + C.get(2) + "\n\n";

                text += "Total += ai * C[i]\n\n";

                long total = 0;
                total = a1* C.get(0) + a2 * C.get(1) + a3 * C.get(2);

                text += "Total = " + a1* C.get(0) + " + " + a2 * C.get(1) + " + " + a3 * C.get(2) + " = " + total +"\n";

                text += "X = Total mod (m1 x m2 x m3)\n";
                text += "X = " + total + " mod (" + m1 + " x " + m2 + " x " + m3 + ") = " + Modulo.simplify(total,1,m1*m2*m3);

                infor.setText(text);
                infor.setVisibility(View.VISIBLE);
            }
        });

        clear.setOnClickListener(v -> {
            ed_a1.setText("");
            ed_a2.setText("");
            ed_a3.setText("");
            ed_m1.setText("");
            ed_m2.setText("");
            ed_m3.setText("");
            ed_x.setText("");
            infor.setText("");
            infor.setVisibility(View.GONE);
        });



        // Inflate the layout for this fragment
        return view;
    }

    private boolean validate() {
        if(!check(ed_a1) || !check(ed_a2) || !check(ed_a3) || !check(ed_m1) || !check(ed_m2) || !check(ed_m3))
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