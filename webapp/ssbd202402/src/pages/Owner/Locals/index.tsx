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
import { useGetOwnLocals } from "@/data/mol/useGetOwnLocals";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { t } from "i18next";
import { FC, useState } from "react";
import { LoadingData } from "@/components/LoadingData";
import { useNavigate } from "react-router-dom";
import { PageChangerComponent } from "@/pages/Admin/Components/PageChangerComponent";

const Locals: FC = () => {
  const { data: locals, isLoading } = useGetOwnLocals();
  const [pageNumber, setPageNumber] = useState(0);
  const [pageSize, setPageSize] = useState(6);
  const breadCrumbs = useBreadcrumbs([
    { title: t("ownerLocals.title"), path: "/owner" },
    { title: t("ownerLocals.locals"), path: "/owner/locals" },
  ]);

  const navigate = useNavigate();

  if (isLoading) {
    return <LoadingData />;
  }

  if (!isLoading && locals && locals.length === 0) {
    return (
      <div className="flex h-full justify-center">
        <div className="w-full pt-2">
          {breadCrumbs}
          <div className="mt-5 flex flex-col items-center">
            <p className="text-xl">{t("ownerLocals.noLocalsFound")}</p>
            <div className="flex gap-3">
              <Button className="mt-3 w-fit">
                {/* TODO: Add a link to the create local page */}
                {t("ownerLocals.addFirstLocal")}
              </Button>
              <RefreshQueryButton className="mt-3" queryKeys={["ownLocals"]} />
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="relative mt-1 flex flex-col justify-center">
      {breadCrumbs}
      <div className="flex justify-center">
        <div className="flex h-full w-11/12 flex-col justify-center">
          <ul className="flex flex-wrap gap-2 py-4">
            {locals?.map((local) => (
              <li key={local.id} className="w-full min-w-[35rem] flex-1">
                <Card className="relative">
                  <Button
                    onClick={() => navigate(`local/${local.id}`)}
                    className="absolute right-1 top-1"
                    variant="ghost"
                  >
                    {t("ownerLocals.show")}
                  </Button>
                  <CardHeader>
                    <CardTitle>{local.name}</CardTitle>
                    <CardDescription>
                      {local.address.street} {local.address.number},{" "}
                      {local.address.zipCode} {local.address.city}
                    </CardDescription>
                  </CardHeader>
                  <CardContent className="flex-grow">
                    <p>{local.description}</p>
                  </CardContent>
                  <CardFooter>
                    <p>{t(`localState.${local.state}`)}</p>
                  </CardFooter>
                </Card>
              </li>
            ))}
          </ul>
          {/* <PageChangerComponent
            totalPages={localsPage.totalPages}
            pageNumber={pageNumber}
            pageSize={pageSize}
            setPageNumber={setPageNumber}
            setNumberOfElements={setPageSize}
            className="mb-3 flex items-center justify-end gap-12"
          /> */}
        </div>
      </div>
      <RefreshQueryButton
        className="absolute -right-9 top-0"
        queryKeys={["ownLocals"]}
      />
    </div>
  );
};

export default Locals;
