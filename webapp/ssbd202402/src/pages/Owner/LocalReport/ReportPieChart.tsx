import { LocalReport } from "@/data/local/useLocalReport";
import React, { FC } from "react";
import { useTranslation } from "react-i18next";
import {
  Cell,
  Legend,
  Pie,
  PieChart,
  ResponsiveContainer,
  Tooltip,
} from "recharts";

type ReportPieChartProps = {
  report?: LocalReport;
};

const ReportPieChartComponent: FC<ReportPieChartProps> = ({ report }) => {
  const { t } = useTranslation();
  const totalPayments = report?.payments
    .map((x) => x.amount)
    .reduce((a, b) => a + b, 0);

  const totalVariableFees = report?.variableFees
    .map((x) => x.amount)
    .reduce((a, b) => a + b, 0);

  const totalFixedFees = report?.fixedFees
    .map((x) => x.marginFee + x.rentalFee)
    .reduce((a, b) => a + b, 0);

  const pieChartData = [
    {
      name: t("localReport.totalPayments"),
      value: totalPayments,
      color: "#8884d8",
    },
    {
      name: t("localReport.totalVariableFees"),
      value: totalVariableFees,
      color: "#82ca9d",
    },
    {
      name: t("localReport.totalFixedFees"),
      value: totalFixedFees,
      color: "#ffc658",
    },
  ];

  return (
    <div className="h-[400px]">
      <ResponsiveContainer height="100%">
        <PieChart width={500} height={500}>
          <Pie
            dataKey="value"
            data={pieChartData}
            cx="50%"
            cy="50%"
            outerRadius={100}
            fill="#8884d8"
          >
            {pieChartData.map((entry, index) => (
              <Cell key={`cell-${index}`} fill={entry.color} />
            ))}
          </Pie>
          <Tooltip
            formatter={(value, name) => [`${name}: ${value} ${t("currency")}`]}
          />
          <Legend />
        </PieChart>
      </ResponsiveContainer>
    </div>
  );
};

const ReportPieChart = React.memo(ReportPieChartComponent);

export default ReportPieChart;
