import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { useToast } from "@/components/ui/use-toast";

export const useTenantRole = () => {
  const { api } = useAxiosPrivate();
  const { toast } = useToast();
  const queryClient = useQueryClient();
  const { mutate: addTenantRole } = useMutation({
    mutationFn: async (id: string) => {
      await api.put(`/tenants/${id}/add-role`);
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: "Success",
      });
      queryClient.invalidateQueries({ queryKey: ["user"] });
    },
  });

  const { mutate: removeTenantRole } = useMutation({
    mutationFn: async (id: string) => {
      await api.put(`/tenants/${id}/remove-role`);
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: "Success",
      });
      queryClient.invalidateQueries({ queryKey: ["user"] });
    },
  });

  return { addTenantRole, removeTenantRole };
};
