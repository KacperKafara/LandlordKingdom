import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { useTranslation } from "react-i18next";
import { useToast } from "@/components/ui/use-toast";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";

type MutateEndDateRequest = {
  id: string;
  newDate: string;
};

export const useMutateEndDate = () => {
  const queryClient = useQueryClient();
  const { toast } = useToast();
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();

  return useMutation({
    mutationFn: async (data: MutateEndDateRequest) => {
      await api.patch(`/me/rents/${data.id}/end-date`, {
        newEndDate: data.newDate,
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["ownerCurrentRents"] });
      toast({
        title: t("changeFixedFee.successTitle"),
        description: t("changeFixedFee.successDescription"),
      });
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("changeFixedFee.errorTitle"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });
};
