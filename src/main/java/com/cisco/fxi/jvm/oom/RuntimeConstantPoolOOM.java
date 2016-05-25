package com.cisco.fxi.jvm.oom;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fxi on 5/14/16.
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        List<String> list = new LinkedList<>();

        int i = 0;
        while (true) {
            list.add(String.valueOf(i).intern());
        }
    }
}
