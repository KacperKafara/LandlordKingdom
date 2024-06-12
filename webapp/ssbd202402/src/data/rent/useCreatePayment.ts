import { useToast } from "@/components/ui/use-toast";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";
import { useTranslation } from "react-i18next";

type CreatePaymentRequest = {
  rentId: string;
  amount: number;
};

export const useCreatePayment = () => {
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();
  const queryClient = useQueryClient();
  const { toast } = useToast();
  const { mutateAsync } = useMutation({
    mutationFn: async ({ rentId, amount }: CreatePaymentRequest) => {
      await api.post(`/me/rents/${rentId}/payment`, { amount });
    },
    onSuccess: async () => {
      toast({
        title: t("createPaymentDialog.success"),
        variant: "success",
      });
      return await queryClient.invalidateQueries({
        queryKey: ["rentDetailsForOwner", "rentDetailsForOwner"],
      });
    },
    onError: (error: AxiosError<ErrorCode>) => {
      const { data } = error.response!;
      toast({
        title: t(`errors.${data.exceptionCode}`),
        variant: "destructive",
      });
    },
  });

  return { createPayment: mutateAsync };
};
