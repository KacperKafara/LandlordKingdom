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
import { useGetOwnerArchivalRents } from "@/data/mol/useGetOwnerArchivalRents";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { t } from "i18next";
import { RefreshCw } from "lucide-react";
import { FC } from "react";
import { useNavigate } from "react-router-dom";

const ArchivalOwnerRentsPage: FC = () => {
  const breadCrumbs = useBreadcrumbs([
    { title: t("currentOwnerRents.title"), path: "/owner" },
    {
      title: t("currentOwnerRents.archivalRents"),
      path: "/owner/archival-rents",
    },
  ]);
  const { data: rents, isLoading } = useGetOwnerArchivalRents();
  const navigate = useNavigate();
  if (isLoading) {
    return (
      <div className="flex justify-center">
        <div className="mt-10 h-full">
          <RefreshCw className="size-14 animate-spin" />
        </div>
      </div>
    );
  }

  if (!isLoading && rents?.length === 0) {
    return (
      <div className="relative w-full pt-2">
        {breadCrumbs}
        <div className="mt-5 flex flex-col items-center">
          <p className="text-xl">{t("currentOwnerRents.noRentsFound")}</p>
          <RefreshQueryButton
            className="absolute -right-9 top-1"
            queryKeys={["ownerCurrentRents"]}
          />
        </div>
      </div>
    );
  }

  return (
    <div className="relative pt-2">
      {breadCrumbs}
      <div className="flex w-full justify-center">
        <ul className="flex w-4/5 flex-wrap gap-2 py-4">
          {rents?.map((rent) => (
            <li key={rent.id} className="w-full min-w-[31rem] flex-1">
              <Card>
                <CardHeader>
                  <CardTitle>{rent.local.name}</CardTitle>
                  <CardDescription>
                    {rent.local.address.country}, {rent.local.address.street}{" "}
                    {rent.local.address.number},{" "}
                    {rent.local.address.zipCode.substring(0, 2)}-
                    {rent.local.address.zipCode.substring(2)}{" "}
                    {rent.local.address.city}
                  </CardDescription>
                </CardHeader>
                <CardContent className="grid grid-cols-2">
                  <DataField
                    label={t("currentOwnerRents.startDate")}
                    value={rent.startDate}
                  />
                  <DataField
                    label={t("currentOwnerRents.endDate")}
                    value={rent.endDate}
                  />
                  <DataField
                    label={t("currentOwnerRents.balance")}
                    value={rent.balance.toString()}
                  />
                  <p className="col-span-2 my-3 text-xl font-bold">
                    {t("currentOwnerRents.tenant")}
                  </p>
                  <DataField
                    label={t("currentOwnerRents.name")}
                    value={rent.tenant.firstName + " " + rent.tenant.lastName}
                  />
                  <DataField
                    label={t("currentOwnerRents.email")}
                    value={rent.tenant.email}
                  />
                </CardContent>
                <CardFooter className="w-full justify-center gap-3">
                  <Button
                    className="flex-auto"
                    onClick={() =>
                      navigate(`rent/${rent.id}`, {
                        state: { prevPath: "/owner/archival-rents" },
                      })
                    }
                  >
                    {t("currentOwnerRents.rentDetails")}
                  </Button>
                </CardFooter>
              </Card>
            </li>
          ))}
        </ul>
      </div>
      <RefreshQueryButton
        className="absolute -right-9 top-1"
        queryKeys={["ownerArchivalRents"]}
      />
    </div>
  );
};

export default ArchivalOwnerRentsPage;
