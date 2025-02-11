package computer;

import java.util.Arrays;

import ifc.IfcMathExpression;

public class MultiplyDivideProcessor implements IfcMathExpression {
    @Override
    public String compute(String term) {
    	
    	//term = term.replace("+-", "-").replace("--", "+"); //So etwas muss man nicht machen mit Punktoperatoren.
    	
        if (!(term.contains("/") || term.contains("*"))) {

        	return term;
        
        }

        //String regex = "(?<=[+*/])-|(?=[+*/])|(?<=\\d)-(?=\\d)|(?<=\\()|(?=\\))";
        //String[] numbers = term.split(regex);
        String[] numbers = term.split("[+\\-*/]");
        
        numbers = Arrays.stream(numbers)
                .filter(s -> !s.isEmpty()) // Entfernt leere Strings
                .toArray(String[]::new);

        
        String[] operators = term.replaceAll("[0-9.]", "").split("");

        for (int i = 0; i < operators.length; i++) {
            if (operators[i].equals("/") || operators[i].equals("*")) {
                String tmpResult = calculate(numbers[i], operators[i], numbers[i + 1]);
                String newTerm = rebuildTerm(numbers, operators, i, tmpResult);
                return compute(newTerm); // Rekursion für verbleibende * und /
            }
        }

        return term;
    }

    private String calculate(String num1Str, String operator, String num2Str) {
        double num1 = Double.parseDouble(num1Str);
        double num2 = Double.parseDouble(num2Str);
        double result = operator.equals("/") ? num1 / num2 : num1 * num2;
        return Double.toString(result);
    }

    private String rebuildTerm(String[] numbers, String[] operators, int index, String result) {
        StringBuilder newTerm = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            if (i == index) {
                newTerm.append(result);
                i++;
            } else {
                newTerm.append(numbers[i]);
            }
            if (i < operators.length && i != index) {
                newTerm.append(operators[i]);
            }
        }
        return newTerm.toString();
    }
}