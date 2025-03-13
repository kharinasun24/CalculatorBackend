using CalculatorBackend.auxiliary;
using CalculatorBackend.ifc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CalculatorBackend.computer
{
    internal class ComputeDecorator : IfcExpressionProcessor
    { 
        public string Process(string expression)
        {
            Result result = ACalculator.SplitExpression(expression);

            if (!string.IsNullOrEmpty(result.middle))
            {
                string middle = ACalculator.DoChunking(result.middle);
                return ACalculator.Compute(result.left + ACalculator.Compute(middle) + result.right);
            }
            else
            {
                return ACalculator.Compute(result.left);
            }
        }
   }
}