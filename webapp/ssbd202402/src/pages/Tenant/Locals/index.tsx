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
import { RefreshCw } from "lucide-react";
import { FC } from "react";
import { useNavigate } from "react-router-dom";
import DataField from "@/components/DataField.tsx";

const ActiveLocals: FC = () => {
  const { data: locals, isLoading } = useGetActiveLocals();
  const navigate = useNavigate();

  return (
    <div className="flex h-full justify-center">
      {isLoading && <RefreshCw className="animate-spin" />}
      {!isLoading && locals && locals.length === 0 && (
        <div>{t("activeLocals.error")}</div>
      )}
      {!isLoading && locals && locals.length > 0 && (
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
                <CardDescription>{local.description}</CardDescription>
              </CardHeader>
              <CardContent>
                <div className="grid w-2/3 grid-cols-4">
                  <DataField
                    label={t("activeLocals.city")}
                    value={local.city}
                  />
                  <DataField
                    label={t("activeLocals.size")}
                    value={local.size}
                  />
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
};

export default ActiveLocals;
