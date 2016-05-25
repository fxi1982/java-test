package com.cisco.fxi.jvm.oom;

/**
 * Created by fxi on 5/14/16.
 */
public class JvmStackSOF {
    private int stackDepth = 1;
    public void stackOverflow() {
        // increase stack frame size by defining a lot primitive local variable.
        long a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z;
        long A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z;
        stackDepth++;
        stackOverflow();
    }

    public static void main(String[] args) {
        JvmStackSOF sof = new JvmStackSOF();

        try {
            sof.stackOverflow();
        } catch (Throwable t) {
            System.out.println("Stack Depth: " + sof.stackDepth);
            throw t;
        }
    }

}
