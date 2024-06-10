import { Button } from "@/components/ui/button";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  useAcceptRoleRequest,
  useGetRoleRequests,
  useRejectRoleRequest,
} from "@/data/useRolesRequset";
import { t } from "i18next";
import { FC } from "react";
import { useNavigate } from "react-router-dom";

const RoleRequestsList: FC = () => {
  const { roleRequests } = useGetRoleRequests();
  const { acceptRoleRequest } = useAcceptRoleRequest();
  const { rejectRoleRequest } = useRejectRoleRequest();
  const navigate = useNavigate();

  const approveRole = async (id: string) => {
    await acceptRoleRequest(id);
  };

  const rejectRole = async (id: string) => {
    await rejectRoleRequest(id);
  };

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
            <TableHead className="w-1"></TableHead>
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
                <TableCell>
                  <Button
                    onClick={() => navigate(`/admin/users/${request.userId}`)}
                    variant="secondary"
                  >
                    {t("notApprovedActionsPage.show")}
                  </Button>
                </TableCell>
                <TableCell>
                  <Button
                    onClick={() => approveRole(request.id)}
                    variant="default"
                  >
                    {t("notApprovedActionsPage.approve")}
                  </Button>
                </TableCell>
                <TableCell>
                  <Button
                    onClick={() => rejectRole(request.id)}
                    variant="destructive"
                  >
                    {t("notApprovedActionsPage.reject")}
                  </Button>
                </TableCell>
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
