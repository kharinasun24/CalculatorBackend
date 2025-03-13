using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using CalculatorBackend.ifc;
using CalculatorBackend;

internal class ReplacePowersDecorator : IfcExpressionProcessor
{
    private readonly IfcExpressionProcessor _next;

    public ReplacePowersDecorator(IfcExpressionProcessor next)
    {
        _next = next;
    }

    public string Process(string expression)
    {
        expression = ACalculator.ReplacePowers(expression);
        return _next.Process(expression);
    }
}
