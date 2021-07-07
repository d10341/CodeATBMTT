package com.lx2td.myapplication.modulo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Modulo {
    public static long gcd(long a, long b){
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).longValue();
    }

    static boolean isPrime(long n)
    {
        if (n <= 1)
            return false;
        for (long i = 2; i < n; i++)
            if (n % i == 0)
                return false;
        return true;
    }

    public static List<Long> primeFactors(long n)
    {
        int i = 2;
        List<Long> listNumbers = new ArrayList<>();
        while (n > 1) {
            if (n % i == 0) {
                n = n / i;
                listNumbers.add((long) i);
            } else {
                i++;
            }
        }
        if (listNumbers.isEmpty()) {
            listNumbers.add(n);
        }
        return listNumbers;
    }

    public static long simplify(long a, long m, long n){
        long r, res;
        res = 1;
        r = a % n;
        while (m > 0)
        {
            if(m%2==1)
                res = (res * r) % n;
            r = (r * r) %n;
            m = m/2;
        }
        return res;
    }

    public static long phi(long n){
        long result = 1;
        for (long i=2; i<n;i++){
            if (gcd(i,n) == 1)
                result += 1;
        }
        return result;
    }

    public static long euler(long a, long m, long n){
        long res = -1;

        if(gcd(a,n) == 1 && phi(n)<=m){
            if(m==phi(n))
                return 1;
            else{
                m = m % phi(n);
                res = simplify(a,m,n);
            }
        }
        return res;
    }

    public static long fermat(long a, long m, long n){
        if(gcd(a,n)==1 && isPrime(n)==true){
            if(m>n){
                m=m%(n-1);
                return simplify(a,m,n);
            }
            else if(m==(n-1))
                return  1;
            else if(m==n)
                return a;
        }
        return -1;
    }

    public static long extendedEuclid(long a, long n){
        long q;
        long a1 = 1, a2 = 0, a3 = n;
        long b1 = 0, b2 = 1, b3 = a;
        long t1, t2, t3;
        while(b3 > 1)
        {
            q = a3/b3;

            t1 = a1 - q*b1;
            t2 = a2 - q*b2;
            t3 = a3 - q*b3;

            a1 = b1;
            a2 = b2;
            a3 = b3;

            b1 = t1;
            b2 = t2;
            b3 = t3;
        }
        if(b3 == 0){
            return -1;
        }
        else{
            if(b2 > 0)
                return b2;
            else
                return n+b2;
        }
    }

    public static List<Long> euclidList(long a, long n){
        List<Long> euclid_list = new ArrayList<>();
        long q;
        long a1 = 1, a2 = 0, a3 = n;
        long b1 = 0, b2 = 1, b3 = a;
        long t1, t2, t3;
        while(b3 > 1)
        {
            q = a3/b3;

            t1 = a1 - q*b1;
            t2 = a2 - q*b2;
            t3 = a3 - q*b3;

            a1 = b1;
            a2 = b2;
            a3 = b3;

            b1 = t1;
            b2 = t2;
            b3 = t3;

            euclid_list.add(q);
            euclid_list.add(a1);
            euclid_list.add(a2);
            euclid_list.add(a3);
            euclid_list.add(b1);
            euclid_list.add(b2);
            euclid_list.add(b3);
        }
        return euclid_list;
    }

    public static long chineseRemainderTheorem(long A, long k, long n){
        List<Long> m;
        List<Long> a = new ArrayList<>();
        List<Long> M = new ArrayList<>();
        List<Long> C = new ArrayList<>();

        m=primeFactors(n);

        int size = m.size();

        
        for(int i = 0; i<size;i++){
            a.add((long) simplify(A,k,m.get(i)));
        }

        //Tinh Mi
        for(int i = 0; i<size;i++){
            M.add((long) (n/m.get(i)));
        }

        //Tinh Ci
        for(int i = 0; i<size;i++){
            if(extendedEuclid(M.get(i), m.get(i)) != 0)
            {
                C.add(M.get(i) * extendedEuclid(M.get(i), m.get(i)));
            }
            else return -1;
        }

        //Tinh tong va mod n
        int total = 0;
        for(int i = 0;i<size;i++){
            total+=a.get(i)*C.get(i);
        }



        return simplify(total,1,n);
    }

    public static boolean primRoot(long a,long mod){
        if(gcd(a,mod)!=1) return false;
        long t = phi(mod);
        List<Long> primes;
        primes = primeFactors(t);
        int size = primes.size();
        for (int i = 0; i<size;i++)
        {
            if (simplify(a,t/primes.get(i),mod) == 1)
            {
                return false;
            }
        }
        return true;
    }

    public static int findLog(int a, int b, int n){
        if(primRoot(a,n)==true){
            for(int i=0;i<n;i++){
                if(simplify(a,i,n)==b){
                    return i;
                }
            }
        }
        return -1;
    }

    public static long solveWithChineseRemainderTheorem(int m1, int m2, int m3, int a1, int a2, int a3){
        List<Long> M = new ArrayList<>();
        List<Long> C = new ArrayList<>();

        M.add((long) (m2 * m3));
        M.add((long) (m1 * m3));
        M.add((long) (m1 * m2));

        C.add(M.get(0)*extendedEuclid(M.get(0),m1));
        C.add(M.get(1)*extendedEuclid(M.get(1),m2));
        C.add(M.get(2)*extendedEuclid(M.get(2),m3));

        long total = 0;
        total = a1* C.get(0) + a2 * C.get(1) + a3 * C.get(2);
        
        return simplify(total,1,m1*m2*m3);
    }

}
