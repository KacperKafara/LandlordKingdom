import { useMutation } from "@tanstack/react-query";
import { useToast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import { AxiosError, AxiosInstance } from "axios";
import useAxiosPrivate from "./useAxiosPrivate";

type ResetPasswordRequest = {
  email: string;
};

const sendRequest = async (
  request: ResetPasswordRequest,
  api: AxiosInstance
) => {
  await api.post(`/users/reset-password`, request);
};

export const useResetPassword = () => {
  const { t } = useTranslation();
  const { toast } = useToast();
  const { api } = useAxiosPrivate();

  const { mutate, isSuccess } = useMutation({
    mutationFn: (request: ResetPasswordRequest) => sendRequest(request, api),
    onSuccess: () => {
      toast({
        title: t("userListPage.resetUserPasswordToastTitleSuccess"),
        description: t("userListPage.resetUserPasswordToastDescriptionSuccess"),
      });
    },
    onError: (error: AxiosError) => {
      if (error.response?.status === 404) {
        toast({
          variant: "destructive",
          title: t("userListPage.resetUserPasswordToastTitleFail"),
          description: t(
            "userListPage.resetUserPasswordToastDescriptionNotFound"
          ),
        });
      } else if (error.response?.status === 403) {
        toast({
          variant: "destructive",
          title: t("userListPage.resetUserPasswordToastTitleFail"),
          description: t(
            "userListPage.resetUserPasswordToastDescriptionForbidden"
          ),
        });
      } else {
        toast({
          variant: "destructive",
          title: t("userListPage.resetUserPasswordToastTitleFail"),
          description: t("userListPage.resetUserPasswordToastDescriptionFail"),
        });
      }
    },
  });

  return { mutate, isSuccess };
};
