import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { useTranslation } from "react-i18next";
import { useToast } from "@/components/ui/use-toast";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";

type MutateOwnLocalFixedFeeRequest = {
  id: string;
  rentalFee: number;
  marginFee: number;
};

export const useMutateOwnLocalFixedFee = () => {
  const queryClient = useQueryClient();
  const { toast } = useToast();
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();

  return useMutation({
    mutationFn: async (data: MutateOwnLocalFixedFeeRequest) => {
      await api.patch(`/me/locals/${data.id}/fixed-fee`, {
        rentalFee: data.rentalFee,
        marginFee: data.marginFee,
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["ownLocalDetails"] });
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
