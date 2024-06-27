import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useTenantRent } from "@/data/rent/useTenantRent";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { useParams, useSearchParams } from "react-router-dom";
import RentInformationCard from "./RentInformationCard";
import CreateVariableFeeDialog from "./CreateVariableFeeDialog";
import { RentPayments } from "@/components/RentPayments";
import { RentFixedFees } from "@/components/RentFixedFees";
import { RentVariableFees } from "@/components/RentVariableFees";

const RentDetailsPage: FC = () => {
  const { t } = useTranslation();
  const { id } = useParams<{ id: string }>();
  const [searchParams] = useSearchParams();
  const { rent } = useTenantRent(id!);
  const referer = searchParams.get("referer");
  const breadcrumbs = useBreadcrumbs([
    {
      title: t("roles.tenant"),
      path: "/tenant",
    },
    {
      title:
        referer === "archival-rents"
          ? t("breadcrumbs.archivalRents")
          : t("breadcrumbs.currentRents"),
      path: `/tenant/${referer === "archival-rents" ? "archival-rents" : "current-rents"}`,
    },
    {
      title: rent
        ? `${rent.local.name} (${rent.startDate} - ${rent.endDate})`
        : "",
      path: `/tenant/rents/${id}`,
    },
  ]);
  return (
    <div className="flex flex-col">
      {breadcrumbs}
      <Tabs defaultValue="details" className="mt-2">
        <div className="flex flex-row justify-between">
          <TabsList>
            <TabsTrigger value="details">
              {t("rentDetailsPage.details")}
            </TabsTrigger>
            <TabsTrigger value="payments">
              {t("rentDetailsPage.payments")}
            </TabsTrigger>
            <TabsTrigger value="fixedFees">
              {t("rentDetailsPage.fixedFees")}
            </TabsTrigger>
            <TabsTrigger value="variableFees">
              {t("rentDetailsPage.variableFees")}
            </TabsTrigger>
          </TabsList>
          {rent && <CreateVariableFeeDialog rentId={rent?.id} />}
        </div>
        <TabsContent value="details">
          <RentInformationCard rent={rent} />
        </TabsContent>
        <TabsContent value="payments">
          {rent && (
            <RentPayments
              id={id!}
              startDate={rent.startDate}
              endDate={rent.endDate}
            />
          )}
        </TabsContent>
        <TabsContent value="fixedFees">
          {rent && (
            <RentFixedFees
              id={id!}
              startDate={rent.startDate}
              endDate={rent.endDate}
              isTenant={true}
            />
          )}
        </TabsContent>
        <TabsContent value="variableFees">
          {rent && (
            <RentVariableFees
              id={id!}
              startDate={rent.startDate}
              endDate={rent.endDate}
            />
          )}
        </TabsContent>
      </Tabs>
    </div>
  );
};

export default RentDetailsPage;
