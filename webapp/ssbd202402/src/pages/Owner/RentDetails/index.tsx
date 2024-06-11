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

const OwnerRentDetailsPage: FC = () => {
  const { id } = useParams<{ id: string }>();
  const { t } = useTranslation();
  const { data, isLoading } = useOwnerRent(id!);
  const breadcrumbs = useBreadcrumbs([
    { title: t("ownerRentDetails.ownerMainPage"), path: "/owner" },
    { title: t("ownerRentDetails.rents"), path: "/owner/rents" },
    {
      title: `${t("ownerRentDetails.rents")} ${data?.local.name}` ?? "",
      path: `/owner/rents/rent/${id}`,
    },
  ]);

  if (isLoading) {
    return <LoadingData />;
  }
  return (
    <>
      <div className="flex flex-col py-2">
        {breadcrumbs}
        <Card className="mt-2">
          <CardHeader className="text-center">
            <CardTitle>{t("ownerRentDetails.rentDetails")}</CardTitle>
          </CardHeader>
        </Card>
        <div className="flex w-full justify-center">
          <div className="w-9/12">
            <Tabs className="mt-1" defaultValue="rentInformation">
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
        </div>
      </div>
    </>
  );
};

export default OwnerRentDetailsPage;
