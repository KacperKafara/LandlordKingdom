import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { useGetOwnApplications } from "@/data/tenant/useGetOwnApplications";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { RefreshCw } from "lucide-react";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import DataField from "@/components/DataField";
import ConfirmDialog from "@/components/ConfirmDialog";
import { useDeleteApplication } from "@/data/application/useDeleteApplication";
import { useQueryClient } from "@tanstack/react-query";

const OwnApplicationsPage: FC = () => {
  const { data: applications, isLoading } = useGetOwnApplications();
  const { t } = useTranslation();
  const queryClient = useQueryClient();
  const { deleteApplication } = useDeleteApplication();
  const breadcrumbs = useBreadcrumbs([
    { title: "Tenant", path: "/tenant" },
    { title: t("navLinks.applications"), path: "/tenant/applications" },
  ]);
  const navigate = useNavigate();

  const handleRemoveApplication = async (id: string) => {
    await deleteApplication(id);
    queryClient.invalidateQueries({ queryKey: ["tenantOwnApplications"] });
  };

  return (
    <div className="relative pt-2">
      {breadcrumbs}
      <div className="relative flex h-full justify-center pt-2">
        {isLoading && <RefreshCw className="animate-spin" />}
        {!isLoading && (!applications || applications.length === 0) && (
          <div>{t("tenantApplications.applicationsNotFund")}</div>
        )}
        {!isLoading && applications && applications.length > 0 && (
          <div className="my-3 grid w-11/12 grid-cols-1 gap-2 md:grid-cols-2">
            {applications.map((application) => (
              <Card className="relative" key={application.id}>
                <Button
                  className="absolute right-1 top-1"
                  variant="ghost"
                  onClick={() =>
                    navigate(`/tenant/locals/${application.localId}`)
                  }
                >
                  {t("tenantApplications.linkToLocal")}
                </Button>
                <CardHeader>
                  <CardTitle className="text-2xl">
                    {application.localName}
                  </CardTitle>
                  <CardDescription>
                    {application.country} {application.city}{" "}
                    {application.street}
                  </CardDescription>
                </CardHeader>
                <CardContent className="grid grid-cols-2">
                  <DataField
                    label={t("tenantApplications.createdAt")}
                    value={application.createdAt}
                  />
                </CardContent>
                <CardFooter className="flex w-full justify-end gap-3">
                  <ConfirmDialog
                    dialogTitle={t("tenantApplications.deleteApplication")}
                    dialogDescription={t(
                      "tenantApplications.deleteApplicationDescription"
                    )}
                    buttonText={t("tenantApplications.deleteApplication")}
                    confirmAction={async () =>
                      await handleRemoveApplication(application.localId)
                    }
                    variant="destructive"
                  />
                </CardFooter>
              </Card>
            ))}
          </div>
        )}
      </div>
      <div className="absolute -right-10 top-0">
        <RefreshQueryButton queryKeys={["tenantOwnApplications"]} />
      </div>
    </div>
  );
};

export default OwnApplicationsPage;
