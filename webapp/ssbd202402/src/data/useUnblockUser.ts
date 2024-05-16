import { useMutation, useQueryClient } from "@tanstack/react-query";
import { api } from "./api";
import { useToast } from "@/components/ui/use-toast.ts";
import { useTranslation } from "react-i18next";
import { AxiosError } from "axios";

export const useUnblockUser = () => {
  const { toast } = useToast();
  const queryClient = useQueryClient();
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
    onError: (error: AxiosError) => {
      if (error.response?.status === 404) {
        toast({
          variant: "destructive",
          title: t("block.toast.title.fail"),
          description: t("block.toast.description.notFound"),
        });
      } else if (error.response?.status === 409) {
        toast({
          variant: "destructive",
          title: t("block.toast.title.fail"),
          description: t("block.toast.description.alreadyUnblocked"),
        });
      } else {
        toast({
          variant: "destructive",
          title: t("block.toast.title.fail"),
          description: t("block.toast.description.fail"),
        });
      }
    },
  });

  return { unblockUser: mutateAsync };
};
