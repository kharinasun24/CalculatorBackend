using System;
using System.Linq;
using CalculatorBackend.ifc;

public class AddSubtractProcessor : IfcMathExpression
{
    private readonly IfcMathExpression _decoratedExpression;

    public AddSubtractProcessor(IfcMathExpression decoratedExpression)
    {
        _decoratedExpression = decoratedExpression;
    }

    public string Compute(string term)
    {
        term = term.Replace("+-", "-").Replace("--", "+");
        term = _decoratedExpression.Compute(term); // Zuerst * und / berechnen

        if (!(term.Contains("+") || term.Contains("-")))
        {
            return term;
        }

        string[] numbers = term.Split(new char[] { '+', '-', '*', '/' }, StringSplitOptions.RemoveEmptyEntries);
        string[] operators = term.Where(c => "+-*/".Contains(c)).Select(c => c.ToString()).ToArray();

        double result = double.Parse(numbers[0]);
        for (int i = 1; i < numbers.Length; i++)
        {
            if (operators[i - 1] == "+")
            {
                result += double.Parse(numbers[i]);
            }
            else if (operators[i - 1] == "-")
            {
                result -= double.Parse(numbers[i]);
            }
        }

        return result.ToString();
    }
}
