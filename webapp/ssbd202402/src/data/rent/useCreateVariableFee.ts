import { useToast } from "@/components/ui/use-toast";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";

type CreateVariableFeeRequest = {
  rentId: string;
  amount: number;
};

export const useCreateVariableFee = () => {
  const { api } = useAxiosPrivate();
  const queryClient = useQueryClient();
  const { toast } = useToast();
  const { mutateAsync } = useMutation({
    mutationFn: async ({ rentId, amount }: CreateVariableFeeRequest) => {
      await api.post(`/me/rents/${rentId}/variable-fee`, { amount });
    },
    onSuccess: async () => {
      toast({
        title: "Variable fee created",
        variant: "success",
      });
      return await queryClient.invalidateQueries({ queryKey: ["tenantRent"] });
    },
  });

  return { createVariableFee: mutateAsync };
};
