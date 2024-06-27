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
import { PageChangerComponent } from "@/pages/Components/PageChangerComponent";
import { toLocaleFixed } from "@/utils/currencyFormat";
import { t } from "i18next";
import { RefreshCw } from "lucide-react";
import { FC, useState } from "react";
import { useNavigate } from "react-router-dom";

const ArchivalOwnerRentsPage: FC = () => {
  const breadCrumbs = useBreadcrumbs([
    { title: t("currentOwnerRents.title"), path: "/owner" },
    {
      title: t("currentOwnerRents.archivalRents"),
      path: "/owner/archival-rents",
    },
  ]);
  const [pageNumber, setPageNumber] = useState(0);
  const [pageSize, setPageSize] = useState(6);
  const { data: rentsPage, isLoading } = useGetOwnerArchivalRents({
    pageNumber: pageNumber,
    pageSize: pageSize,
  });
  const rents = rentsPage?.rents;
  const navigate = useNavigate();
  return (
    <div className="flex flex-col justify-center">
      <div className="flex flex-row items-center justify-between">
        {breadCrumbs}
        <RefreshQueryButton queryKeys={["ownerArchivalRents"]} />
      </div>
      {isLoading && <RefreshCw className="animate-spin" />}
      {!isLoading && (!rentsPage?.rents || rentsPage.rents.length === 0) && (
        <div className="flex justify-center text-2xl">
          {t("currentOwnerRents.rentsNotFound")}
        </div>
      )}
      <div className="flex w-full justify-center">
        <ul className="flex flex-wrap gap-2 py-4">
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
                    value={toLocaleFixed(rent.balance)}
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
      {rentsPage && rentsPage.pages !== 0 && (
        <PageChangerComponent
          totalPages={rentsPage.pages}
          pageNumber={pageNumber}
          pageSize={pageSize}
          setPageNumber={setPageNumber}
          setNumberOfElements={setPageSize}
          className="mb-3 flex justify-between"
        ></PageChangerComponent>
      )}
    </div>
  );
};

export default ArchivalOwnerRentsPage;
