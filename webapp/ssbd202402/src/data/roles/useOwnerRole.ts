import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { useToast } from "@/components/ui/use-toast";

export const useOwnerRole = () => {
  const { api } = useAxiosPrivate();
  const { toast } = useToast();
  const queryClient = useQueryClient();
  const { mutate: addOwnerRole } = useMutation({
    mutationFn: async (id: string) => {
      await api.put(`/owners/${id}/add-role`);
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: "Success",
      });
      queryClient.invalidateQueries({ queryKey: ["user"] });
    },
  });

  const { mutate: removeOwnerRole } = useMutation({
    mutationFn: async (id: string) => {
      await api.put(`/owners/${id}/remove-role`);
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: "Success",
      });
      queryClient.invalidateQueries({ queryKey: ["user"] });
    },
  });

  return { addOwnerRole, removeOwnerRole };
};
