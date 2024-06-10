import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { useCreateRoleRequest } from "@/data/role-request/useCreateRoleRequest";
import { useGetRoleRequest } from "@/data/role-request/useGetRoleRequest";
import { useDialogStore } from "@/store/dialogStore";
import React, { FC } from "react";
import { useTranslation } from "react-i18next";

const RoleRequestDialogComponent: FC = () => {
  const { t } = useTranslation();
  const { isOpened, closeDialog } = useDialogStore();
  const { roleRequest, isError } = useGetRoleRequest();
  const { createRoleRequest } = useCreateRoleRequest();
  return (
    <Dialog open={isOpened("roleRequest")} onOpenChange={closeDialog}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>{t("roleRequestDialog.requestOwnerRole")}</DialogTitle>
        </DialogHeader>
        <div className="flex flex-col gap-3">
          {roleRequest && (
            <div>
              {t("roleRequestDialog.alreadyPlacedRequest", {
                when: roleRequest?.createdAt.toString() ?? "",
              })}
            </div>
          )}
          <h2 className="font-bold">{t("roleRequestDialog.howDoesItWork")}</h2>
          <p>{t("roleRequestDialog.description")}</p>

          <Button
            disabled={!isError}
            onClick={async () => {
              await createRoleRequest();
            }}
          >
            {t("roleRequestDialog.requestRoleButton")}
          </Button>
          <span className="text-center text-sm">
            {t("roleRequestDialog.requestOwnerRoleDescription")}
          </span>
        </div>
      </DialogContent>
    </Dialog>
  );
};

const RoleRequestDialog = React.memo(RoleRequestDialogComponent);

export default RoleRequestDialog;
