import { useMutation } from "@tanstack/react-query";
import { useToast } from "@/components/ui/use-toast.ts";
import { useTranslation } from "react-i18next";
import useAxiosPrivate from "./useAxiosPrivate";

export const useResetOtherUserEmailAddress = () => {
  const { toast } = useToast();
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();

  const { mutateAsync } = useMutation({
    mutationFn: async (id: string) => {
      const response = await api.post(`/users/${id}/email-update-request`);
      return response.status;
    },
    onSettled: async (_, error) => {
      if (error) {
        toast({
          variant: "destructive",
          title: t("userListPage.resetUserEmailError"),
          description: error.message,
        });
      } else {
        toast({
          title: t("userListPage.resetUserEmailSuccess"),
        });
      }
    },
  });
  return { updateEmail: mutateAsync };
};

type UpdateEmailAddressRequest = {
  email: string;
}

export const useResetMyEmailAddress = () => {
  const { toast } = useToast();
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();

  const { mutateAsync } = useMutation({
    mutationFn: async (data : UpdateEmailAddressRequest) => {
      const response = await api.post(`/me/email-update-request`, data);
      return response.status;
    },
    onSettled: async (_, error) => {
      if (error) {
        toast({
          variant: "destructive",
          title: t("userListPage.resetUserEmailError"),
          description: error.message,
        });
      } else {
        toast({
          title: t("userListPage.resetUserEmailSuccess"),
        });
      }
    },
  });
  return { updateEmail: mutateAsync };
};
