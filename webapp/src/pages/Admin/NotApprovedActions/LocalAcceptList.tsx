import ConfirmDialog from "@/components/ConfirmDialog";
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
import { useApproveLocal } from "@/data/local/useApproveLocal";
import { useRejectLocal } from "@/data/local/useRejectLocal";
import { useUnapprovedLocals } from "@/data/local/useUnapprovedLocals";
import { PageChangerComponent } from "@/pages/Components/PageChangerComponent";
import { FC, useState } from "react";
import { useTranslation } from "react-i18next";
import { NavLink } from "react-router-dom";

const LocalAcceptList: FC = () => {
  const [pageNumber, setPageNumber] = useState(0);
  const [pageSize, setPageSize] = useState(8);
  const { t } = useTranslation();
  const { unapprovedLocalsPage, isLoading } = useUnapprovedLocals({
    pageNumber,
    pageSize,
  });
  const { approveLocal } = useApproveLocal();
  const { rejectLocal } = useRejectLocal();

  if (!unapprovedLocalsPage || isLoading) {
    return <LoadingData />;
  }

  const unapprovedLocals = unapprovedLocalsPage.locals;

  return (
    <>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>
              {t("notApprovedActionsPage.unapprovedLocals.name")}
            </TableHead>
            <TableHead>
              {t("notApprovedActionsPage.unapprovedLocals.address")}
            </TableHead>
            <TableHead>
              {t("notApprovedActionsPage.unapprovedLocals.owner")}
            </TableHead>
            <TableHead className="w-1"></TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {unapprovedLocals && unapprovedLocals.length ? (
            unapprovedLocals.map((local) => (
              <TableRow key={local.id}>
                <TableCell>{local.name}</TableCell>
                <TableCell>
                  {local.address.street} {local.address.number},{" "}
                  {local.address.zipCode} {local.address.city}
                </TableCell>
                <TableCell>{local.ownerLogin}</TableCell>
                <TableCell>
                  <div className="flex gap-2">
                    <Button variant="secondary" asChild>
                      <NavLink to={`/admin/locals/local/${local.id}`}>
                        {t("notApprovedActionsPage.show")}
                      </NavLink>
                    </Button>
                    <ConfirmDialog
                      buttonText={t("notApprovedActionsPage.approve")}
                      confirmAction={() => approveLocal(local.id)}
                      dialogTitle={t(
                        "notApprovedActionsPage.confirmDialog.title"
                      )}
                      dialogDescription={t(
                        "notApprovedActionsPage.confirmDialog.confirmDescription"
                      )}
                    />
                    <ConfirmDialog
                      variant="destructive"
                      buttonText={t("notApprovedActionsPage.reject")}
                      confirmAction={() => rejectLocal(local.id)}
                      dialogTitle={t(
                        "notApprovedActionsPage.confirmDialog.title"
                      )}
                      dialogDescription={t(
                        "notApprovedActionsPage.confirmDialog.rejectDescription"
                      )}
                    />
                  </div>
                </TableCell>
              </TableRow>
            ))
          ) : (
            <TableRow>
              <TableCell colSpan={5} className="text-center text-xl">
                {t("notApprovedActionsPage.emptyUnapprovedLocals")}
              </TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
      <PageChangerComponent
        totalPages={unapprovedLocalsPage.totalPages}
        pageNumber={pageNumber}
        pageSize={pageSize}
        setPageNumber={setPageNumber}
        setNumberOfElements={setPageSize}
        className="m-3 flex justify-between"
      />
    </>
  );
};

export default LocalAcceptList;
