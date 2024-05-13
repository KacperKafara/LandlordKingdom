import { useMutation } from "@tanstack/react-query";
import { api } from "./api";
import { AxiosError } from "axios";
import { useTranslation } from "react-i18next";
import { useToast } from "@/components/ui/use-toast";

type ChangePasswordType = {
  password: string;
  token: string;
};

const sendRequest = async (data: ChangePasswordType) => {
  const response = await api.post("/me/change-password-with-token", data);
  return response.status;
};

export const useChangeUserPasswordWithToken = () => {
  const { t } = useTranslation();
  const { toast } = useToast();

  const { mutate, isSuccess } = useMutation({
    mutationFn: sendRequest,
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
