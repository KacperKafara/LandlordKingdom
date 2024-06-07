import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { fetchRoleRequests } from "@/data/useRolesRequset";
import { t } from "i18next";
import { FC } from "react";

const RoleRequestsList: FC = () => {
  const { roleRequests } = fetchRoleRequests();

  return (
    <>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>{t("userListPage.login")}</TableHead>
            <TableHead>{t("userListPage.email")}</TableHead>
            <TableHead>{t("userListPage.firstName")}</TableHead>
            <TableHead>{t("userListPage.lastName")}</TableHead>
            <TableHead className="w-1"></TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {roleRequests && roleRequests.length ? (
            roleRequests.map((request) => (
              <TableRow key={request.id}>
                <TableCell>{request.login}</TableCell>
                <TableCell>{request.email}</TableCell>
                <TableCell>{request.firstName}</TableCell>
                <TableCell>{request.lastName}</TableCell>
              </TableRow>
            ))
          ) : (
            <TableRow>
              <TableCell colSpan={5} className="text-center text-xl">
                {t("notApprovedActionsPage.emptyRoleRequests")}
              </TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
    </>
  );
};

export default RoleRequestsList;
