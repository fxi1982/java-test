package com.cisco.fxi.jvm.oom;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fxi on 5/14/16.
 */
public class JvmStackOOM {
    static final int CONST_STACK_DEPTH = 1000;

    public static void main(String[] args) {
        List<Thread> oomThreads = new LinkedList<>();

        try {
            while (true) {
                Thread thread = new Thread(new OOMThread());
                thread.start();
                oomThreads.add(thread);
            }
        } catch (Throwable t) {
            System.out.println("Thread Number: " + oomThreads.size());
            throw t;
        }
    }


    static class OOMThread implements Runnable {
        private int stackDepth = 1;

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            stackAlloc();
        }

        private void stackAlloc() {
            // increase stack frame size by defining a lot primitive local variable.
            long a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z;
            long A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z;
            stackDepth++;

            if (stackDepth < CONST_STACK_DEPTH) {
                stackAlloc();
            } else {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        //ignore
                    }
                }
            }
        }
    }
}
