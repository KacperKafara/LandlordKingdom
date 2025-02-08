import { useMutation } from "@tanstack/react-query";
import useAxiosPrivate from "./useAxiosPrivate";
import { useTranslation } from "react-i18next";
import { useToast } from "@/components/ui/use-toast";
import { ErrorCode } from "@/@types/errorCode";
import {AxiosError} from "axios";
type ChangePasswordType = {
  oldPassword: string;
  newPassword: string;
};

type ChangePasswordWithTokenType = {
  password: string;
  token: string;
};

export const useChangeUserPassword = () => {
  const { api } = useAxiosPrivate();
  const { toast } = useToast();
  const { t } = useTranslation();

  const { mutateAsync } = useMutation({
    mutationFn: async (data: ChangePasswordType) => {
      const response = await api.post("/me/change-password", data);
      return response.status;
    },
    onSuccess: () => {
      toast({
        title: t("changePasswordForm.success"),
        description: t("changePasswordForm.successDescription"),
      });
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("error.baseTitle"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });

  return { changePassword: mutateAsync };
};

export const useChangeUserPasswordWithToken = () => {
  const { api } = useAxiosPrivate();
  const { t } = useTranslation();
  const { toast } = useToast();

  const { mutateAsync, isSuccess, isPending } = useMutation({
    mutationFn: async (data: ChangePasswordWithTokenType) => {
      await api.post("/me/change-password-with-token", data);
    },
    onSuccess: () => {
      toast({
        title: t("changePasswordForm.success"),
        description: t("changePasswordForm.successDescription"),
      });
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("error.baseTitle"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });

  return { mutateAsync, isSuccess, isPending };
};
