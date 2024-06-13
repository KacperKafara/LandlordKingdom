import { LocalReport } from "@/data/local/useLocalReport";
import moment from "moment";
import React, { FC } from "react";
import { useTranslation } from "react-i18next";
import {
  Bar,
  CartesianGrid,
  ComposedChart,
  Legend,
  Line,
  ReferenceLine,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";

type ReportComboChartProps = {
  report?: LocalReport;
};

const ReportComboChartComponent: FC<ReportComboChartProps> = ({ report }) => {
  const { t } = useTranslation();
  const paymentGroups = report?.payments.reduce(
    (acc, payment) => {
      const yearWeek = `${moment(payment.date).year()}-${moment(payment.date).week()}`;

      if (!acc[yearWeek]) {
        acc[yearWeek] = [];
      }

      acc[yearWeek].push({ payment: payment.amount });

      return acc;
    },
    {} as Record<string, { payment: number }[]>
  );

  const variableFeesGroups = report?.variableFees.reduce(
    (acc, variableFee) => {
      const yearWeek = `${moment(variableFee.date).year()}-${moment(variableFee.date).week()}`;

      if (!acc[yearWeek]) {
        acc[yearWeek] = [];
      }

      acc[yearWeek].push({ variableFee: variableFee.amount });

      return acc;
    },
    {} as Record<string, { variableFee: number }[]>
  );

  const fixedFeesGroups = report?.fixedFees.reduce(
    (acc, fixedFee) => {
      const yearWeek = `${moment(fixedFee.date).year()}-${moment(fixedFee.date).week()}`;

      if (!acc[yearWeek]) {
        acc[yearWeek] = [];
      }

      acc[yearWeek].push({
        fixedFee: fixedFee.marginFee + fixedFee.rentalFee,
        marginFee: fixedFee.marginFee,
        rentalFee: fixedFee.rentalFee,
      });

      return acc;
    },
    {} as Record<
      string,
      { fixedFee: number; marginFee: number; rentalFee: number }[]
    >
  );

  const groups = [
    ...new Set([
      ...Object.keys(paymentGroups ?? {}),
      ...Object.keys(variableFeesGroups ?? {}),
      ...Object.keys(fixedFeesGroups ?? {}),
    ]),
  ]
    .sort()
    .map((key) => {
      const obj = {
        name: key,
        payment: (paymentGroups![key] ?? []).reduce(
          (acc, x) => acc + x.payment,
          0
        ),
        variableFee:
          (variableFeesGroups![key] ?? []).reduce(
            (acc, x) => acc + x.variableFee,
            0
          ) * -1,
        fixedFee:
          (fixedFeesGroups![key] ?? []).reduce(
            (acc, x) => acc + x.fixedFee,
            0
          ) * -1,
        marginFee:
          (fixedFeesGroups![key] ?? []).reduce(
            (acc, x) => acc + x.marginFee,
            0
          ) * -1,
        rentalFee:
          (fixedFeesGroups![key] ?? []).reduce(
            (acc, x) => acc + x.rentalFee,
            0
          ) * -1,
      };

      return {
        ...obj,
        total: obj.payment + obj.variableFee + obj.fixedFee,
      };
    });

  return (
    <div className="h-[500px]">
      <ResponsiveContainer>
        <ComposedChart
          width={500}
          height={300}
          data={groups}
          stackOffset="sign"
        >
          <CartesianGrid />
          <XAxis dataKey="name" />
          <YAxis unit={" " + t("currency")} />
          <Tooltip />
          <Legend />
          <ReferenceLine y={0} stroke="#000" />
          <Bar
            dataKey="payment"
            stackId="a"
            fill="#8884d8"
            barSize={40}
            name={t("localReport.payment")}
            unit={" " + t("currency")}
          />
          <Bar
            dataKey="variableFee"
            stackId="a"
            fill="#82ca9d"
            name={t("localReport.variableFee")}
            unit={" " + t("currency")}
          />
          <Bar
            dataKey="marginFee"
            stackId="a"
            fill="#ffc658"
            name={t("localReport.marginFee")}
            unit={" " + t("currency")}
          />
          <Bar
            dataKey="rentalFee"
            stackId="a"
            fill="#ff7300"
            name={t("localReport.rentalFee")}
            unit={" " + t("currency")}
          />
          <Line
            type="monotone"
            dataKey="total"
            stroke="#ff0000"
            name={t("localReport.summary")}
            unit={" " + t("currency")}
          />
        </ComposedChart>
      </ResponsiveContainer>
    </div>
  );
};

const ReportComboChart = React.memo(ReportComboChartComponent);

export default ReportComboChart;
