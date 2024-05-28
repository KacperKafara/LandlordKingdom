import { useMutation } from "@tanstack/react-query";
import { useToast } from "@/components/ui/use-toast";
import { useParams } from "react-router-dom";
import useAxiosPrivate from "./useAxiosPrivate";
import { t } from "i18next";
import { ErrorCode } from "@/@types/errorCode";
import {AxiosError} from "axios";

export const useVerifyAccount = () => {
  const { token } = useParams<{ token: string }>();
  const { toast } = useToast();
  const { api } = useAxiosPrivate();

  const { mutate, isSuccess } = useMutation({
    mutationKey: ["verify"],
    mutationFn: async () => {
      await api.post("/me/verify", { token });
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
    onSuccess: () => {
      toast({
        title: t("success"),
      });
    },
  });

  return { verifyAccount: mutate, isSuccess };
};
