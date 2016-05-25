package com.cisco.fxi.jvm.oom;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fxi on 5/14/16.
 */
public class HeapOOM {
    public static void main(String[] args) {
        List<OOMObject> list = new LinkedList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }

    static class OOMObject {
    }
}
