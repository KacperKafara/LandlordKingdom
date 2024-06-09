import DataField from "@/components/DataField";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useGetActiveLocal } from "@/data/local/useGetActiveLocal";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { t } from "i18next";
import { FC } from "react";
import { useParams } from "react-router-dom";

const ActiveLocalDetailsPage: FC = () => {
  const { id } = useParams<{ id: string }>();
  const { local } = useGetActiveLocal(id!);
  const breadcrumbs = useBreadcrumbs([
    {
      title: t("breadcrumbs.tenant"),
      path: "/tenant",
    },
    {
      title: t("breadcrumbs.locals"),
      path: "/tenant/locals",
    },
    {
      title: local?.name ?? "",
      path: `/tenant/locals/${id}`,
    },
  ]);

  return (
    <>
      <div className="my-2">{breadcrumbs}</div>
      {local && (
        <div className="flex flex-col gap-4">
          <Card>
            <CardHeader>
              <CardTitle className="text-center">{local.name}</CardTitle>
            </CardHeader>
          </Card>
          <div className="flex justify-center">
            <div className="w-9/12">
              <Card className="relative pt-6">
                <CardContent>
                  <div className="grid w-2/3 grid-cols-3 gap-2">
                    <p className="col-span-3 text-xl font-semibold">
                      {t("localDetails.localInformation")}
                    </p>
                    <DataField
                      label={t("localDetails.city")}
                      value={local.city}
                    />
                    <DataField
                      label={t("localDetails.size")}
                      value={local.size}
                    />
                    <DataField
                      label={t("localDetails.price")}
                      value={local.price}
                    />

                    <p className="col-span-3 text-xl font-semibold">
                      {t("localDetails.ownerInformation")}
                    </p>
                    <DataField
                      label={t("localDetails.firstName")}
                      value={local.ownerName}
                    />
                  </div>

                  <p className="col-span-2 mt-2 text-xl font-semibold">
                    {t("localDetails.description")}
                  </p>
                  <div>{local.description}</div>

                  <RefreshQueryButton
                    className="absolute right-1 top-1"
                    queryKeys={["activeLocalDetails"]}
                  />
                </CardContent>
              </Card>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default ActiveLocalDetailsPage;
