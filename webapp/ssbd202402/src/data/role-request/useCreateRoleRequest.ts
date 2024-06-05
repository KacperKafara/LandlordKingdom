import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";

export const useCreateRoleRequest = () => {
  const { api } = useAxiosPrivate();
  const queryClient = useQueryClient();
  const { mutateAsync } = useMutation({
    mutationFn: async () => {
      const result = await api.post("/me/role-request");
      return result.data;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["role-request"] });
    },
  });

  return { createRoleRequest: mutateAsync };
};
