import RefreshQueryButton from "@/components/RefreshQueryButton";
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
      title: t("notApprovedActionsPage.title"),
      path: `/tenant/${referer !== "current-rents" ? "archival-rents" : "current-rents"}`,
    },
    { title: "Rent", path: `/tenant/rent/${id}` },
  ]);
  return (
    <div className="flex justify-center">
      <div className="flex w-10/12 flex-col pt-10">
        <div className="flex items-center justify-between">
          {breadcrumbs}
          <RefreshQueryButton queryKeys={["tenantRent"]} />
        </div>
        {rent && <CreateVariableFeeDialog rentId={rent?.id} />}
        <Tabs defaultValue="details">
          <TabsList>
            <TabsTrigger value="details">Details</TabsTrigger>
            <TabsTrigger value="payments">Payments</TabsTrigger>
            <TabsTrigger value="fixedFees">Fixed Fees</TabsTrigger>
            <TabsTrigger value="variableFees">Variable Fees</TabsTrigger>
          </TabsList>
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
    </div>
  );
};

export default RentDetailsPage;
