import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { useToast } from "@/components/ui/use-toast";
import {AxiosError} from "axios";
import {useTranslation} from "react-i18next";
import {ErrorCode} from "@/@types/errorCode.ts";

export const useAdminRole = () => {
  const { api } = useAxiosPrivate();
  const { toast } = useToast();
  const queryClient = useQueryClient();
  const {t} = useTranslation();
  const { mutate: addAdminRole } = useMutation({
    mutationFn: async (id: string) => {
      await api.put(`/admins/${id}/add-role`);
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: t("userDetailsPage.role.added.administrator"),
      });
      queryClient.invalidateQueries({ queryKey: ["user"] });
    },
    onError: (error: AxiosError) => {
      toast(
          {
            variant: "destructive",
            title: t("error.baseTitle"),
            description: t(`errors.${(error.response?.data as ErrorCode).exceptionCode}`),
          }
      )
    },
  });

  const { mutate: removeAdminRole } = useMutation({
    mutationFn: async (id: string) => {
      await api.put(`/admins/${id}/remove-role`);
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: t("userDetailsPage.role.removed.administrator"),
      });
      queryClient.invalidateQueries({ queryKey: ["user"] });
    },
    onError: (error: AxiosError) => {
      toast(
          {
            variant: "destructive",
            title: t("error.baseTitle"),
            description: t(`errors.${(error.response?.data as ErrorCode).exceptionCode}`),
          }
      )
    },
  });

  return { addAdminRole, removeAdminRole };
};
