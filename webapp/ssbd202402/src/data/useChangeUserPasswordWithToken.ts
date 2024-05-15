import { useMutation } from "@tanstack/react-query";
import { AxiosError, AxiosInstance } from "axios";
import { useTranslation } from "react-i18next";
import { useToast } from "@/components/ui/use-toast";
import useAxiosPrivate from "./useAxiosPrivate";

type ChangePasswordType = {
  password: string;
  token: string;
};

const sendRequest = async (data: ChangePasswordType, api: AxiosInstance) => {
  const response = await api.post("/me/change-password-with-token", data);
  return response.status;
};

export const useChangeUserPasswordWithToken = () => {
  const { t } = useTranslation();
  const { toast } = useToast();
  const { api } = useAxiosPrivate();

  const { mutate, isSuccess } = useMutation({
    mutationFn: (data: ChangePasswordType) => sendRequest(data, api),
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

  return { mutate, isSuccess };
};
