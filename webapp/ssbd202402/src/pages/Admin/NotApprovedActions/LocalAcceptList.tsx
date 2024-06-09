import ConfirmDialog from "@/components/ConfirmDialog";
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
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { NavLink } from "react-router-dom";

const LocalAcceptList: FC = () => {
  const { t } = useTranslation();
  const { unapprovedLocals } = useUnapprovedLocals();
  const { approveLocal } = useApproveLocal();
  const { rejectLocal } = useRejectLocal();

  return (
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
                <div className="flex gap-1">
                  <ConfirmDialog
                    buttonText={t("notApprovedActionsPage.confirm")}
                    confirmAction={() => approveLocal(local.id)}
                    dialogTitle={t(
                      "notApprovedActionsPage.confirmDialog.title"
                    )}
                    dialogDescription={t(
                      "notApprovedActionsPage.confirmDialog.confirmDescription"
                    )}
                  />
                  <ConfirmDialog
                    buttonText={t("notApprovedActionsPage.reject")}
                    confirmAction={() => rejectLocal(local.id)}
                    dialogTitle={t(
                      "notApprovedActionsPage.confirmDialog.title"
                    )}
                    dialogDescription={t(
                      "notApprovedActionsPage.confirmDialog.rejectDescription"
                    )}
                  />
                  <Button asChild>
                    <NavLink to={`/admin/locals/${local.id}`}>
                      {t("notApprovedActionsPage.unapprovedLocals.details")}
                    </NavLink>
                  </Button>
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
  );
};

export default LocalAcceptList;
