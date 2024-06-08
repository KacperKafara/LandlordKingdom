import { useToast } from "@/components/ui/use-toast.ts";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";

export const useCreateRoleRequest = () => {
  const { api } = useAxiosPrivate();
  const queryClient = useQueryClient();
  const { toast } = useToast();
  const { mutateAsync } = useMutation({
    mutationFn: async () => {
      const result = await api.post("/me/role-request");
      return result.data;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["role-request"] });
      toast({
        variant: "success",
        title: "Role request created",
        description: "We will review your request and get back to you soon",
      });
    },
  });

  return { createRoleRequest: mutateAsync };
};
