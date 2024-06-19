import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { FC, useState } from "react";
import { useTranslation } from "react-i18next";
import { useResetPassword } from "@/data/useResetPassword";
import { useNavigate } from "react-router-dom";
import UserFilter from "./UserFilter";
import { useFilteredUsers } from "@/data/useFilteredUsers";
import PageChanger from "./PageChanger";
import { useBlockUser } from "@/data/useBlockUser.ts";
import { useUnblockUser } from "@/data/useBlockUser.ts";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";

interface UserData {
  login: string;
  email: string;
}

const UserListPage: FC = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { users } = useFilteredUsers();
  const resetPassword = useResetPassword();
  const [openPaswordResetDialog, setOpenPasswordResetDialog] =
    useState<boolean>(false);
  const { blockUser } = useBlockUser();
  const { unblockUser } = useUnblockUser();
  const [userData, setUserData] = useState<UserData>();
  const test = useBreadcrumbs([
    { title: "Admin", path: "/admin" },
    { title: "Users", path: "/admin/users" },
  ]);

  const handlePasswordResetClick = (data: UserData) => {
    setUserData(data);
    setOpenPasswordResetDialog(true);
  };

  const handlePasswordReset = async () => {
    await resetPassword.mutateAsync({
      email: userData?.email || "",
    });

    setOpenPasswordResetDialog(false);
  };

  return (
    <div className="flex flex-col justify-center">
      {test}
      <div className="m-5 flex justify-center">
        <UserFilter />
      </div>
      <AlertDialog open={openPaswordResetDialog}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>
              {t("userListPage.resetUserPasswordTitle")}
            </AlertDialogTitle>
            <AlertDialogDescription>
              {t("userListPage.resetUserPasswordDescription")}
              <span className="font-bold">{userData?.login}</span>?
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel
              onClick={() => setOpenPasswordResetDialog(false)}
            >
              {t("cancel")}
            </AlertDialogCancel>
            <AlertDialogAction onClick={() => handlePasswordReset()}>
              {t("confirm")}
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>{t("userListPage.firstName")}</TableHead>
            <TableHead>{t("userListPage.lastName")}</TableHead>
            <TableHead>{t("userListPage.login")}</TableHead>
            <TableHead>{t("userListPage.email")}</TableHead>
            <TableHead className="w-1"></TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {users &&
            users.map((user) => (
              <TableRow key={user.login}>
                <TableCell>{user.firstName}</TableCell>
                <TableCell>{user.lastName}</TableCell>
                <TableCell>{user.login}</TableCell>
                <TableCell>{user.email}</TableCell>
                <TableCell className="w-1">
                  <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                      <Button variant="ghost">...</Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent>
                      <DropdownMenuLabel>
                        {t("userListPage.actions")}
                      </DropdownMenuLabel>
                      <DropdownMenuItem
                        onClick={() =>
                          handlePasswordResetClick({
                            login: user.login,
                            email: user.email,
                          })
                        }
                      >
                        {t("userListPage.resetUserPasswordAction")}
                      </DropdownMenuItem>
                      {user.blocked ? (
                        <DropdownMenuItem
                          onClick={async () => {
                            await unblockUser(user.id);
                          }}
                        >
                          {t("block.unblockUserAction")}
                        </DropdownMenuItem>
                      ) : (
                        <DropdownMenuItem
                          onClick={async () => {
                            await blockUser(user.id);
                          }}
                        >
                          {t("block.blockUserAction")}
                        </DropdownMenuItem>
                      )}
                      <DropdownMenuSeparator />
                      <DropdownMenuItem
                        onClick={() => navigate(`/admin/users/${user.id}`)}
                      >
                        {t("userListPage.viewDetails")}
                      </DropdownMenuItem>
                    </DropdownMenuContent>
                  </DropdownMenu>
                </TableCell>
              </TableRow>
            ))}
        </TableBody>
      </Table>
      <div className="flex justify-end">
        <PageChanger />
      </div>
    </div>
  );
};

export default UserListPage;
