import DataField from "@/components/DataField";
import RefreshQueryButton from "@/components/RefreshQueryButton";
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

const OwnApplicationsPage: FC = () => {
  const { data, isLoading } = useGetOwnApplications();
  const { t } = useTranslation();
  const breadcrumbs = useBreadcrumbs([
    { title: "Tenant", path: "/tenant" },
    { title: t("navLinks.applications"), path: "/tenant/applications" },
  ]);
  if (isLoading) {
    return <div>Loading...</div>;
  }
  if (!data) {
    return <div>No data</div>;
  }

  return (
    <div className="relative pt-2">
      {breadcrumbs}
      <div className=" flex w-full justify-center">
        <ul className="flex w-4/5 flex-wrap gap-2 py-4">
          {data?.map((application) => (
            <li key={application.id} className="min-w-80 flex-1">
              <Card className="">
                <CardHeader>
                  <CardTitle className="text-2xl">
                    {application.localName}
                  </CardTitle>
                  <CardDescription>{application.createdAt}</CardDescription>
                  <p></p>
                </CardHeader>
                <CardContent className="grid grid-cols-2">
                  <DataField
                    label={t("tenantApplications.createdAt")}
                    value={application.createdAt}
                  />
                </CardContent>
                <CardFooter className="w-full justify-center gap-3">
                  <Button className="flex-auto" asChild>
                    <NavLink to={`/locals/local/${application.localId}`}>
                      {t("tenantApplications.linkToLocal")}
                    </NavLink>
                  </Button>
                  <Button className="flex-auto">Action 2</Button>
                </CardFooter>
              </Card>
            </li>
          ))}
        </ul>
      </div>
      <div className="absolute -right-10 top-0 ">
        <RefreshQueryButton queryKeys={["tenantOwnApplications"]} />
      </div>
    </div>
  );
};

export default OwnApplicationsPage;
