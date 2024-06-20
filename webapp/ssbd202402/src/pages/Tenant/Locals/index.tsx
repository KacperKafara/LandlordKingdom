import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { useGetActiveLocals } from "@/data/mol/useGetActiveLocals.ts";
import { t } from "i18next";
import { FC, useState } from "react";
import { useNavigate } from "react-router-dom";
import DataField from "@/components/DataField.tsx";
import { LoadingData } from "@/components/LoadingData.tsx";
import { PageChangerComponent } from "@/pages/Components/PageChangerComponent.tsx";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import RefreshQueryButton from "@/components/RefreshQueryButton";

const ActiveLocals: FC = () => {
  const [pageNumber, setPageNumber] = useState(0);
  const [pageSize, setPageSize] = useState(6);
  const { data: localsPage, isLoading } = useGetActiveLocals({
    pageNumber: pageNumber,
    pageSize: pageSize,
  });
  const navigate = useNavigate();
  const locals = localsPage?.locals;

  const breadcrumbs = useBreadcrumbs([
    { title: t("breadcrumbs.tenant"), path: "/tenant" },
    { title: t("breadcrumbs.locals"), path: "/tenant/locals" },
  ]);

  if (!localsPage || !locals) {
    return <LoadingData />;
  }

  if (isLoading) {
    return <LoadingData />;
  }

  if (locals.length !== 0 && localsPage.pages) {
    return (
      <div className="justify-center">
        <div className="flex flex-row items-center justify-between">
          {breadcrumbs}
          <RefreshQueryButton queryKeys={["tenantOwnRents"]} />
        </div>
        <div className="h -full  flex justify-center">
          <div className="my-3 grid w-11/12 grid-cols-1 gap-2 md:grid-cols-2">
            {locals.map((local) => (
              <Card className="relative" key={local.id}>
                <Button
                  className="absolute right-1 top-1"
                  variant="ghost"
                  onClick={() => navigate(`/tenant/locals/${local.id}`)}
                >
                  {t("activeLocals.show")}
                </Button>
                <CardHeader>
                  <CardTitle>{local.name}</CardTitle>
                  <CardDescription>
                    {local.description.substring(0, 80) + "..."}
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <div className="grid grid-cols-2">
                    <DataField
                      label={t("activeLocals.city")}
                      value={local.city}
                    />
                    <DataField
                      label={t("activeLocals.size")}
                      value={local.size + " m²"}
                    />
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
        <PageChangerComponent
          totalPages={localsPage.pages}
          pageNumber={pageNumber}
          pageSize={pageSize}
          setPageNumber={setPageNumber}
          setNumberOfElements={setPageSize}
          className="mb-3 flex justify-between"
        ></PageChangerComponent>
      </div>
    );
  }
};

export default ActiveLocals;
