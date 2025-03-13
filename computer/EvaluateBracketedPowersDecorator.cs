using CalculatorBackend.ifc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CalculatorBackend.computer
{
    internal class EvaluateBracketedPowersDecorator : IfcExpressionProcessor
    {
        private readonly IfcExpressionProcessor _next;

        public EvaluateBracketedPowersDecorator(IfcExpressionProcessor next)
        {
            _next = next;
        }

        public string Process(string expression)
        {
            expression = ACalculator.EvaluateBracketedPowers(expression);
            return _next.Process(expression);
        }
    }
}
