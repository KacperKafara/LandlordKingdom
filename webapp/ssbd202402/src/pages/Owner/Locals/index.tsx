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
import { t } from "i18next";
import { RefreshCw } from "lucide-react";
import { FC } from "react";

const Locals: FC = () => {
  const { data: locals, isLoading } = useGetOwnLocals();
  // const locals: OwnLocals[] = [];
  // const isLoading = true;

  return (
    <div className="flex h-full justify-center">
      {isLoading && <RefreshCw className="animate-spin" />}
      {!isLoading && locals && locals.length === 0 && (
        <div>No locals found</div>
      )}
      {!isLoading && locals && locals.length > 0 && (
        <div className="my-3 grid w-11/12 grid-cols-1 gap-2 md:grid-cols-2">
          {locals.map((local) => (
            <Card className="relative" key={local.id}>
              <Button className="absolute right-1 top-1" variant="ghost">
                {t("ownerLocals.show")}
              </Button>
              <CardHeader>
                <CardTitle>{local.name}</CardTitle>
                <CardDescription>{local.description}</CardDescription>
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
      )}
    </div>
  );
};

export default Locals;
