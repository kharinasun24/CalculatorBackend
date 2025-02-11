package core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import auxiliary.Result;
import computer.ComputeDecorator;
import computer.AddSubtractProcessor;
import computer.MultiplyDivideProcessor;
import computer.EvaluateBracketedPowersDecorator;
import computer.ReplacePowersDecorator;
import ifc.IfcExpressionProcessor;
import ifc.IfcMathExpression;

 class Term {

    private static Term instance;

    private Term() {
    }

    public static synchronized Term getInstance() {
        if (instance == null) {
            instance = new Term();
        }
        return instance;
    }

    public String calculate(String term) {

    	  IfcMathExpression processor = new AddSubtractProcessor(new MultiplyDivideProcessor());
          return processor.compute(term);
 
    }
}


public class Main {

	
    public static String doChunking(String str) {
        while (str.contains("(")) {
            int openIndex = -1;
            int closeIndex = -1;
            int depth = 0;

            // Finde die innerste Klammer
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '(') {
                    if (depth == 0) openIndex = i;
                    depth++;
                }
                if (str.charAt(i) == ')') {
                    depth--;
                    if (depth == 0) {
                        closeIndex = i;
                        break;
                    }
                }
            }

            if (openIndex == -1 || closeIndex == -1) break; // Sicherheit

            String innerExpr = str.substring(openIndex + 1, closeIndex);
            String result = compute(innerExpr); // Berechne innersten Ausdruck

            // Ersetze in der ursprünglichen Zeichenkette
            str = str.substring(0, openIndex) + result + str.substring(closeIndex + 1);
        }
        return str;
    }

	
	 public static Result splitExpression(String s) {
	        int openIndex = s.indexOf('(');
	        if (openIndex == -1) return new Result(s, "", ""); // Falls keine Klammer vorhanden ist

	        int count = 0;
	        int closeIndex = -1;

	        for (int i = openIndex; i < s.length(); i++) {
	            if (s.charAt(i) == '(') count++;
	            if (s.charAt(i) == ')') count--;

	            if (count == 0) {
	                closeIndex = i;
	                break;
	            }
	        }

	        if (closeIndex == -1) return new Result(s, "", ""); 

	        String left = s.substring(0, openIndex);
	        String middle = s.substring(openIndex + 1, closeIndex);
	        String right = s.substring(closeIndex + 1);

	        return new Result(left, middle, right);
	    }

	 public static String evaluateBracketedPowers(String input) {
		    Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)\\*\\*\\(([^)]+)\\)");
		    Matcher matcher = pattern.matcher(input);

		    StringBuffer sb = new StringBuffer();
		    while (matcher.find()) {
		        double base = Double.parseDouble(matcher.group(1));
		        String exponentExpr = matcher.group(2);
		        
		        // Berechne den Exponenten Y
		        double exponent = Double.parseDouble(compute(exponentExpr));
		        
		        // Berechne das Ergebnis der Potenzierung
		        double result = Math.pow(base, exponent);
		        
		        matcher.appendReplacement(sb, String.valueOf(result));
		    }
		    matcher.appendTail(sb);
		    
		    return sb.toString();
		}

	
	 public static String replacePowers(String input) {
		 

	        Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)\\*\\*(\\d+(?:\\.\\d+)?)");
	        Matcher matcher = pattern.matcher(input);

	        StringBuffer sb = new StringBuffer();
	        while (matcher.find()) {
	            double base = Double.parseDouble(matcher.group(1));
	            double exponent = Double.parseDouble(matcher.group(2));
	            double result = Math.pow(base, exponent);
	            matcher.appendReplacement(sb, String.valueOf(result));
	        }
	        matcher.appendTail(sb);
	        return sb.toString();
	    }
	 
    public static String compute(String expression) {

    	   Term termInstance = Term.getInstance();
           return termInstance.calculate(expression);

    }

    
    private static String processExpression(String expression) {
        IfcExpressionProcessor processor = 
            new ReplacePowersDecorator( 
                new EvaluateBracketedPowersDecorator( 
                    new ComputeDecorator() 
                )
            );

        return processor.process(expression);
    }


   
    
    public static String preprocessNegativeNumbers(String expression) {

		boolean isContainsWrongOperands = expression.matches(".*(--{2,}|\\+{2,}|/{2,}).*");
		
		if(isContainsWrongOperands) {
			throw new NumberFormatException("Incorrect operands or the order of the operands is not accepted by this parser.");
		}

    	
    	expression = expression.replace("--", "+"); 
    	
    	return expression.replaceAll("(?<=\\()-", "0-");
    }
   
    
    public static String init(String term) {

      String processedTerm = preprocessNegativeNumbers(term);
      
  	  String finalResult = processExpression(processedTerm);
  	  
  	  return finalResult;
  }




}
