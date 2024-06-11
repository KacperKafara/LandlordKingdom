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
import { FC } from "react";
import { LoadingData } from "@/components/LoadingData";
import { useNavigate } from "react-router-dom";

const Locals: FC = () => {
  const { data: locals, isLoading } = useGetOwnLocals();
  const breadCrumbs = useBreadcrumbs([
    { title: t("ownerLocals.title"), path: "/owner" },
    { title: t("ownerLocals.locals"), path: "/owner/locals" },
  ]);

  const navigate = useNavigate();

  if (isLoading) {
    return <LoadingData />;
  }

  return (
    <div className="flex h-full justify-center">
      {!isLoading && locals && locals.length === 0 && (
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
      )}
      {!isLoading && locals && locals.length > 0 && (
        <div className="w-full">
          <div className="pt-2">{breadCrumbs}</div>
          <div className="relative flex w-full justify-center">
            <div className="my-3 grid w-11/12 grid-cols-1 gap-2 md:grid-cols-2">
              {locals.map((local) => (
                <Card className="relative flex flex-col" key={local.id}>
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
                      <p>
                        {local.address.street} {local.address.number},{" "}
                        {local.address.zipCode} {local.address.city}
                      </p>
                    </CardDescription>
                  </CardHeader>
                  <CardContent className="flex-grow">
                    <p>{local.description}</p>
                  </CardContent>
                  <CardFooter>
                    <p>{t(`localState.${local.state}`)}</p>
                  </CardFooter>
                </Card>
              ))}
            </div>
            <RefreshQueryButton
              className="absolute -right-9 -top-6"
              queryKeys={["ownLocals"]}
            />
          </div>
        </div>
      )}
    </div>
  );
};

export default Locals;
