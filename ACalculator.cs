using CalculatorBackend.auxiliary;
using CalculatorBackend.computer;
using CalculatorBackend.ifc;
using System.Text.RegularExpressions;

namespace CalculatorBackend
{
    class Term
    {
        private static Term instance;
        private Term() { }

        public static Term GetInstance()
        {
            if (instance == null)
            {
                instance = new Term();
            }
            return instance;
        }

        public string Calculate(string term)
        {
            IfcMathExpression processor = new AddSubtractProcessor(new MultiplyDivideProcessor());
            return processor.Compute(term);
        }
    }

    public class ACalculator
    {
        public static string DoChunking(string str)
        {
            while (str.Contains("("))
            {
                int openIndex = -1;
                int closeIndex = -1;
                int depth = 0;

                for (int i = 0; i < str.Length; i++)
                {
                    if (str[i] == '(')
                    {
                        if (depth == 0) openIndex = i;
                        depth++;
                    }
                    if (str[i] == ')')
                    {
                        depth--;
                        if (depth == 0)
                        {
                            closeIndex = i;
                            break;
                        }
                    }
                }

                if (openIndex == -1 || closeIndex == -1) break;

                string innerExpr = str.Substring(openIndex + 1, closeIndex - openIndex - 1);
                string result = Compute(innerExpr);

                str = str.Substring(0, openIndex) + result + str.Substring(closeIndex + 1);
            }
            return str;
        }

        public static Result SplitExpression(string s)
        {
            int openIndex = s.IndexOf('(');
            if (openIndex == -1) return new Result(s, "", "");

            int count = 0;
            int closeIndex = -1;

            for (int i = openIndex; i < s.Length; i++)
            {
                if (s[i] == '(') count++;
                if (s[i] == ')') count--;
                if (count == 0)
                {
                    closeIndex = i;
                    break;
                }
            }

            if (closeIndex == -1) return new Result(s, "", "");

            string left = s.Substring(0, openIndex);
            string middle = s.Substring(openIndex + 1, closeIndex - openIndex - 1);
            string right = s.Substring(closeIndex + 1);

            return new Result(left, middle, right);
        }

        public static string EvaluateBracketedPowers(string input)
        {
            Regex pattern = new Regex("(\\d+(?:\\.\\d+)?)\\*\\*\\(([^)]+)\\)");
            return pattern.Replace(input, match =>
            {
                double baseNum = double.Parse(match.Groups[1].Value);
                string exponentExpr = match.Groups[2].Value;
                double exponent = double.Parse(Compute(exponentExpr));
                return Math.Pow(baseNum, exponent).ToString();
            });
        }

        public static string ReplacePowers(string input)
        {
            Regex pattern = new Regex("(\\d+(?:\\.\\d+)?)\\*\\*(\\d+(?:\\.\\d+)?)");
            return pattern.Replace(input, match =>
            {
                double baseNum = double.Parse(match.Groups[1].Value);
                double exponent = double.Parse(match.Groups[2].Value);
                return Math.Pow(baseNum, exponent).ToString();
            });
        }

        public static string Compute(string expression)
        {
            Term termInstance = Term.GetInstance();
            return termInstance.Calculate(expression);
        }

        private static string ProcessExpression(string expression)
        {
            IfcExpressionProcessor processor = new ReplacePowersDecorator(
                new EvaluateBracketedPowersDecorator(
                    new ComputeDecorator()
                )
            );
            return processor.Process(expression);
        }

        public static string PreprocessNegativeNumbers(string expression)
        {
            if (Regex.IsMatch(expression, ".*(--{2,}|\\+{2,}|/{2,}).*"))
            {
                throw new FormatException("Incorrect operands or the order of the operands is not accepted by this parser.");
            }

            expression = expression.Replace("--", "+");
            return Regex.Replace(expression, "(?<=\\()-", "0-");
        }

        public static string Does(string term)
        {
            string processedTerm = PreprocessNegativeNumbers(term);
            return ProcessExpression(processedTerm);
        }
    } 
}
