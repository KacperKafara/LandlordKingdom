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

const LocalDetailsPage: FC = () => {
  const { id } = useParams<{ id: string }>();
  const { data, isLoading } = useGetLocalDetailsForAdmin(id!);
  const [localName] = useState<string>(data?.name || "");
  const navigate = useNavigate();
  const { t } = useTranslation();

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
        <div className="flex w-full justify-center py-9">
          <div className="w-10/12 ">
            <Card className="relative">
              <CardHeader className="items-center">
                <CardTitle>{data.name}</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="flex justify-center">
                  <div className="grid w-2/3 grid-cols-2 gap-2">
                    <p className="col-span-2 text-xl font-semibold">
                      Local information
                    </p>
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
                      value={`${data.address.zipCode.substring(0, 2)}${data.address.zipCode.substring(2)}`}
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
                </div>
              </CardContent>
              <RefreshQueryButton
                className="absolute right-1 top-1"
                queryKeys={["localDetailsForAdmin"]}
              />
            </Card>
          </div>
        </div>
      )}
    </div>
  );
};

export default LocalDetailsPage;
