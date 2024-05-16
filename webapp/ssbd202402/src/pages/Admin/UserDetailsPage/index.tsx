import { FC } from "react";
import { useTranslation } from "react-i18next";
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
import { useGetUserQuery } from "@/data/fetchUser";
import { useBlockUser } from "@/data/useBlockUser.ts";
import { useUnblockUser } from "@/data/useUnblockUser.ts";
import { useTenantRole } from "@/data/roles/useTenantRole";
import { useOwnerRole } from "@/data/roles/useOwnerRole";
import { useAdminRole } from "@/data/roles/useAdminRole";

const UserDetailsPage: FC = () => {
  const { t } = useTranslation();
  const { id } = useParams<{ id: string }>();
  const { blockUser } = useBlockUser();
  const { unblockUser } = useUnblockUser();
  const { addTenantRole, removeTenantRole } = useTenantRole();
  const { addOwnerRole, removeOwnerRole } = useOwnerRole();
  const { addAdminRole, removeAdminRole } = useAdminRole();

  const { data, isError } = useGetUserQuery(id!);

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
                  {data.blocked ? (
                    <DropdownMenuItem
                      onClick={async () => {
                        await unblockUser(data.id);
                      }}
                    >
                      {t("block.unblockUserAction")}
                    </DropdownMenuItem>
                  ) : (
                    <DropdownMenuItem
                      onClick={async () => {
                        await blockUser(data.id);
                      }}
                    >
                      {t("block.blockUserAction")}
                    </DropdownMenuItem>
                  )}
                  <DropdownMenuItem>test</DropdownMenuItem>
                  <DropdownMenuItem>test</DropdownMenuItem>
                  <DropdownMenuItem>test</DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </div>
          </div>
        </Card>
      )}
      <Card>
        <CardHeader>
          <CardTitle>Roles</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex gap-2">
            {data?.roles.includes("TENANT") ? (
              <Button onClick={() => removeTenantRole(data?.id || "")}>
                Remove Tenant Role
              </Button>
            ) : (
              <Button onClick={() => addTenantRole(data?.id || "")}>
                Add Tenant Role
              </Button>
            )}
            {data?.roles.includes("OWNER") ? (
              <Button onClick={() => removeOwnerRole(data?.id || "")}>
                Remove Owner Role
              </Button>
            ) : (
              <Button onClick={() => addOwnerRole(data?.id || "")}>
                Add Owner Role
              </Button>
            )}
            {data?.roles.includes("ADMIN") ? (
              <Button onClick={() => removeAdminRole(data?.id || "")}>
                Remove Admin Role
              </Button>
            ) : (
              <Button onClick={() => addAdminRole(data?.id || "")}>
                Add Admin Role
              </Button>
            )}
          </div>
        </CardContent>
      </Card>
    </>
  );
};

export default UserDetailsPage;
