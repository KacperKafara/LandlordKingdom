import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { useToast } from "@/components/ui/use-toast";
import {AxiosError} from "axios";
import {ErrorCode} from "@/@types/errorCode.ts";
import {useTranslation} from "react-i18next";

export const useOwnerRole = () => {
  const { api } = useAxiosPrivate();
  const { toast } = useToast();
  const queryClient = useQueryClient();
  const {t} = useTranslation();
  const { mutate: addOwnerRole } = useMutation({
    mutationFn: async (id: string) => {
      await api.put(`/owners/${id}/add-role`);
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: t("userDetailsPage.role.added.owner"),
      });
      queryClient.invalidateQueries({ queryKey: ["user"] });
    },
    onError: (error: AxiosError<ErrorCode>) => {
      toast(
          {
            variant: "destructive",
            title: t("error.baseTitle"),
            description: t(`errors.${(error.response!.data).exceptionCode}`),
          }
      )
    },
  });

  const { mutate: removeOwnerRole } = useMutation({
    mutationFn: async (id: string) => {
      await api.put(`/owners/${id}/remove-role`);
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: t("userDetailsPage.role.removed.owner"),
      });
      queryClient.invalidateQueries({ queryKey: ["user"] });
    },
    onError: (error: AxiosError<ErrorCode>) => {
      toast(
          {
            variant: "destructive",
            title: t("error.baseTitle"),
            description: t(`errors.${(error.response!.data).exceptionCode}`),
          }
      )
    },
  });

  return { addOwnerRole, removeOwnerRole };
};
