import { FC } from "react";
import { useGetTenantOwnRents } from "@/data/mol/useGetTenantOwnRents";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import DataField from "@/components/DataField";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import { Button } from "@/components/ui/button";
import { useTranslation } from "react-i18next";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { LoadingData } from "@/components/LoadingData";
import { NavLink } from "react-router-dom";

const CurrentRentsPage: FC = () => {
  const { data, isLoading } = useGetTenantOwnRents();
  const { t } = useTranslation();
  const breadcrumbs = useBreadcrumbs([
    { title: "Tenant", path: "/tenant" },
    { title: t("navLinks.currentRents"), path: "/tenant/current-rents" },
  ]);
  if (isLoading) {
    return <LoadingData />;
  }
  if (!data) {
    return <div>{t("tenantRents.noData")}</div>;
  }

  return (
    <div className="relative pt-2">
      {breadcrumbs}
      <div className=" flex w-full justify-center">
        <ul className="flex w-4/5 flex-wrap gap-2 py-4">
          {data?.map((rent) => (
            <li key={rent.id} className="min-w-80 flex-1">
              <Card className="">
                <CardHeader>
                  <CardTitle className="text-2xl">{rent.local.name}</CardTitle>
                  <CardDescription>
                    {rent.local.address.country +
                      ", " +
                      rent.local.address.street +
                      " " +
                      rent.local.address.number +
                      ", " +
                      rent.local.address.zipCode.substring(0, 2) +
                      "-" +
                      rent.local.address.zipCode.substring(2) +
                      " " +
                      rent.local.address.city}
                  </CardDescription>
                  <p></p>
                </CardHeader>
                <CardContent className="grid grid-cols-2">
                  <DataField
                    label={t("tenantRents.startDate")}
                    value={rent.startDate}
                  />
                  <DataField
                    label={t("tenantRents.endDate")}
                    value={rent.endDate}
                  />
                  <DataField
                    label={t("tenantRents.fixedFee")}
                    value={
                      (rent.local.rentalFee + rent.local.marginFee).toString() +
                      " " +
                      t("currency")
                    }
                  />
                  <DataField
                    label={t("tenantRents.balance")}
                    value={rent.balance.toString() + " " + t("currency")}
                  />
                  <DataField
                    label={t("tenantRents.localSize")}
                    value={rent.local.size.toString()}
                  />
                  <p className="col-span-2 my-3 text-xl font-bold">
                    {t("tenantRents.owner")}
                  </p>
                  <DataField
                    label={t("tenantRents.name")}
                    value={`${rent.owner.firstName} ${rent.owner.lastName}`}
                  />
                  <DataField
                    label={t("tenantRents.email")}
                    value={rent.owner.email}
                  />
                  <DataField
                    label={t("tenantRents.login")}
                    value={rent.owner.login}
                  />
                </CardContent>
                <CardFooter className="w-full justify-center gap-3">
                  <Button className="flex-auto" asChild>
                    <NavLink to={`/tenant/rents/${rent.id}`}>Details</NavLink>
                  </Button>
                  <Button className="flex-auto">Action 2</Button>
                  <Button className="flex-auto">Action 3</Button>
                </CardFooter>
              </Card>
            </li>
          ))}
        </ul>
      </div>
      <div className="absolute -right-10 top-0 ">
        <RefreshQueryButton queryKeys={["tenantOwnRents"]} />
      </div>
    </div>
  );
};

export default CurrentRentsPage;
