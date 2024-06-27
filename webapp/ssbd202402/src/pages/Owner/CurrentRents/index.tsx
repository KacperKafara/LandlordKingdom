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
import { useGetOwnerCurrentRents } from "@/data/mol/useGetOwnerCurrentRents";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { t } from "i18next";
import { FC, useState } from "react";
import { ChangeEndDate } from "./ChangeEndDate";
import { useNavigate } from "react-router-dom";
import { getAddressString } from "@/utils/address";
import { PageChangerComponent } from "@/pages/Components/PageChangerComponent.tsx";
import { RefreshCw } from "lucide-react";

const CurrentOwnerRentsPage: FC = () => {
  const breadCrumbs = useBreadcrumbs([
    { title: t("currentOwnerRents.title"), path: "/owner" },
    { title: t("currentOwnerRents.rents"), path: "/owner/current-rents" },
  ]);
  const [pageNumber, setPageNumber] = useState(0);
  const [pageSize, setPageSize] = useState(6);
  const { data: rentsPage, isLoading } = useGetOwnerCurrentRents({
    pageNumber: pageNumber,
    pageSize: pageSize,
  });
  const rents = rentsPage?.rents;
  const navigate = useNavigate();

  return (
    <div className="flex flex-col justify-center">
      <div className="flex flex-row items-center justify-between">
        {breadCrumbs}
        <RefreshQueryButton queryKeys={["ownerCurrentRents"]} />
      </div>
      {isLoading && <RefreshCw className="animate-spin" />}
      {!isLoading && (!rents || rents.length === 0) && (
        <div className="flex justify-center text-2xl">
          {t("currentOwnerRents.rentsNotFound")}
        </div>
      )}
      <div className="flex w-full justify-center">
        <ul className="flex w-4/5 flex-wrap gap-2 py-4">
          {rents?.map((rent) => (
            <li key={rent.id} className="w-full min-w-[31rem] flex-1">
              <Card>
                <CardHeader>
                  <CardTitle>{rent.local.name}</CardTitle>
                  <CardDescription>
                    {getAddressString(rent.local.address)}
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
                    value={rent.balance.toString() + " " + t("currency")}
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
                  <ChangeEndDate
                    startDate={rent.startDate}
                    id={rent.id}
                    endDate={rent.endDate}
                  />
                  <Button
                    className="flex-1"
                    onClick={() =>
                      navigate(`rent/${rent.id}`, {
                        state: { prevPath: "/owner/current-rents" },
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

export default CurrentOwnerRentsPage;
