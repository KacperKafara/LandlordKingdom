import { useMutation } from "@tanstack/react-query";
import { api } from "./api";
import { useToast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import { AxiosError } from "axios";

const sendRequest = async (email: String) => {
  await api.post(`/users/reset-password?email=${email}`);
};

export const useResetPassword = () => {
  const { t } = useTranslation();
  const { toast } = useToast();

  const { mutate, isSuccess } = useMutation({
    mutationFn: sendRequest,
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
