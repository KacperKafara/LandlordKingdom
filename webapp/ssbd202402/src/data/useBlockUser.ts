import { useMutation, useQueryClient } from "@tanstack/react-query";
import { api } from "./api";
import { useToast } from "@/components/ui/use-toast.ts";
import { useTranslation } from "react-i18next";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";

export const useBlockUser = () => {
  const queryClient = useQueryClient();
  const { toast } = useToast();
  const { t } = useTranslation();

  const { mutateAsync } = useMutation({
    mutationFn: async (userId: string) => {
      await api.post(`/users/${userId}/block`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["filteredUsers"] });
      queryClient.invalidateQueries({ queryKey: ["users"] });
      toast({
        title: t("block.toast.title.success"),
        description: t("block.toast.description.blockSuccess"),
      });
    },
    onError: (error) => {
      toast({
        variant: "destructive",
        title: t("block.toast.title.fail"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });

  return { blockUser: mutateAsync };
};

export const useUnblockUser = () => {
  const queryClient = useQueryClient();
  const { toast } = useToast();
  const { t } = useTranslation();

  const { mutateAsync } = useMutation({
    mutationFn: async (userId: string) => {
      await api.post(`/users/${userId}/unblock`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["filteredUsers"] });
      queryClient.invalidateQueries({ queryKey: ["users"] });
      toast({
        title: t("block.toast.title.success"),
        description: t("block.toast.description.unblockSuccess"),
      });
    },
    onError: (error) => {
      toast({
        variant: "destructive",
        title: t("block.toast.title.fail"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });

  return { unblockUser: mutateAsync };
};
