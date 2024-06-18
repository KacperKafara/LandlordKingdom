import { useLocalReport } from "@/data/local/useLocalReport";
import { FC } from "react";
import { useParams } from "react-router-dom";
import ReportPieChart from "./ReportPieChart";
import ReportComboChart from "./ReportComboChart";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import DataField from "@/components/DataField";
import { useTranslation } from "react-i18next";
import { toLocaleFixed } from "@/utils/currencyFormat";

const LocalReportPage: FC = () => {
  const { t } = useTranslation();
  const { id } = useParams<{ id: string }>();
  const { report } = useLocalReport(id!);

  const breadcrumbs = useBreadcrumbs([
    { title: t("ownerLocals.title"), path: "/owner" },
    { title: t("ownerLocals.locals"), path: "/owner/locals" },
    { title: report?.name ?? "", path: `/owner/locals/local/${id}` },
    {
      title: t("breadcrumbs.report"),
      path: `/owner/locals/local/${id}/report`,
    },
  ]);

  const marginFeeSum = report?.fixedFees.reduce(
    (acc, fixedFee) => acc + fixedFee.marginFee,
    0
  );

  const rentalFeeSum =
    report?.fixedFees.reduce((acc, fixedFee) => acc + fixedFee.rentalFee, 0) ??
    0;

  const variableFeeSum =
    report?.variableFees.reduce(
      (acc, variableFee) => acc + variableFee.amount,
      0
    ) ?? 0;

  return (
    <div className="flex flex-col gap-4">
      <div className="flex flex-row items-center justify-between">
        {breadcrumbs}
        <RefreshQueryButton queryKeys={["localReport"]} />
      </div>
      <div className="flex flex-row gap-4">
        <Card className="w-2/3">
          <CardHeader className="mb-4">
            <CardTitle>{t("localReport.localSummary")}</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="grid grid-cols-2 gap-10 text-2xl">
              <DataField
                label={t("localReport.totalMarginFees")}
                value={
                  toLocaleFixed(marginFeeSum ? marginFeeSum : 0) +
                  " " +
                  t("currency")
                }
              />
              <DataField
                label={t("localReport.rentCount")}
                value={report?.rentCount ?? 0}
              />
              <DataField
                className="col-span-2"
                label={t("localReport.totalRentalFees")}
                value={
                  toLocaleFixed(variableFeeSum + rentalFeeSum) +
                  " " +
                  t("currency")
                }
              />
              <DataField
                label={t("localReport.longestRentDays")}
                value={report?.longestRentDays ?? 0}
              />
              <DataField
                label={t("localReport.shortestRentDays")}
                value={report?.shortestRentDays ?? 0}
              />
            </div>
          </CardContent>
        </Card>
        <Card className="w-1/3">
          <CardHeader>
            <CardTitle>{t("localReport.summaryPieChart")}</CardTitle>
          </CardHeader>
          <CardContent>
            <ReportPieChart report={report} />
          </CardContent>
        </Card>
      </div>
      <Card className="mb-10">
        <CardHeader>
          <CardTitle>{t("localReport.summaryBarChart")}</CardTitle>
        </CardHeader>
        <CardContent>
          <ReportComboChart report={report} />
        </CardContent>
      </Card>
    </div>
  );
};

export default LocalReportPage;
