package com.artechra;

import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;

public class Caller extends AbstractBaseClient {

    public Caller(String name, String regionName) {
        super(name, regionName) ;
    }

    public void callFunction(String name) {
        //MultiGetFunction fn = new MultiGetFunction() ;
        ResultCollector rc = FunctionService.onRegion(getRegion()).
                                withArgs(new Integer(10)).
                                    execute(name);
        Object result = rc.getResult();
        System.out.println(result.getClass().getName() + " :: " + result) ;
    }

    public static void main(String[] args) {
        final String regionName = "numbersRegion" ;
        Caller caller = new Caller("Caller", regionName) ;
        caller.callFunction("com::artechra::SimpleFunction");
    }
}
