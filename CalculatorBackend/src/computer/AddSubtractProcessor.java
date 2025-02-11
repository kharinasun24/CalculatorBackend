package computer;

import java.util.Arrays;

import ifc.IfcMathExpression;

public class AddSubtractProcessor implements IfcMathExpression {

	private final IfcMathExpression decoratedExpression;

    public AddSubtractProcessor(IfcMathExpression decoratedExpression) {
        this.decoratedExpression = decoratedExpression;
    }

    @Override
    public String compute(String term) {
    	
    	term = term.replace("+-", "-").replace("--", "+");
    	
        term = decoratedExpression.compute(term); // Zuerst * und / berechnen

        if (!(term.contains("+") || term.contains("-"))) {
            return term;
        }

        //String regex = "(?<=[+*/])-|(?=[+*/])|(?<=\\d)-(?=\\d)|(?<=\\()|(?=\\))";
        //String[] numbers = term.split(regex);
        String[] numbers = term.split("[+\\-*/]");

        
        numbers = Arrays.stream(numbers)
                .filter(s -> !s.isEmpty()) // Entfernt leere Strings
                .toArray(String[]::new);

        
        String[] operators = term.replaceAll("[0-9.]", "").split("");

        double result = Double.parseDouble(numbers[0]);
        for (int i = 1; i < numbers.length; i++) {
            if (operators[i - 1].equals("+")) {
                result += Double.parseDouble(numbers[i]);
            } else if (operators[i - 1].equals("-")) {
                result -= Double.parseDouble(numbers[i]);
            }
        }

        return Double.toString(result);
    }
}
