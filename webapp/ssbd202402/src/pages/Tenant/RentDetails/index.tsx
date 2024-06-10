import RefreshQueryButton from "@/components/RefreshQueryButton";
import { Card, CardContent } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useTenantRent } from "@/data/rent/useTenantRent";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";
import RentInformationCard from "./RentInformationCard";
import CreateVariableFeeDialog from "./CreateVariableFeeDialog";

const RentDetailsPage: FC = () => {
  const { t } = useTranslation();
  const { id } = useParams<{ id: string }>();
  const { rent } = useTenantRent(id!);
  const breadcrumbs = useBreadcrumbs([
    {
      title: t("roles.tenant"),
      path: "/tenant",
    },
    {
      title: t("notApprovedActionsPage.title"),
      path: "/tenant/rent",
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
        <RentInformationCard rent={rent} />
        <Tabs defaultValue="payments">
          <TabsList>
            <TabsTrigger value="payments">Payments</TabsTrigger>
            <TabsTrigger value="fixedFees">Fixed Fees</TabsTrigger>
            <TabsTrigger value="variableFees">Variable Fees</TabsTrigger>
          </TabsList>
          <Card>
            <CardContent>
              <TabsContent value="payments">Payments</TabsContent>
              <TabsContent value="fixedFees">Fixed Fees</TabsContent>
              <TabsContent value="variableFees">Variable Fees</TabsContent>
            </CardContent>
          </Card>
        </Tabs>
      </div>
    </div>
  );
};

export default RentDetailsPage;
