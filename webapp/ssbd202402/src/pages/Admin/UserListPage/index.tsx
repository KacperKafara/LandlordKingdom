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
import { useToast } from "@/components/ui/use-toast";
import { useResetOtherUserEmailAddress } from "@/data/useUpdateEmailAddress.ts";
import { useResetPassword } from "@/data/useUserPassword";
import { useNavigate } from "react-router-dom";
import UserFilter from "./UserFilter";
import { useFilteredUsers } from "@/data/useFilteredUsers";
import PageChanger from "./PageChanger";

interface UserData {
  login: string;
  email: string;
}

const UserListPage: FC = () => {
  const { toast } = useToast();
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { users } = useFilteredUsers();
  const { resetPassword } = useResetPassword();
  const { updateEmail } = useResetOtherUserEmailAddress();
  const [openPaswordResetDialog, setOpenPasswordResetDialog] =
    useState<boolean>(false);

  const [userData, setUserData] = useState<UserData>();

  const handlePasswordResetClick = (data: UserData) => {
    setUserData(data);
    setOpenPasswordResetDialog(true);
  };

  const handleEmailUpdateClick = async (id: string) => {
    await updateEmail(id);
  };

  const handlePasswordReset = async () => {
    const result = await resetPassword(userData?.email || "");

    if (result === 200) {
      toast({
        title: t("userListPage.resetUserPasswordToastTitleSuccess"),
        description: t("userListPage.resetUserPasswordToastDescriptionSuccess"),
      });
    } else {
      toast({
        title: t("userListPage.resetUserPasswordToastTitleFail"),
        description: t("userListPage.resetUserPasswordToastDescriptionFail"),
      });
    }

    setOpenPasswordResetDialog(false);
  };

  return (
    <>
      <div className="flex justify-center m-5">
        <UserFilter />
      </div>
      <div className="flex justify-center">
        <div className="w-3/5">
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
              <TableRow className="hover:bg-white">
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
                          <DropdownMenuItem
                            onClick={() => handleEmailUpdateClick(user.id)}
                          >
                            {t("userListPage.resetUserEmailAction")}
                          </DropdownMenuItem>
                          <DropdownMenuSeparator />
                          <DropdownMenuItem
                            onClick={() =>
                              navigate(`/admin/users/user/${user.id}`)
                            }
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
        </div>
      </div>
      <div className="flex justify-center">
        <PageChanger />
      </div>
    </>
  );
};

export default UserListPage;
