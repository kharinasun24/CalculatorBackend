package computer;

import core.Main;
import ifc.IfcExpressionProcessor;

public class EvaluateBracketedPowersDecorator implements IfcExpressionProcessor {
    private final IfcExpressionProcessor next;
    
    public EvaluateBracketedPowersDecorator(IfcExpressionProcessor next) {
        this.next = next;
    }

    
    @Override
    public String process(String expression) {
        expression = Main.evaluateBracketedPowers(expression);
        return next.process(expression);
    }
}
