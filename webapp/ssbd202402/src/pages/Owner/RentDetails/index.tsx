import { FC } from "react";
import RentInformation from "./RentInformation";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Card, CardHeader, CardTitle } from "@/components/ui/card";
import { useParams } from "react-router-dom";
import { useOwnerRent } from "@/data/rent/useOwnerRent";
import { RentPayments } from "@/components/RentPayments";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { useTranslation } from "react-i18next";
import { RentFixedFees } from "@/components/RentFixedFees";
import { RentVariableFees } from "@/components/RentVariableFees";
import { LoadingData } from "@/components/LoadingData";
import CreatePaymentDialog from "./CreatePaymentDialog";

const OwnerRentDetailsPage: FC = () => {
  const { id } = useParams<{ id: string }>();
  const { t } = useTranslation();
  const { data, isLoading } = useOwnerRent(id!);
  const path =
    new Date(data?.endDate ?? Date()) < new Date()
      ? "/owner/archival-rents"
      : "/owner/current-rents";
  const breadcrumbs = useBreadcrumbs([
    { title: t("ownerRentDetails.ownerMainPage"), path: "/owner" },
    {
      title:
        path === "/owner/current-rents"
          ? t("ownerRentDetails.rents")
          : t("ownerRentDetails.archivalRents"),
      path: path ?? "/owner/current-rents",
    },
    {
      title:
        `${
          path === "/owner/current-rents"
            ? t("ownerRentDetails.rents")
            : t("ownerRentDetails.archivalRents")
        } ${data?.local.name}` ?? "",
      path: `${path}/rent/${id}`,
    },
  ]);
  if (isLoading) {
    return <LoadingData />;
  }
  const isFutureEndDate = data?.endDate && new Date(data.endDate) > new Date();
  return (
    <div className="flex flex-col">
      {breadcrumbs}
      <Tabs className="mt-2" defaultValue="rentInformation">
        <div className="flex flex-row justify-between">
          <TabsList>
            <TabsTrigger value="rentInformation">
              {t("ownerRentDetails.rentInfo")}
            </TabsTrigger>
            <TabsTrigger value="payments">
              {t("ownerRentDetails.payments")}
            </TabsTrigger>
            <TabsTrigger value="fixedFees">
              {t("ownerRentDetails.fixedFees")}
            </TabsTrigger>
            <TabsTrigger value="variableFees">
              {t("ownerRentDetails.variableFees")}
            </TabsTrigger>
          </TabsList>
          {isFutureEndDate && <CreatePaymentDialog rentId={data?.id} />}
        </div>
        <TabsContent value="rentInformation">
          <RentInformation rent={data} />
        </TabsContent>
        <TabsContent value="payments">
          {data ? (
            <RentPayments
              id={id!}
              startDate={data!.startDate}
              endDate={data!.endDate}
            />
          ) : (
            <Card>
              <CardHeader className="text-center">
                <CardTitle>{t("ownerRentDetails.noPayments")}</CardTitle>
              </CardHeader>
            </Card>
          )}
        </TabsContent>
        <TabsContent value="fixedFees">
          <RentFixedFees
            id={id!}
            startDate={data!.startDate}
            endDate={data!.endDate}
          />
        </TabsContent>
        <TabsContent value="variableFees">
          <RentVariableFees
            id={id!}
            startDate={data!.startDate}
            endDate={data!.endDate}
          />
        </TabsContent>
      </Tabs>
    </div>
  );
};

export default OwnerRentDetailsPage;
