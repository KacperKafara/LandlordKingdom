import { useMutation } from "@tanstack/react-query";
import { useToast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import { AxiosError } from "axios";
import useAxiosPrivate from "./useAxiosPrivate";

type ResetPasswordRequest = {
  email: string;
};

export const useResetPassword = () => {
  const { t } = useTranslation();
  const { toast } = useToast();
  const { api } = useAxiosPrivate();

  const { mutateAsync, isSuccess, isPending } = useMutation({
    mutationFn: async (request: ResetPasswordRequest) => {
      await api.post(`/users/reset-password`, request);
    },
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

  return { mutateAsync, isSuccess, isPending };
};
