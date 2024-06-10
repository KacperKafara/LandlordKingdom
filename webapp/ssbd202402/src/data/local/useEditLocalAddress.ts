import { Address } from "@/types/Address";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { AxiosError } from "axios";
import { toast } from "@/components/ui/use-toast";
import { t } from "i18next";
import { ErrorCode } from "@/@types/errorCode";

interface EditLocalAddress {
  id: string;
  address: Address;
}

export const useEditLocalAddress = () => {
  const { api } = useAxiosPrivate();
  const queryClient = useQueryClient();

  const { mutateAsync } = useMutation({
    mutationFn: async (data: EditLocalAddress) => {
      await api.patch(`/locals/${data.id}/address`, data.address);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["localDetailsForAdmin"] });
      toast({
        variant: "success",
        title: t("success"),
        description: t("changeAddressForm.addressUpdateSuccess"),
      });
    },
    onError: (error: AxiosError) => {
      queryClient.invalidateQueries({ queryKey: ["localDetailsForAdmin"] });
      toast({
        variant: "destructive",
        title: t("error.baseTitle"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });

  return { editLocalAddress: mutateAsync };
};
