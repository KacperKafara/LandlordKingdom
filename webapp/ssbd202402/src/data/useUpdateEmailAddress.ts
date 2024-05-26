import { useMutation } from "@tanstack/react-query";
import { useToast } from "@/components/ui/use-toast.ts";
import { useTranslation } from "react-i18next";
import useAxiosPrivate from "./useAxiosPrivate";
import { api } from "./api";
import { AxiosError } from "axios";

type ChangePasswordRequest = {
  token: string;
  password: string;
};

export const useResetOtherUserEmailAddress = () => {
  const { toast } = useToast();
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();

  const { mutateAsync } = useMutation({
    mutationFn: async (data: { id: string; email: string }) => {
      const response = await api.post(
        `/users/${data.id}/email-update-request`,
        { email: data.email }
      );
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
};

export const useResetMyEmailAddress = () => {
  const { toast } = useToast();
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();

  const { mutateAsync } = useMutation({
    mutationFn: async (data: UpdateEmailAddressRequest) => {
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

export const useChangeEmailAddress = () => {
  const { toast } = useToast();
  const { t } = useTranslation();

  const { mutateAsync, isPending } = useMutation({
    mutationFn: async (data: ChangePasswordRequest) => {
      const response = await api.post("/me/update-email", data);
      return response.status;
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: t("updateEmailPage.updateEmailSuccess"),
      });
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("updateEmailPage.updateEmailError"),
        description: error.message,
      });
    },
  });

  return { changeEmailAddress: mutateAsync, isPending };
};
