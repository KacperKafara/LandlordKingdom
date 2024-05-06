import { FC } from "react";
import { useTranslation } from "react-i18next";
import { fetchUser } from "@/data/fetchUser";
import { useQuery } from "@tanstack/react-query";
import { NavLink, Navigate, useParams } from "react-router-dom";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";

const UserDetailsPage: FC = () => {
  const { t } = useTranslation();
  const { id } = useParams<{ id: string }>();

  const { data, isError } = useQuery({
    queryKey: ["user", id],
    queryFn: () => (id ? fetchUser(id) : Promise.resolve(null)),
  });

  if (isError) {
    return <Navigate to="/admin/users" />;
  }

  return (
    <>
      <NavLink to={`/admin/users`}>{t("userDetailsPage.goBack")}</NavLink>
      {data && (
        <Card>
          <div className="flex justify-between items-start">
            <div>
              <CardHeader>
                <CardTitle>
                  {data.firstName} {data.lastName}
                </CardTitle>
                <CardDescription>{data.email}</CardDescription>
              </CardHeader>
              <CardContent>
                <p>
                  {t("userDetailsPage.login")}: {data.login}
                </p>
                <p>
                  {t("userDetailsPage.blocked")}:{" "}
                  {data.blocked ? t("common.yes") : t("common.no")}
                </p>
                <p>
                  {t("userDetailsPage.verified")}:{" "}
                  {data.verified ? t("common.yes") : t("common.no")}
                </p>
                <p>
                  {t("userDetailsPage.lastSuccessfulLogin")}:{" "}
                  {data.lastSuccessfulLogin}
                </p>
                <p>
                  {t("userDetailsPage.lastFailedLogin")}: {data.lastFailedLogin}
                </p>
                <p>
                  {t("userDetailsPage.language")}: {data.language}
                </p>
              </CardContent>
            </div>
            <div className="ml-auto">
              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <Button variant="ghost">...</Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent>
                  <DropdownMenuLabel>
                    {t("userDetailsPage.actions")}
                  </DropdownMenuLabel>
                  <DropdownMenuItem>test</DropdownMenuItem>
                  <DropdownMenuItem>test</DropdownMenuItem>
                  <DropdownMenuItem>test</DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </div>
          </div>
        </Card>
      )}
    </>
  );
};

export default UserDetailsPage;
