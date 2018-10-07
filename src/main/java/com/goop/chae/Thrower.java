package com.goop.chae;

import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.exceptions.CRE.CRECastException;
import com.laytonsmith.core.exceptions.CRE.CREIllegalArgumentException;

public class Thrower {

    public static void throwCRECastException1(String type, int para, String func){
        throw new CRECastException("Expecting an " + type + " at parameter "+ para +" of "+ func, Target.UNKNOWN);
    }

    public static void throwCREIllegalException1(String type){
        throw new CREIllegalArgumentException("Invalid inventory type: "+type, Target.UNKNOWN);
    }
}
