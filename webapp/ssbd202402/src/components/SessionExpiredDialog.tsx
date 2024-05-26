import { FC, useEffect, useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
} from "./ui/dialog";
import { useUserStore } from "@/store/userStore";
import { decodeJwt } from "@/utils/jwt";

const SessionExpiredDialog: FC = () => {
  const [isOpen, setOpen] = useState(false);
  const { refreshToken } = useUserStore();

  useEffect(() => {
    const interval = setInterval(() => {
      const payload = decodeJwt(refreshToken ?? "");
      if (Date.now() + 1000 * 60 * 5 > payload.exp * 1000) {
        setOpen(true);
      }
    }, 1000 * 60);

    return () => {
      clearInterval(interval);
    };
  }, [refreshToken]);

  return (
    <Dialog open={isOpen} onOpenChange={setOpen}>
      <DialogContent>
        <DialogHeader>Session will expire soon</DialogHeader>
        <DialogDescription>
          Your session will expire in 5 minutes. Please save your work and
          refresh the page to continue.
        </DialogDescription>
      </DialogContent>
    </Dialog>
  );
};

export default SessionExpiredDialog;
