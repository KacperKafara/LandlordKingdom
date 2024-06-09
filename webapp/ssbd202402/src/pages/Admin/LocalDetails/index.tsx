import { useGetLocalDetailsForAdmin } from "@/data/local/useGetLocalDetailsForAdmin";
import { FC } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import DataField from "@/components/DataField";
import { Button } from "@/components/ui/button";
import { useTranslation } from "react-i18next";
import { LoadingData } from "@/components/LoadingData";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTrigger,
  DialogClose,
} from "@/components/ui/dialog";
import { useArchiveLocal } from "@/data/local/useArchiveLocal";

const LocalDetailsPage: FC = () => {
  const { id } = useParams<{ id: string }>();
  const { data, isLoading } = useGetLocalDetailsForAdmin(id!);
  const { archiveLocal } = useArchiveLocal();
  const navigate = useNavigate();
  const { t } = useTranslation();

  const breadcrumbs = useBreadcrumbs([
    { title: t("roles.administrator"), path: "/admin" },
    { title: t("allLocals.title"), path: "/admin/locals" },
    { title: data?.name ?? "", path: `/admin/locals/${id}` },
  ]);

  if (isLoading) {
    return <LoadingData />;
  }

  const handleArchiveLocal = async () => {
    await archiveLocal(id!);
  };

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
                  <Card className="relative mb-2">
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

                        <div className="col-span-2 flex gap-3">
                          <Button
                            variant="secondary"
                            className="mt-3 w-full text-lg font-normal"
                            onClick={() => {
                              navigate(`/admin/users/${data.owner.userId}`);
                            }}
                          >
                            {t("localDetails.showOwnerDetails")}
                          </Button>
                          {data.state === "WITHOUT_OWNER" && (
                            <Dialog>
                              <DialogTrigger asChild>
                                <Button
                                  variant="destructive"
                                  className="mt-3 w-full text-lg font-normal"
                                >
                                  {t("localDetails.archiveLocal")}
                                </Button>
                              </DialogTrigger>
                              <DialogContent>
                                <DialogHeader>
                                  {t("localDetails.archiveLocal")}
                                </DialogHeader>
                                <p>
                                  {t("localDetails.archiveLocalDescription")}
                                </p>
                                <div className="flex gap-2">
                                  <DialogClose asChild>
                                    <Button
                                      variant="default"
                                      className="mt-3 w-full text-lg font-normal"
                                    >
                                      {t("localDetails.close")}
                                    </Button>
                                  </DialogClose>
                                  <DialogClose asChild>
                                    <Button
                                      variant="destructive"
                                      className="mt-3 w-full text-lg font-normal"
                                      onClick={handleArchiveLocal}
                                    >
                                      {t("localDetails.archiveLocal")}
                                    </Button>
                                  </DialogClose>
                                </div>
                              </DialogContent>
                            </Dialog>
                          )}
                          {data.state === "UNAPPROVED" && (
                            <Button
                              variant="default"
                              className="mt-3 w-full text-lg font-normal"
                            >
                              {t("localDetails.approveLocal")}
                            </Button>
                          )}
                        </div>
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
