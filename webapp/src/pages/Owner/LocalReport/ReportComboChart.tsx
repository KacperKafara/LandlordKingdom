import { Button } from "@/components/ui/button";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { LocalReport } from "@/data/local/useLocalReport";
import { LocalsReport } from "@/data/local/useLocalsReport";
import moment from "moment";
import React, { FC, useState } from "react";
import { useTranslation } from "react-i18next";
import { FaChevronLeft, FaChevronRight } from "react-icons/fa";
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
  report?: LocalReport | LocalsReport;
};

const ReportComboChartComponent: FC<ReportComboChartProps> = ({ report }) => {
  const { t } = useTranslation();
  const [amount, setAmount] = useState(20);
  const [page, setPage] = useState(0);

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
    .sort((a, b) => {
      const [aYear, aWeek] = a.split("-").map((x) => parseInt(x));
      const [bYear, bWeek] = b.split("-").map((x) => parseInt(x));
      return aYear - bYear || aWeek - bWeek;
    })
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

  const totalPages = Math.ceil(groups.length / amount);

  const pagedGroups = groups.slice(page * amount, page * amount + amount);

  return (
    <div className="flex flex-col">
      <div className="flex flex-row items-center justify-between pb-4">
        <div className="flex flex-row items-center gap-2">
          <span>{t("localReport.numberOfWeeks")}</span>
          <Select
            value={amount.toString()}
            onValueChange={(value) => setAmount(+value)}
          >
            <SelectTrigger className="w-20">
              <SelectValue />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="20">20</SelectItem>
              <SelectItem value="30">30</SelectItem>
              <SelectItem value="50">50</SelectItem>
              <SelectItem value="70">70</SelectItem>
            </SelectContent>
          </Select>
        </div>

        <div className="flex flex-row items-center gap-4">
          <Button
            variant="secondary"
            disabled={page === 0}
            onClick={() => setPage((curr) => curr - 1)}
          >
            <FaChevronLeft />
          </Button>
          <span>
            {pagedGroups.at(0)?.name} - {pagedGroups.at(-1)?.name}
          </span>
          <Button
            variant="secondary"
            onClick={() => setPage((curr) => curr + 1)}
            disabled={page === totalPages - 1}
          >
            <FaChevronRight />
          </Button>
        </div>
      </div>
      <div className="h-[500px]">
        <ResponsiveContainer>
          <ComposedChart
            width={500}
            height={300}
            data={pagedGroups}
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
    </div>
  );
};

const ReportComboChart = React.memo(ReportComboChartComponent);

export default ReportComboChart;
