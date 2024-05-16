import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { useToast } from "@/components/ui/use-toast";

export const useAdminRole = () => {
  const { api } = useAxiosPrivate();
  const { toast } = useToast();
  const queryClient = useQueryClient();
  const { mutate: addAdminRole } = useMutation({
    mutationFn: async (id: string) => {
      await api.put(`/admins/${id}/add-role`);
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: "Success",
      });
      queryClient.invalidateQueries({ queryKey: ["user"] });
    },
  });

  const { mutate: removeAdminRole } = useMutation({
    mutationFn: async (id: string) => {
      await api.put(`/admins/${id}/remove-role`);
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: "Success",
      });
      queryClient.invalidateQueries({ queryKey: ["user"] });
    },
  });

  return { addAdminRole, removeAdminRole };
};
