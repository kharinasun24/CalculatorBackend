using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CalculatorBackend.auxiliary
{
    public class Result { 

    public string left;
    public string middle;
    public string right;

    public Result(string left, string middle, string right)
    {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
    }
}
