package com.artechra;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.ResultSender;

import java.util.Properties;
import java.util.logging.Logger;

public class SimpleFunction extends FunctionAdapter implements Declarable {

    Logger LOG = Logger.getLogger("com.artechra.SimpleFunction") ;

    @Override
    public void execute(FunctionContext functionContext) {
        LOG.info("SimpleFunction.execute(" + functionContext + ")") ;
        ResultSender<String> rs = functionContext.getResultSender() ;

        Object argsObj = functionContext.getArguments() ;
        if (argsObj instanceof java.lang.Integer) {
            int i = 0 ;
            for (i=1; i < (Integer)argsObj; i++) {
                rs.sendResult(formatResult(i));
            }
            rs.lastResult(formatResult(i));
        }  else {
            rs.sendException(new IllegalArgumentException("SimpleFunction expects an Integer argument"));
        }
    }

    private String formatResult(int i) {
        return "SimpleFunctionResult(" + System.currentTimeMillis() + "," + i + ")" ;
    }

    @Override
    public String getId() {
        return "com::artechra::SimpleFunction" ;
    }

    @Override
    public boolean optimizeForWrite() {
        return false;
    }

    @Override
    public void init(Properties properties) {
        System.out.println("WARNING: unused properties passed to function '" + getId() + "': " + properties) ;
    }
}
