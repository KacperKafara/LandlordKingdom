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
import { fetchUsers } from "@/data/fetchUsers";
import { FC, useState } from "react";
import { useTranslation } from "react-i18next";
import { useQuery } from "@tanstack/react-query";
import { useResetOtherUserPassword } from "@/data/useResetOtherUserPassword";
import { Toaster } from "@/components/ui/toaster";
import { useToast } from "@/components/ui/use-toast";
import { NavLink } from "react-router-dom";
import {useResetOtherUserEmailAddress} from "@/data/useUpdateEmailAddress.ts";

const UserListPage: FC = () => {
  const { toast } = useToast();
  const { t } = useTranslation();
  const { data } = useQuery({ queryKey: ["users"], queryFn: fetchUsers });
  const { resetPassword } = useResetOtherUserPassword();
  const {updateEmail} = useResetOtherUserEmailAddress()
  const [openPaswordResetDialog, setOpenPasswordResetDialog] =
    useState<boolean>(false);

  const [userLogin, setUserLogin] = useState<string>();

  const handlePasswordResetClick = (login: string) => {
    setUserLogin(login);
    setOpenPasswordResetDialog(true);
  };

  const handleEmailUpdateClick = async (id: string) => {
    await updateEmail(id)
  };


  const handlePasswordReset = async () => {
    const result = await resetPassword(userLogin!);

    if (result === 204) {
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
      <div className="text-center m-10">!!! THERE SHOULD BE FILTER !!!</div>
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
                  <span className="font-bold">{userLogin}</span>?
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
              {data &&
                data.map((user) => (
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
                            onClick={() => handlePasswordResetClick(user.login)}
                          >
                            {t("userListPage.resetUserPasswordAction")}
                          </DropdownMenuItem>
                          <DropdownMenuItem
                              onClick={() => handleEmailUpdateClick(user.id)}
                          >
                            {t("userListPage.resetUserEmailAction")}
                          </DropdownMenuItem>
                          <DropdownMenuItem>test</DropdownMenuItem>
                          <DropdownMenuItem>
                            <NavLink to={`/admin/users/user/${user.id}`}>
                              {t("userListPage.viewDetails")}
                            </NavLink>
                          </DropdownMenuItem>
                          <DropdownMenuSeparator />
                          <DropdownMenuItem>test</DropdownMenuItem>
                        </DropdownMenuContent>
                      </DropdownMenu>
                    </TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </div>
      </div>
      <Toaster />
    </>
  );
};

export default UserListPage;
