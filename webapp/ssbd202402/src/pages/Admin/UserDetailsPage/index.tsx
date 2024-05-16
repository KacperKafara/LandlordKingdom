import { FC, useState } from "react";
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
import UpdateUserDataDialog from "./updateUserDataDialog";

const UserDetailsPage: FC = () => {
  const { t } = useTranslation();
  const { id } = useParams<{ id: string }>();
  const { blockUser } = useBlockUser();
  const { unblockUser } = useUnblockUser();
  const { addTenantRole, removeTenantRole } = useTenantRole();
  const { addOwnerRole, removeOwnerRole } = useOwnerRole();
  const { addAdminRole, removeAdminRole } = useAdminRole();
  const [openUpdateUserDataDialog, setOpenUpdateUserDataDialog] =
    useState<boolean>(false);

  const { data } = useGetUserQuery(id!);

  if (data?.status && (data.status < 200 || data?.status >= 300)) {
    return <Navigate to="/admin/users" />;
  }

  const handleUpdateUserDataDialogOpen = () => {
    setOpenUpdateUserDataDialog(true);
  };

  return (
    <>
      <NavLink to={`/admin/users`}>{t("userDetailsPage.goBack")}</NavLink>
      {openUpdateUserDataDialog && id && data && (
        <UpdateUserDataDialog
          id={id}
          userData={{
            firstName: data.data.firstName,
            lastName: data.data.lastName,
            language: data.data.language,
          }}
          etag={data.headers.etag}
          setOpenUpdateUserDataDialog={setOpenUpdateUserDataDialog}
          openUpdateUserDataDialog={openUpdateUserDataDialog}
        />
      )}
      {data && (
        <Card>
          <div className="flex justify-between items-start">
            <div>
              <div className="flex">
                <CardHeader>
                  <CardTitle>
                    {data.data.firstName} {data.data.lastName}
                  </CardTitle>
                  <CardDescription>{data.data.email}</CardDescription>
                </CardHeader>
              </div>
              <CardContent>
                <p>
                  {t("userDetailsPage.login")}: {data.data.login}
                </p>
                <p>
                  {t("userDetailsPage.blocked")}:{" "}
                  {data.data.blocked ? t("common.yes") : t("common.no")}
                </p>
                <p>
                  {t("userDetailsPage.verified")}:{" "}
                  {data.data.verified ? t("common.yes") : t("common.no")}
                </p>
                <p>
                  {t("userDetailsPage.lastSuccessfulLogin")}:{" "}
                  {data.data.lastSuccessfulLogin}
                </p>
                <p>
                  {t("userDetailsPage.lastFailedLogin")}:{" "}
                  {data.data.lastFailedLogin}
                </p>
                <p>
                  {t("userDetailsPage.language")}: {data.data.language}
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
                  {data.data.blocked ? (
                    <DropdownMenuItem
                      onClick={async () => {
                        await unblockUser(data.data.id);
                      }}
                    >
                      {t("block.unblockUserAction")}
                    </DropdownMenuItem>
                  ) : (
                    <DropdownMenuItem
                      onClick={async () => {
                        await blockUser(data.data.id);
                      }}
                    >
                      {t("block.blockUserAction")}
                    </DropdownMenuItem>
                  )}
                  <DropdownMenuItem
                    onClick={() => handleUpdateUserDataDialogOpen()}
                  >
                    {t("updateDataForm.updateUserData")}
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </div>
          </div>
        </Card>
      )}
      <Card className="mt-3">
        <CardHeader>
          <CardTitle>Roles</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex gap-2">
            {data?.data.roles.includes("TENANT") ? (
              <Button onClick={() => removeTenantRole(data?.data.id || "")}>
                Remove Tenant Role
              </Button>
            ) : (
              <Button onClick={() => addTenantRole(data?.data.id || "")}>
                Add Tenant Role
              </Button>
            )}
            {data?.data.roles.includes("OWNER") ? (
              <Button onClick={() => removeOwnerRole(data?.data.id || "")}>
                Remove Owner Role
              </Button>
            ) : (
              <Button onClick={() => addOwnerRole(data?.data.id || "")}>
                Add Owner Role
              </Button>
            )}
            {data?.data.roles.includes("ADMIN") ? (
              <Button onClick={() => removeAdminRole(data?.data.id || "")}>
                Remove Admin Role
              </Button>
            ) : (
              <Button onClick={() => addAdminRole(data?.data.id || "")}>
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
