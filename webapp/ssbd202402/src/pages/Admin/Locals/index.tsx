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
import { RefreshCw } from "lucide-react";
import { FC } from "react";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import RefreshQueryButton from "@/components/RefreshQueryButton";

const AllLocals: FC = () => {
  const { data: locals, isLoading } = useGetAllLocals();
  const breadCrumbs = useBreadcrumbs([
    { title: t("roles.administrator"), path: "/admin" },
    { title: t("allLocals.title"), path: "/admin/locals" },
  ]);

  if (isLoading) {
    return (
      <div className="flex h-full justify-center">
        <div className="mt-10 h-full">
          <RefreshCw className="size-14 animate-spin" />
        </div>
      </div>
    );
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
    <div className="relative mt-1 flex h-full flex-col justify-center">
      {breadCrumbs}
      <div className="my-3 grid w-11/12 grid-cols-1 gap-2 self-center md:grid-cols-2">
        {locals.map((local) => (
          <Card className="relative" key={local.id}>
            <Button className="absolute right-1 top-1" variant="ghost">
              {t("allLocals.show")}
            </Button>
            <CardHeader>
              <CardTitle>{local.name}</CardTitle>
              <CardDescription>
                <p>{t("allLocals.localOwner") + " " + local.ownerLogin}</p>
                <p>{local.description}</p>
              </CardDescription>
            </CardHeader>
            <CardContent>
              <p>
                {local.address.street} {local.address.number},{" "}
                {local.address.zipCode} {local.address.city}
              </p>
            </CardContent>
            <CardFooter>
              <p>{t(`localState.${local.state}`)}</p>
            </CardFooter>
          </Card>
        ))}
      </div>
      <RefreshQueryButton
        className="absolute -right-9 top-0"
        queryKeys={["allLocals"]}
      />
    </div>
  );
};

export default AllLocals;
