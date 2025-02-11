package computer;

import auxiliary.Result;
import core.Main;
import ifc.IfcExpressionProcessor;

public class ComputeDecorator implements IfcExpressionProcessor {
	
	
    @Override
    public String process(String expression) {
        
    	Result result = Main.splitExpression(expression);
        
        if (!result.middle.isEmpty()) {
            String middle = Main.doChunking(result.middle);
            return Main.compute(result.left + Main.compute(middle) + result.right);
        } else {
            return Main.compute(result.left);
        }
    }
}
