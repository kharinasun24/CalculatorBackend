package computer;

import auxiliary.Result;
import core.Main;
import ifc.IfcExpressionProcessor;

class SplitExpressionDecorator implements IfcExpressionProcessor {
    @SuppressWarnings("unused")
	private final IfcExpressionProcessor next;

    
    public SplitExpressionDecorator(IfcExpressionProcessor next) {
        this.next = next;
    }

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
