using CalculatorBackend.ifc;

public class MultiplyDivideProcessor : IfcMathExpression
{
    public string Compute(string term)
    {
        if (!(term.Contains("/") || term.Contains("*")))
        {
            return term;
        }

        string[] numbers = term.Split(new char[] { '+', '-', '*', '/' }, StringSplitOptions.RemoveEmptyEntries);
        string[] operators = term.Where(c => "+-*/".Contains(c)).Select(c => c.ToString()).ToArray();

        for (int i = 0; i < operators.Length; i++)
        {
            if (operators[i] == "/" || operators[i] == "*")
            {
                string tmpResult = Calculate(numbers[i], operators[i], numbers[i + 1]);
                string newTerm = RebuildTerm(numbers, operators, i, tmpResult);
                return Compute(newTerm); // Rekursion für verbleibende * und /
            }
        }

        return term;
    }

    private string Calculate(string num1Str, string operatorStr, string num2Str)
    {
        double num1 = double.Parse(num1Str);
        double num2 = double.Parse(num2Str);
        double result = operatorStr == "/" ? num1 / num2 : num1 * num2;
        return result.ToString();
    }

    private string RebuildTerm(string[] numbers, string[] operators, int index, string result)
    {
        var newTerm = "";
        for (int i = 0; i < numbers.Length; i++)
        {
            if (i == index)
            {
                newTerm += result;
                i++;
            }
            else
            {
                newTerm += numbers[i];
            }
            if (i < operators.Length && i != index)
            {
                newTerm += operators[i];
            }
        }
        return newTerm;
    }
}
