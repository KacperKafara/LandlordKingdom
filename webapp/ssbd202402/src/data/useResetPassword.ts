import { useMutation } from "@tanstack/react-query";
import { useToast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import useAxiosPrivate from "./useAxiosPrivate";
import { ErrorCode } from "@/@types/errorCode";

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
    onError: (error) => {
      toast({
        variant: "destructive",
        title: t("userListPage.resetUserPasswordToastTitleFail"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });

  return { mutateAsync, isSuccess, isPending };
};
