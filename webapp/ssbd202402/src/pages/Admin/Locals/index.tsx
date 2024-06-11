import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { useGetAllLocals } from "@/data/mol/useGetAllLocals";
import { t } from "i18next";
import { FC, useState } from "react";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import { useNavigate } from "react-router-dom";
import { LoadingData } from "@/components/LoadingData";
import { PageChangerComponent } from "../Components/PageChangerComponent";
import { getAddressString } from "@/utils/address";

const AllLocals: FC = () => {
  const [pageNumber, setPageNumber] = useState(0);
  const [pageSize, setPageSize] = useState(6);

  const { data: localsPage, isLoading } = useGetAllLocals({
    pageNumber: pageNumber,
    pageSize: pageSize,
  });
  const locals = localsPage?.locals;

  const breadCrumbs = useBreadcrumbs([
    { title: t("roles.administrator"), path: "/admin" },
    { title: t("allLocals.title"), path: "/admin/locals" },
  ]);

  const navigate = useNavigate();
  if (isLoading) {
    return <LoadingData />;
  }

  if (!locals || locals.length === 0) {
    return (
      <div className="relative flex h-full flex-col pt-1">
        {breadCrumbs}
        <div className="flex w-full justify-center pt-3">
          <p className="text-xl">{t("allLocals.noLocalsFound")}</p>
        </div>
        <RefreshQueryButton
          className="absolute -right-8 top-0"
          queryKeys={["allLocals"]}
        />
      </div>
    );
  }

  return (
    <div className="relative mt-1 flex flex-col justify-center">
      {breadCrumbs}
      <div className="flex justify-center">
        <div className="flex h-full w-11/12 flex-col justify-center">
          <ul className="flex flex-wrap gap-2 py-4">
            {locals.map((local) => (
              <li key={local.id} className="w-full min-w-[35rem] flex-1">
                <Card className="relative">
                  <Button
                    onClick={() => navigate(`local/${local.id}`)}
                    className="absolute right-1 top-1"
                    variant="ghost"
                  >
                    {t("allLocals.show")}
                  </Button>
                  <CardHeader>
                    <CardTitle>{local.name}</CardTitle>
                    <CardDescription>
                      {getAddressString(local.address)}
                      {(local.ownerLogin && (
                        <>
                          {t("allLocals.localOwner") + " " + local.ownerLogin}
                        </>
                      )) || <>{t("allLocals.noOwner")}</>}
                    </CardDescription>
                  </CardHeader>
                  <CardContent className="flex-grow">
                    {local.description}
                  </CardContent>
                  <CardFooter>{t(`localState.${local.state}`)}</CardFooter>
                </Card>
              </li>
            ))}
          </ul>
          <PageChangerComponent
            totalPages={localsPage.totalPages}
            pageNumber={pageNumber}
            pageSize={pageSize}
            setPageNumber={setPageNumber}
            setNumberOfElements={setPageSize}
            className="mb-3 flex items-center justify-end gap-12"
          ></PageChangerComponent>
        </div>
      </div>
      <RefreshQueryButton
        className="absolute -right-9 top-0"
        queryKeys={["allLocals"]}
      />
    </div>
  );
};

export default AllLocals;
