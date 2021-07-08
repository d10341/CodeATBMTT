package com.lx2td.myapplication.signature;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lx2td.myapplication.R;
import com.lx2td.myapplication.modulo.Modulo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RSAFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RSAFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText ed_q, ed_p, ed_e, ed_M;
    TextView infor;
    Button enc, dec;

    public RSAFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RSAFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RSAFragment newInstance(String param1, String param2) {
        RSAFragment fragment = new RSAFragment();
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
        View view = inflater.inflate(R.layout.fragment_r_s_a, container, false);

        ed_p = view.findViewById(R.id.ed_rsa_p);
        ed_q = view.findViewById(R.id.ed_rsa_q);
        ed_e = view.findViewById(R.id.ed_rsa_e);
        ed_M = view.findViewById(R.id.ed_rsa_M);

        enc = view.findViewById(R.id.btn_rsa_enc);
        dec = view.findViewById(R.id.btn_rsa_dec);

        infor = view.findViewById(R.id.tv_rsa_infor);

        enc.setOnClickListener(v -> {
            if (validate()) {
                String text = "";

                String endl = "\n";

                int p = Integer.parseInt(ed_p.getText().toString());
                int q = Integer.parseInt(ed_q.getText().toString());
                int e = Integer.parseInt(ed_e.getText().toString());
                int M = Integer.parseInt(ed_M.getText().toString());
                long n = q*p;
                text += "\nn = q * p \n   = " + q + " * " + p + " \n   = " + n + endl;
                long PhiN = (p-1)*(q-1);
                text += "\nø(n) = (q - 1) * (p - 1) \n        = " + (q-1) + " * " + (p-1) + " \n        = " + PhiN + endl;
                long d = Modulo.extendedEuclid(e,PhiN);
                text += "\nd = e ^ -1 mod ø(n) \n   = " + e + " ^ -1 mod " + PhiN + " \n   = " + d + endl;

                text += "\nPublic key: {e, n} = {" + e + ", " + n + "}" + endl;
                text += "Private key: {d, n} = {" + d + ", " + n + "}" + endl;

                long C = Modulo.simplify(M,d,n);

                text +=  "\nAn mã hóa: \nBản mã: \n C = M ^ d mod n \n     = " + M + " ^ " + d + " mod " + n + " \n     = " + C + endl + endl;
                text +=  "\nBa giải mã: \nBản rỏ: \n M = C ^ e mod n \n     = " + C + " ^ " + e + " mod " + n + " \n     = " + Modulo.simplify(C,e,n) + endl;

                infor.setText(text);
                infor.setVisibility(View.VISIBLE);
            }
        });

        dec.setOnClickListener(v -> {
            if (validate()) {
                String text = "";

                String endl = "\n";

                int p = Integer.parseInt(ed_p.getText().toString());
                int q = Integer.parseInt(ed_q.getText().toString());
                int e = Integer.parseInt(ed_e.getText().toString());
                int M = Integer.parseInt(ed_M.getText().toString());
                long n = q*p;
                text += "\nn = q * p \n   = " + q + " * " + p + " \n   = " + n + endl;
                long PhiN = (p-1)*(q-1);
                text += "\nø(n) = (q - 1) * (p - 1) \n        = " + (q-1) + " * " + (p-1) + " \n        = " + PhiN + endl;
                long d = Modulo.extendedEuclid(e,PhiN);
                text += "\nd = e ^ -1 mod ø(n) \n   = " + e + " ^ -1 mod " + PhiN + " \n   = " + d + endl;

                text += "\nPublic key: {e, n} = {" + e + ", " + n + "}" + endl;
                text += "Private key: {d, n} = {" + d + ", " + n + "}" + endl;

                long C = Modulo.simplify(M,e,n);

                text +=  "\nBa mã hóa: \nBản mã: \n C = M ^ e mod n \n     = " + M + " ^ " + e + " mod " + n + " \n     = " + C + endl + endl;
                text +=  "\nAn giải mã: \nBản rỏ: \n M = C ^ d mod n \n     = " + C + " ^ " + d + " mod " + n + " \n     = " + Modulo.simplify(C,d,n) + endl;

                infor.setText(text);
                infor.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private boolean validate() {
        if(!check(ed_q) || !check(ed_p) || !check(ed_e) || !check(ed_M))
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