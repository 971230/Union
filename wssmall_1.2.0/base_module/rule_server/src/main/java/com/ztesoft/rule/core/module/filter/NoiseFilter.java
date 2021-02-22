package com.ztesoft.rule.core.module.filter;

import com.ztesoft.rule.core.module.fact.DefFact;
import com.ztesoft.rule.core.module.comm.Error;

/**
 *
 *举例说明：
 *
 */
public class NoiseFilter implements IFactFilter{
	
    @Override
	public void doFilter(DefFact fact, FactFilterChain chain) {
    	//do something
    	if(true){
    		fact.setValidFlag(false) ;
    		fact.addError(Error.IFactFilter_code, "error_code", "xx error") ;
    	}
    	
    	chain.doFilter(fact, chain) ;
    }
}