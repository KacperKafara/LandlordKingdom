import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { useToast } from "@/components/ui/use-toast";
import {AxiosError} from "axios";
import {ErrorCode} from "@/@types/errorCode.ts";
import {useTranslation} from "react-i18next";

export const useTenantRole = () => {
  const { api } = useAxiosPrivate();
  const { toast } = useToast();
  const {t} = useTranslation();
  const queryClient = useQueryClient();
  const { mutate: addTenantRole } = useMutation({
    mutationFn: async (id: string) => {
      await api.put(`/tenants/${id}/add-role`);
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: "Success",
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

  const { mutate: removeTenantRole } = useMutation({
    mutationFn: async (id: string) => {
      await api.put(`/tenants/${id}/remove-role`);
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: "Success",
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

  return { addTenantRole, removeTenantRole };
};
