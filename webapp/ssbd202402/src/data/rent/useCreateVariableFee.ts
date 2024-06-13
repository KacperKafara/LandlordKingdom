import { useToast } from "@/components/ui/use-toast";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";
import { useTranslation } from "react-i18next";

type CreateVariableFeeRequest = {
  rentId: string;
  amount: number;
};

export const useCreateVariableFee = () => {
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();
  const queryClient = useQueryClient();
  const { toast } = useToast();
  const { mutateAsync } = useMutation({
    mutationFn: async ({ rentId, amount }: CreateVariableFeeRequest) => {
      await api.post(`/me/rents/${rentId}/variable-fee`, { amount });
    },
    onSuccess: async () => {
      toast({
        title: t("createVariableFeeDialog.success"),
        variant: "success",
      });
      await queryClient.invalidateQueries({
        queryKey: ["tenantRent"],
      });
      await queryClient.invalidateQueries({ queryKey: ["rentVariableFees"] });
    },
    onError: (error: AxiosError<ErrorCode>) => {
      const { data } = error.response!;
      toast({
        title: t(`errors.${data.exceptionCode}`),
        variant: "destructive",
      });
    },
  });

  return { createVariableFee: mutateAsync };
};
