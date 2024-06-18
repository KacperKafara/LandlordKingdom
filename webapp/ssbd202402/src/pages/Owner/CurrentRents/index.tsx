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
import {FC, useState} from "react";
import { ChangeEndDate } from "./ChangeEndDate";
import { useNavigate } from "react-router-dom";
import { getAddressString } from "@/utils/address";
import {PageChangerComponent} from "@/pages/Components/PageChangerComponent.tsx";
import {LoadingData} from "@/components/LoadingData.tsx";

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

  if (!rentsPage || !rents) {
    return <LoadingData />;
  }

  if (isLoading) {
    return <LoadingData />;
  }

  if (rents.length !== 0 && rentsPage.pages) {
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
                                  state: {prevPath: "/owner/current-rents"},
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
          <PageChangerComponent
              totalPages={rentsPage.pages}
              pageNumber={pageNumber}
              pageSize={pageSize}
              setPageNumber={setPageNumber}
              setNumberOfElements={setPageSize}
              className="mb-3 flex justify-between"
          >
          </PageChangerComponent>
          <RefreshQueryButton
              className="absolute -right-9 top-1"
              queryKeys={["ownerCurrentRents"]}
          />
        </div>
    );
  }
};

export default CurrentOwnerRentsPage;
