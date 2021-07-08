package com.lx2td.myapplication.signature;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lx2td.myapplication.R;
import com.lx2td.myapplication.modulo.Modulo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DSAFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DSAFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText ed_q, ed_p, ed_h, ed_xA, ed_k, ed_hm;
    TextView infor;
    Button calc;

    public DSAFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DSAFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DSAFragment newInstance(String param1, String param2) {
        DSAFragment fragment = new DSAFragment();
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
        View view = inflater.inflate(R.layout.fragment_d_s_a, container, false);

        ed_p = view.findViewById(R.id.ed_dsa_p);
        ed_q = view.findViewById(R.id.ed_dsa_q);
        ed_h = view.findViewById(R.id.ed_dsa_h);
        ed_xA = view.findViewById(R.id.ed_dsa_xA);
        ed_k = view.findViewById(R.id.ed_dsa_k);
        ed_hm = view.findViewById(R.id.ed_dsa_hm);

        calc = view.findViewById(R.id.btn_dsa_calc);

        infor = view.findViewById(R.id.tv_dsa_infor);

        calc.setOnClickListener(v1 -> {
            if (validate()) {
                String text = "";

                String endl = "\n";

                int p = Integer.parseInt(ed_p.getText().toString());
                int q = Integer.parseInt(ed_q.getText().toString());
                int h = Integer.parseInt(ed_h.getText().toString());
                int xA = Integer.parseInt(ed_xA.getText().toString());
                int k = Integer.parseInt(ed_k.getText().toString());
                int hm = Integer.parseInt(ed_hm.getText().toString());
                
                int calcG = (p-1)/q;
                long g = Modulo.simplify(h,calcG,p);
                text += "\ng = h ^ ((p - 1) / q) mod p \n   = " + h + " ^ " + calcG + " mod " + p + " \n   = " + g + endl;

                long y = Modulo.simplify(g,xA,p);
                text += "\ny = g ^ xA mod p \n   = " + g + " ^ " + xA + " mod " + p + " \n   = " + y + endl + endl + endl;

                long r = Modulo.simplify(g,k,p)%q;
                text += "\nr = (g ^ k mod p) mod q \n   = (" + g + " ^ " + k + " mod " + p + ") mod " + q + " \n   = " + r + endl;

                long s1 = Modulo.extendedEuclid(k,q);
                long s2 = (hm+xA*r)%q;

                long s = (s1*s2)%q;
                text += "\ns = [k ^ -1 * (H(M) + xA * r)] mod q \n   = (k ^ -1 mod q) * [(H(M) + xA * r) mod q] \n   = (" + k + " ^ " + (-1)
                        + " mod " + q + ") * [("+ hm +" + " + xA + " * " + r + ") mod " + q + "] \n   = " + s1 + " * " + s2 + " \n   = " + s + endl + endl;

                text += "==> Chữ ký số : (r, s) = (" + r + ", " + s + ")"  + endl + endl + endl;

                // xac thuc lai de kiem tra v = r'
                int hmFake = hm;
                long sFake = s;
                long rFake = r;

                long w = Modulo.extendedEuclid(sFake,q);
                text += "\nw = s' ^ -1 mod q \n   = " + sFake + " ^ " + (-1) + " mod " + q + " \n   = " + w + endl;

                long u1 = (hmFake*w)%q;
                text += "\nu1 = (H(M)' * w) mod q \n   = (" + hmFake + " * " + w + ") mod " + q + " \n   = " + u1 + endl;
                long u2 = (rFake*w)%q;
                text += "\nu2 = (r' * w) mod q \n   = (" + rFake + " * " + w + ") mod " + q + " \n   = " + u2 + endl;
                long gu1 = Modulo.simplify(g,u1,p);
                long yu2 = Modulo.simplify(y,u2,p);
                long v = ((gu1*yu2)%p)%q;
                text += "\nv = [(g ^ u1 * y ^ u2) mod p] mod q \n   = [(g ^ u1 mod p) * (y ^ u2 mod p)] mod q \n   = [(" + g + " ^ " + u1 + " mod " +
                        p + ") * " + "(" + y + " ^ " + u2 + " mod " +
                        p + ")] mod " + q + " \n   = (" + gu1 + " * " + yu2 + ") mod " + q + " \n   = " + (gu1*yu2) + " mod " + q + " \n   = " + v + endl + endl;
                if(v==rFake)
                    text +=  "Do v : " + v + " = r' : " + rFake + " nên đúng";
                else
                    text +=  "Do v : " + v + " ≠ r' : " + rFake + " nên sai";



                text += endl + endl + endl + endl + endl + endl + endl + endl + endl + endl + endl + endl + endl;
                text +=  "Step 1: Các giá trị dùng chung.\n\t\tp="
                        + p + " : là số nguyên tố\n\t\tq=" + q
                        + " : là ước số nguyên tố của " + p
                        + "- 1\n\t\th=" + h + " là số nguyên thỏa mãn 1 < " + h
                        + " < (" + p + "-1) sao cho g = " + g + " > 1" + endl + endl;
                text +=  "Step 2: Người dùng.\n\t\tKhóa riêng : " + xA + " thỏa mãn 0 < " + xA + " < " + q + "\n\t\tKhóa công khai: y = " + y + "\n\t\tSố bí mật k = " + k + " thỏa mãn 0 < " + k + " < q " + "= " + q + endl + endl;
                text +=  "Step 3: Chữ kí số : \n\t\tr = " + r + "\n\t\ts = " + s + "\n\t\tChữ kí số : (" + r + " , " + s + ")" + endl;
                text +=  endl + "w = " + w + " || u1 = " + u1 + " || u2 = " + u2 + " || gu1 = " + gu1 + " || yu2 = " + yu2 + " || v = " + v + endl;
                if(v==rFake)
                    text +=  "Do v : " + v + " = r' : " + rFake + " nên đúng";
                else
                    text +=  "Do v : " + v + " ≠ r' : " + rFake + " nên sai";

                infor.setText(text);
                infor.setVisibility(View.VISIBLE);
            }
        });
        
        return view;
    }

    private boolean validate() {
        if(!check(ed_q) || !check(ed_p) || !check(ed_h) || !check(ed_xA) || !check(ed_k) || !check(ed_hm))
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