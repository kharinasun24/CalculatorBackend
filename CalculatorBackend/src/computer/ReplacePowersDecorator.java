package computer;

import core.Main;
import ifc.IfcExpressionProcessor;

public class ReplacePowersDecorator implements IfcExpressionProcessor {
    private final IfcExpressionProcessor next;
    
    public ReplacePowersDecorator(IfcExpressionProcessor next) {
        this.next = next;
    }

    @Override
    public String process(String expression) {
        expression = Main.replacePowers(expression);
        return next.process(expression);
    }
}
