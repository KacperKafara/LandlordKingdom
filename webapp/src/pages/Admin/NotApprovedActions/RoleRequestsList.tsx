import { LoadingData } from "@/components/LoadingData";
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
import { PageChangerComponent } from "@/pages/Components/PageChangerComponent";
import { t } from "i18next";
import { FC, useState } from "react";
import { useNavigate } from "react-router-dom";

const RoleRequestsList: FC = () => {
  const [pageNumber, setPageNumber] = useState(0);
  const [pageSize, setPageSize] = useState(8);
  const { roleRequestsPage, isLoading } = useGetRoleRequests({
    pageNumber,
    pageSize,
  });
  const { acceptRoleRequest } = useAcceptRoleRequest();
  const { rejectRoleRequest } = useRejectRoleRequest();
  const navigate = useNavigate();

  const approveRole = async (id: string) => {
    await acceptRoleRequest(id);
  };

  const rejectRole = async (id: string) => {
    await rejectRoleRequest(id);
  };

  if (!roleRequestsPage || isLoading) {
    return <LoadingData />;
  }

  const roleRequests = roleRequestsPage?.requests;

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
                <TableCell className="flex justify-end gap-2">
                  <Button
                    onClick={() => navigate(`/admin/users/${request.userId}`)}
                    variant="secondary"
                  >
                    {t("notApprovedActionsPage.show")}
                  </Button>

                  <Button
                    onClick={() => approveRole(request.id)}
                    variant="default"
                  >
                    {t("notApprovedActionsPage.approve")}
                  </Button>

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
      <PageChangerComponent
        totalPages={roleRequestsPage.totalPages}
        pageNumber={pageNumber}
        pageSize={pageSize}
        setPageNumber={setPageNumber}
        setNumberOfElements={setPageSize}
        className="m-3 flex justify-between"
      />
    </>
  );
};

export default RoleRequestsList;
