import { FC, useEffect, useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
} from "./ui/dialog";
import { useUserStore } from "@/store/userStore";
import { decodeJwt } from "@/utils/jwt";
import { useTranslation } from "react-i18next";
import { Button } from "./ui/button";
import { api } from "@/data/api";
import { useNavigate } from "react-router-dom";
import { toast } from "./ui/use-toast";

const SessionExpiredDialog: FC = () => {
  const { t } = useTranslation();
  const [isOpen, setOpen] = useState(false);
  const {
    token,
    refreshToken,
    clearRefreshToken,
    clearToken,
    setToken,
  } = useUserStore();
  const navigation = useNavigate();


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

  const handleSubmit = async () => {
    try {
    const response = await api.post("/auth/refresh", {
      refreshToken,
    });
    setToken(response.data.token);
    setOpen(false);
    } catch (error) { 
      clearRefreshToken();
      clearToken();
      navigation("/login");
      toast({
        title: t("sessionExpired"),
        description: t("sessionExpiredDescription"),
      });
    }
  }

  const handleLogout = () => {
    clearRefreshToken();
    clearToken();
    navigation("/login");
  };


  return (
    <Dialog  open={isOpen} onOpenChange={setOpen}>
      <DialogContent onInteractOutside={(e) => e.preventDefault()}>
        <DialogHeader>{t("sessionExpiredDialog.title")}</DialogHeader>
        <DialogDescription>
          {t("sessionExpiredDialog.description")}
        </DialogDescription>
        <DialogFooter>
          <Button onClick={() => handleLogout()}>{t("sessionExpiredDialog.signOut")}</Button>
          <Button onClick={() => handleSubmit()}>{t("confirm")}</Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default SessionExpiredDialog;
