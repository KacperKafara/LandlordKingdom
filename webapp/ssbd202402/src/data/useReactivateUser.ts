import { useMutation } from "@tanstack/react-query";
import { api } from "./api";
import { useToast } from "@/components/ui/use-toast";
import {ErrorCode} from "@/@types/errorCode.ts";
import {t} from "i18next";
import {AxiosError} from "axios";

export const useReactivateUser = () => {
  const { toast } = useToast();
  const { mutateAsync } = useMutation({
    mutationFn: async (token: string) => {
      await api.post(`/auth/reactivate?token=${token}`);
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: "success",
        description: "reactivationSuccess",
      });
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("error.baseTitle"),
        description: t(
            `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });

  return { reactivate: mutateAsync };
};
