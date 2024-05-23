import { useMutation } from "@tanstack/react-query";
import useAxiosPrivate from "./useAxiosPrivate";
import { useTranslation } from "react-i18next";
import { useToast } from "@/components/ui/use-toast";
import { AxiosError } from "axios";
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
      });
    },
    onError: (error: AxiosError) => {
      if (error.response?.status === 404) {
        toast({
          variant: "destructive",
          title: t("changePasswordForm.errorTitle"),
          description: t("changePasswordForm.errorDescriptionNotFound"),
        });
      } else if (error.response?.status === 400) {
        toast({
          variant: "destructive",
          title: t("changePasswordForm.errorTitle"),
          description: t("changePasswordForm.errorDescriptionBadRequest"),
        });
      } else {
        toast({
          variant: "destructive",
          title: t("changePasswordForm.errorTitle"),
        });
      }
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
        title: t("resetPasswordPage.changePasswordToastTitleSuccess"),
        description: t(
          "resetPasswordPage.changePasswordToastDescriptionSuccess"
        ),
      });
    },
    onError: (error: AxiosError) => {
      if (error.response?.status === 404) {
        toast({
          variant: "destructive",
          title: t("resetPasswordPage.changePasswordToastTitleFail"),
          description: t(
            "resetPasswordPage.changePasswordToastDescriptionTokenNotValid"
          ),
        });
      } else if (error.response?.status === 403) {
        toast({
          variant: "destructive",
          title: t("resetPasswordPage.changePasswordToastTitleFail"),
          description: t(
            "resetPasswordPage.changePasswordToastDescriptionForbidden"
          ),
        });
      } else {
        toast({
          variant: "destructive",
          title: t("resetPasswordPage.changePasswordToastTitleFail"),
          description: t(
            "resetPasswordPage.changePasswordToastDescriptionFail"
          ),
        });
      }
    },
  });

  return { mutateAsync, isSuccess, isPending };
};
