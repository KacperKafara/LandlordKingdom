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
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { useQuery } from "react-query";
import { NavLink } from "react-router-dom";

const UserListPage: FC = () => {
  const { t } = useTranslation();
  const { data } = useQuery({ queryKey: ["users"], queryFn: fetchUsers });

  return (
    <>
      <div className="text-center m-10">!!! THERE SHOULD BE FILTER !!!</div>
      <div className="flex justify-center">
        <div className="w-3/5">
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
                            <DropdownMenuItem> 
                              <NavLink to={`/admin/users/user/${user.id}`}>
                                {t("userListPage.viewDetails")}
                              </NavLink>
                            </DropdownMenuItem>
                          <DropdownMenuSeparator />
                          <DropdownMenuItem>test</DropdownMenuItem>
                          <DropdownMenuItem>test</DropdownMenuItem>
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
    </>
  );
};

export default UserListPage;
