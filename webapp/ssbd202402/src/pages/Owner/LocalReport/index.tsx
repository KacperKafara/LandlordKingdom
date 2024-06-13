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

const LocalReportPage: FC = () => {
  const { t } = useTranslation();
  const { id } = useParams<{ id: string }>();
  const { report } = useLocalReport(id!);

  const breadcrumbs = useBreadcrumbs([
    { title: "Owner", path: "/owner" },
    { title: "Local", path: "/owner/local" },
    { title: "Report", path: "/owner/local/report" },
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

  console.log(marginFeeSum);

  return (
    <div className="flex justify-center">
      <div className="flex w-10/12 flex-col gap-4">
        <div className="mt-10 flex flex-row items-center justify-between">
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
                  value={marginFeeSum?.toFixed(2) + " " + t("currency")}
                />
                <DataField
                  label={t("localReport.rentCount")}
                  value={report?.rentCount ?? 0}
                />
                <DataField
                  className="col-span-2"
                  label={t("localReport.totalRentalFees")}
                  value={
                    (variableFeeSum + rentalFeeSum).toFixed(2) +
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
            <CardTitle>Report Combo Chart</CardTitle>
          </CardHeader>
          <CardContent>
            <ReportComboChart report={report} />
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default LocalReportPage;
