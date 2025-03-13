using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CalculatorBackend.ifc
{
    public interface IfcExpressionProcessor
    {
        public string Process(string expression);
    }
}
