package com.alone.sell;

import org.junit.Test;

public class Test123 {

    @Test
    public void t343() {
        int[] a = new int[41];
        a[0] = 0;
        a[1] = 1;
        System.out.println(a[0]);
        System.out.println(a[1]);
        for (int i = 2; i < 40; i++) {
            a[i] = a[i - 1] + a[i - 2];
            System.out.println(a[i]);
        }
    }


    @Test
    public void t2() {
        for (int i = 0; i < 40; i++) {
            System.out.println(t2_found(i));
        }
    }

    private int t2_found(int i) {
        if (i < 2) {
            return i == 0 ? 0 : 1;
        }
        return t2_found(i - 1) + t2_found(i - 2);

    }


}
