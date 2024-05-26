import { FC, useEffect, useState } from "react";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
} from "./ui/dialog";
import { useUserStore } from "@/store/userStore";
import { decodeJwt } from "@/utils/jwt";
import { useTranslation } from "react-i18next";

const SessionExpiredDialog: FC = () => {
  const { t } = useTranslation();
  const [isOpen, setOpen] = useState(false);
  const { token } = useUserStore();

  useEffect(() => {
    const interval = setInterval(() => {
      const payload = decodeJwt(token ?? "");
      if (Date.now() + 1000 * 60 * 5 > payload.exp * 1000) {
        setOpen(true);
      }
    }, 1000 * 60);

    return () => {
      clearInterval(interval);
    };
  }, [token]);

  return (
    <Dialog open={isOpen} onOpenChange={setOpen}>
      <DialogContent>
        <DialogHeader>{t("sessionExpiredDialog.title")}</DialogHeader>
        <DialogDescription>
          {t("sessionExpiredDialog.description")}
        </DialogDescription>
        <DialogFooter>
          <DialogClose>{t("cancel")}</DialogClose>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default SessionExpiredDialog;
