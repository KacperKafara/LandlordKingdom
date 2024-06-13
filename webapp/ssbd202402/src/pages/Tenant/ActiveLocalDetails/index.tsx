import DataField from "@/components/DataField";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useGetActiveLocal } from "@/data/local/useGetActiveLocal";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { t } from "i18next";
import { FC } from "react";
import { Button } from "@/components/ui/button";
import { useParams } from "react-router-dom";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import LoadingButton from "@/components/LoadingButton";
import { useGetUserApplication } from "@/data/application/useGetUserApplication";
import { useCreateApplication } from "@/data/application/useCreateApplication";
import { useQueryClient } from "@tanstack/react-query";

const ActiveLocalDetailsPage: FC = () => {
  const { id } = useParams<{ id: string }>();
  const { local } = useGetActiveLocal(id!);
  const { application, isError } = useGetUserApplication(id!);
  const { createApplication } = useCreateApplication();
  const queryClient = useQueryClient();

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

  const handleCreateApplication = async () => {
    await createApplication(id!);
    queryClient.invalidateQueries({ queryKey: ["userApplication"] });
  };

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
                      {t("activeLocalDetails.localInformation")}
                    </p>
                    <DataField
                      label={t("activeLocalDetails.city")}
                      value={local.city}
                    />
                    <DataField
                      label={t("activeLocalDetails.size")}
                      value={local.size}
                    />
                    <DataField
                      label={t("activeLocalDetails.price")}
                      value={
                        (local?.price ?? 0.0).toFixed(2) + " " + t("currency")
                      }
                    />

                    <p className="col-span-3 text-xl font-semibold">
                      {t("activeLocalDetails.ownerInformation")}
                    </p>
                    <DataField
                      label={t("activeLocalDetails.firstName")}
                      value={local.ownerName}
                    />
                  </div>

                  <p className="col-span-2 mt-2 text-xl font-semibold">
                    {t("localDetails.description")}
                  </p>
                  <div>{local.description}</div>

                  <AlertDialog>
                    <AlertDialogTrigger asChild>
                      <Button className="mt-4">
                        {t("activeLocalDetails.apply")}
                      </Button>
                    </AlertDialogTrigger>
                    <AlertDialogContent>
                      <AlertDialogHeader>
                        <AlertDialogTitle>
                          {t("activeLocalDetails.applicationTitle")}
                        </AlertDialogTitle>
                        <AlertDialogDescription>
                          {application
                            ? t(
                                "activeLocalDetails.applicationExistsDescription"
                              ) + application.createdAt
                            : t("activeLocalDetails.applicationDescription")}
                        </AlertDialogDescription>
                      </AlertDialogHeader>
                      <AlertDialogFooter>
                        <AlertDialogCancel>{t("cancel")}</AlertDialogCancel>
                        <AlertDialogAction asChild>
                          <LoadingButton
                            text={t("confirm")}
                            isLoading={!isError && !application}
                            disableButton={application != undefined}
                            onClick={async () =>
                              await handleCreateApplication()
                            }
                          />
                        </AlertDialogAction>
                      </AlertDialogFooter>
                    </AlertDialogContent>
                  </AlertDialog>

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
