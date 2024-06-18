import { useGetOwnLocalDetails } from "@/data/local/useGetOwnLocalDetails";
import { FC } from "react";
import { NavLink, useParams } from "react-router-dom";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import DataField from "@/components/DataField";
import { useTranslation } from "react-i18next";
import { LoadingData } from "@/components/LoadingData";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import UpdateOwnLocalFixedFee from "./UpdateOwnLocalFixedFee";
import UpdateLocalDetailsForm from "@/pages/Owner/OwnLocalDetails/UpdateOwnLocal.tsx";
import LeaveLocalCard from "./LeaveLocalCard";
import { LocalState } from "@/@types/localState";
import LocalApplications from "@/pages/Owner/OwnLocalDetails/ShowApplications.tsx";
import { Button } from "@/components/ui/button";
import { toLocaleFixed } from "@/utils/currencyFormat";

const OwnLocalDetailsPage: FC = () => {
  const { id } = useParams<{ id: string }>();
  const { data, isLoading } = useGetOwnLocalDetails(id!);
  const { t } = useTranslation();
  const breadcrumbs = useBreadcrumbs([
    { title: t("ownerLocals.title"), path: "/owner" },
    { title: t("ownerLocals.locals"), path: "/owner/locals" },
    { title: data?.data.name ?? "", path: `/owner/locals/local/${id}` },
  ]);

  if (isLoading) {
    return <LoadingData />;
  }

  return (
    <div className="flex flex-col pt-2">
      {breadcrumbs}
      {data && (
        <>
          <Card className="mt-2">
            <CardHeader>
              <CardTitle className="text-center">{data.data.name}</CardTitle>
            </CardHeader>
          </Card>
          <div className="flex w-full justify-center">
            <div className="w-9/12 ">
              <Tabs defaultValue="basic">
                <div className="mt-2 flex flex-row items-center justify-between">
                  <TabsList className="mt-1">
                    <TabsTrigger value="basic">
                      {t("ownLocalDetails.basicInformation")}
                    </TabsTrigger>
                    <TabsTrigger value="updateData">
                      {t("ownLocalDetails.updateData")}
                    </TabsTrigger>
                    <TabsTrigger value="changeFixedFee">
                      {t("ownLocalDetails.changeFixedFee")}
                    </TabsTrigger>
                    <TabsTrigger value="leaveLocal">
                      {t("ownLocalDetails.leaveLocal")}
                    </TabsTrigger>
                    <TabsTrigger value="checkApplications">
                      {t("ownLocalDetails.showApplications")}
                    </TabsTrigger>
                  </TabsList>
                  <Button asChild>
                    <NavLink to="report">Report</NavLink>
                  </Button>
                </div>
                <TabsContent value="basic">
                  <Card className="relative mb-2">
                    <CardHeader>
                      <CardTitle className="text-center">
                        {t("ownLocalDetails.localInformation")}
                      </CardTitle>
                    </CardHeader>
                    <CardContent className="flex justify-center">
                      <div className="grid w-2/3 grid-cols-2 gap-2">
                        <DataField
                          label={t("ownLocalDetails.size")}
                          value={data.data.size.toString()}
                        />
                        <DataField
                          label={t("ownLocalDetails.rentalFee")}
                          value={
                            toLocaleFixed(data.data?.rentalFee ?? 0.0) +
                            " " +
                            t("currency")
                          }
                        />
                        <DataField
                          label={t("ownLocalDetails.marginFee")}
                          value={
                            toLocaleFixed(data.data?.marginFee ?? 0.0) +
                            " " +
                            t("currency")
                          }
                        />

                        <DataField
                          label={t("ownLocalDetails.nextRentalFee")}
                          value={
                            data.data?.nextRentalFee != null
                              ? toLocaleFixed(data.data.nextRentalFee) +
                                " " +
                                t("currency")
                              : "-"
                          }
                        />
                        <DataField
                          label={t("ownLocalDetails.nextMarginFee")}
                          value={
                            data.data?.nextMarginFee != null
                              ? toLocaleFixed(data.data.nextMarginFee) +
                                " " +
                                t("currency")
                              : "-"
                          }
                        />

                        <p className="col-span-2 text-xl font-semibold">
                          {t("ownLocalDetails.addressInformation")}{" "}
                        </p>

                        <DataField
                          label={t("ownLocalDetails.country")}
                          value={data.data.address.country}
                        />
                        <DataField
                          label={t("ownLocalDetails.city")}
                          value={data.data.address.city}
                        />
                        <DataField
                          label={t("ownLocalDetails.street")}
                          value={data.data.address.street}
                        />
                        <DataField
                          label={t("ownLocalDetails.number")}
                          value={data.data.address.number}
                        />
                        <DataField
                          label={t("ownLocalDetails.zipCode")}
                          value={`${data.data.address.zipCode}`}
                        />

                        <div className="col-span-2 flex flex-col">
                          <div className="text-sm font-semibold">
                            {t("ownLocalDetails.description")}
                          </div>
                          <div>{data.data.description}</div>
                        </div>
                      </div>
                    </CardContent>
                    <RefreshQueryButton
                      className="absolute right-1 top-1"
                      queryKeys={["ownLocalDetails"]}
                    />
                  </Card>
                </TabsContent>
                <TabsContent value="updateData">
                  <UpdateLocalDetailsForm />
                </TabsContent>
                <TabsContent value="changeFixedFee">
                  <Card>
                    <CardHeader>
                      <CardTitle className="text-center">
                        {t("ownLocalDetails.changeFixedFee")}
                      </CardTitle>
                    </CardHeader>
                    <CardContent className="pb-2">
                      <div className="flex justify-center">
                        <UpdateOwnLocalFixedFee
                          id={id || ""}
                          initialRentalFee={
                            data.data.nextRentalFee ?? data.data.rentalFee
                          }
                          initialMarginFee={
                            data.data.nextMarginFee ?? data.data.marginFee
                          }
                          etag={data.headers.etag! ?? ""}
                        />
                      </div>
                    </CardContent>
                    <CardDescription className="flex justify-center px-6 pb-5 ">
                      {t("ownLocalDetails.changeFixedFeeDescription")}
                    </CardDescription>
                  </Card>
                </TabsContent>
                <TabsContent value="leaveLocal">
                  <LeaveLocalCard
                    state={data.data.state as LocalState}
                    id={id!}
                  />
                </TabsContent>
                <TabsContent value="checkApplications">
                  <LocalApplications />
                </TabsContent>
              </Tabs>
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default OwnLocalDetailsPage;
