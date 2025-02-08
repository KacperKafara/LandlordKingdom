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
import { NavLink } from "react-router-dom";
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
    { title: t("breadcrumbs.tenant"), path: "/tenant" },
    { title: t("navLinks.applications"), path: "/tenant/applications" },
  ]);

  const handleRemoveApplication = async (id: string) => {
    await deleteApplication(id);
    queryClient.invalidateQueries({ queryKey: ["tenantOwnApplications"] });
  };

  return (
    <div>
      <div className="flex flex-row items-center justify-between">
        {breadcrumbs}
        <RefreshQueryButton queryKeys={["tenantOwnApplications"]} />
      </div>
      {isLoading && <RefreshCw className="animate-spin" />}
      {!isLoading && (!applications || applications.length === 0) && (
        <div className="flex justify-center text-2xl">
          {t("tenantApplications.applicationsNotFund")}
        </div>
      )}
      {!isLoading && applications && applications.length > 0 && (
        <div className="grid grid-cols-3 gap-4">
          {applications.map((application) => (
            <Card className="relative" key={application.id}>
              <CardHeader>
                <CardTitle className="text-2xl">
                  {application.localName}
                </CardTitle>
                <CardDescription>
                  {application.country} {application.city} {application.street}
                </CardDescription>
              </CardHeader>
              <CardContent>
                <DataField
                  label={t("tenantApplications.createdAt")}
                  value={application.createdAt}
                />
              </CardContent>
              <CardFooter className="flex w-full justify-end gap-3">
                <Button variant="secondary" asChild>
                  <NavLink to={`/tenant/locals/${application.localId}`}>
                    {t("tenantApplications.linkToLocal")}
                  </NavLink>
                </Button>
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
  );
};

export default OwnApplicationsPage;
