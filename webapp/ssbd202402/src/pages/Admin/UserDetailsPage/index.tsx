import { FC, useState } from "react";
import { useTranslation } from "react-i18next";
import { Navigate, useParams } from "react-router-dom";
import {
  Card,
  CardContent,
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
import RefreshQueryButton from "@/components/RefreshQueryButton";
import DataField from "@/pages/Me/DataField";

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
    <div className="flex flex-col  items-center">
      {/* <NavLink to={`/admin/users`}>{t("userDetailsPage.goBack")}</NavLink> */}
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
        <Card className="relative mt-3 w-4/5 ">
          <div className="absolute top-0 right-0 flex">
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
            <RefreshQueryButton queryKeys={["meData"]} />
          </div>
          <div>
            <CardHeader className=" items-center">
              <CardTitle>{t("mePage.basicInformation")}</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="flex justify-center">
                <div className="grid grid-cols-2 grid- w-2/3 gap-2">
                  <DataField
                    label={t("userDetailsPage.firstName")}
                    value={data?.data.firstName ?? "-"}
                  />
                  <DataField
                    label={t("userDetailsPage.lastName")}
                    value={data?.data.lastName ?? "-"}
                  />
                  <DataField
                    label={t("userDetailsPage.email")}
                    value={data?.data.email ?? "-"}
                  />
                  <DataField
                    label={t("userDetailsPage.login")}
                    value={data?.data.login ?? "-"}
                  />
                  <DataField
                    label={t("userDetailsPage.blocked")}
                    value={data?.data.blocked ? "true" : "false"}
                  />
                  <DataField
                    label={t("userDetailsPage.verified")}
                    value={data?.data.verified ? "true" : "false"}
                  />
                  <DataField
                    label={t("userDetailsPage.lastSuccessfulLogin")}
                    value={data?.data.lastSuccessfulLogin ?? "-"}
                  />
                  <DataField
                    label={t("userDetailsPage.lastFailedLogin")}
                    value={data?.data.lastFailedLogin ?? "-"}
                  />
                  <DataField
                    label={t("userDetailsPage.language")}
                    value={data?.data.language ?? "-"}
                  />

                </div>
              </div>
            </CardContent>
          </div>
        </Card>
      )}
      <Card className="mt-3 w-4/5">
        <CardHeader className="items-center">
          <CardTitle>Roles</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex gap-2 justify-center">
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


    </div>
  );
};

export default UserDetailsPage;
