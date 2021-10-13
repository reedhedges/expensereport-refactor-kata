using expensereport_csharp;
using System.Text;

namespace Tests
{
    internal class ExpenseReportFake : ExpenseReport
    {
        public StringBuilder Output { get; } = new StringBuilder();

        protected override void WriteOutput(string message) =>
            Output.AppendLine(message);

        protected override string GetCurrentDate() => "01.01.0001 00:00:00";
    }
}
