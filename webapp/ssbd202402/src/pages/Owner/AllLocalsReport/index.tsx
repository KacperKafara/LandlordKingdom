import { FC } from "react";
import ReportPieChart from "@/pages/Owner/LocalReport/ReportPieChart";
import ReportComboChart from "@/pages/Owner/LocalReport/ReportComboChart";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import DataField from "@/components/DataField";
import { useTranslation } from "react-i18next";
import { useLocalsReport } from "@/data/local/useLocalsReport";

const AllLocalsReportPage: FC = () => {
  const { t } = useTranslation();
  const { report } = useLocalsReport();

  const breadcrumbs = useBreadcrumbs([
    { title: t("ownerLocals.title"), path: "/owner" },
    { title: t("ownerLocals.locals"), path: "/owner/locals" },
    {
      title: t("breadcrumbs.report"),
      path: `/owner/locals/report`,
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
                  className="col-span-2"
                  label={t("localReport.totalRentalFees")}
                  value={
                    (variableFeeSum + rentalFeeSum).toFixed(2) +
                    " " +
                    t("currency")
                  }
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
    </div>
  );
};

export default AllLocalsReportPage;
