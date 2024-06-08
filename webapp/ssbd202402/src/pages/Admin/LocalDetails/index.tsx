import { useGetLocalDetailsForAdmin } from "@/data/local/useGetLocalDetailsForAdmin";
import { FC, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import DataField from "@/components/DataField";
import { Button } from "@/components/ui/button";
import { useTranslation } from "react-i18next";
import { LoadingData } from "@/components/LoadingData";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";

const LocalDetailsPage: FC = () => {
  const { id } = useParams<{ id: string }>();
  const { data, isLoading } = useGetLocalDetailsForAdmin(id!);
  const [localName] = useState<string>(data?.name || "");
  const navigate = useNavigate();
  const { t } = useTranslation();

  //TODO: Poprawic ten syf bo przeciez ledwo dziala, jak sie pierwszy raz wejdzie na lokal to nie ma nazwy lokalu
  const breadcrumbs = useBreadcrumbs([
    { title: t("roles.administrator"), path: "/admin" },
    { title: t("allLocals.title"), path: "/admin/locals" },
    { title: localName, path: `/admin/local/${id}` },
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
              <CardTitle className="text-center">{data.name}</CardTitle>
            </CardHeader>
          </Card>
          <div className="flex w-full justify-center">
            <div className="w-9/12 ">
              <Tabs defaultValue="basic">
                <TabsList className="mt-1">
                  <TabsTrigger value="basic">
                    {t("localDetails.basicInformation")}
                  </TabsTrigger>
                  <TabsTrigger value="updateData">
                    {t("localDetails.updateData")}
                  </TabsTrigger>
                  <TabsTrigger value="changeAddress">
                    {t("localDetails.changeAddress")}
                  </TabsTrigger>
                </TabsList>
                <TabsContent value="basic">
                  <Card className="relative">
                    <CardHeader>
                      <CardTitle className="text-center">
                        {t("localDetails.localInformation")}
                      </CardTitle>
                    </CardHeader>
                    <CardContent className="flex justify-center">
                      <div className="grid w-2/3 grid-cols-2 gap-2">
                        <DataField
                          label={t("localDetails.size")}
                          value={data.size.toString()}
                        />
                        <DataField
                          label={t("localDetails.rentalFee")}
                          value={data.rentalFee.toString()}
                        />
                        <DataField
                          label={t("localDetails.marginFee")}
                          value={data.marginFee.toString()}
                        />

                        <p className="col-span-2 text-xl font-semibold">
                          {t("localDetails.ownerInformation")}{" "}
                        </p>

                        <DataField
                          label={t("localDetails.firstName")}
                          value={data.owner.firstName}
                        />
                        <DataField
                          label={t("localDetails.lastName")}
                          value={data.owner.lastName}
                        />
                        <DataField
                          label={t("localDetails.login")}
                          value={data.owner.login}
                        />
                        <DataField
                          label={t("localDetails.email")}
                          value={data.owner.email}
                        />

                        <p className="col-span-2 text-xl font-semibold">
                          {t("localDetails.addressInformation")}{" "}
                        </p>

                        <DataField
                          label={t("localDetails.country")}
                          value={data.address.country}
                        />
                        <DataField
                          label={t("localDetails.city")}
                          value={data.address.city}
                        />
                        <DataField
                          label={t("localDetails.street")}
                          value={data.address.street}
                        />
                        <DataField
                          label={t("localDetails.number")}
                          value={data.address.number}
                        />
                        <DataField
                          label={t("localDetails.zipCode")}
                          value={`${data.address.zipCode}`}
                        />

                        <div className="col-span-2 flex flex-col">
                          <div className="text-sm font-semibold">
                            {t("localDetails.description")}
                          </div>
                          <div>{data.description}</div>
                        </div>

                        <Button
                          variant="secondary"
                          className="col-span-2 mt-3 text-lg font-normal"
                          onClick={() => {
                            navigate(`/admin/users/${data.owner.userId}`);
                          }}
                        >
                          {t("localDetails.showOwnerDetails")}
                        </Button>
                      </div>
                    </CardContent>
                    <RefreshQueryButton
                      className="absolute right-1 top-1"
                      queryKeys={["localDetailsForAdmin"]}
                    />
                  </Card>
                </TabsContent>
                <TabsContent value="updateData">
                  <Card>
                    <CardHeader>
                      <CardTitle className="text-center">
                        {t("localDetails.updateData")}
                      </CardTitle>
                    </CardHeader>
                  </Card>
                </TabsContent>
                <TabsContent value="changeAddress">
                  <Card>
                    <CardHeader>
                      <CardTitle className="text-center">
                        {t("localDetails.changeAddress")}
                      </CardTitle>
                    </CardHeader>
                  </Card>
                </TabsContent>
              </Tabs>
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default LocalDetailsPage;
