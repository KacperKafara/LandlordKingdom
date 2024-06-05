import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";

type RoleRequest = {
  id: string;
  createdAt: Date;
};

export const useGetRoleRequest = () => {
  const { api } = useAxiosPrivate();
  const { data, isError } = useQuery({
    queryKey: ["role-request"],
    queryFn: async () => {
      const result = await api.get<RoleRequest>("/me/role-request");
      return result.data;
    },
    retry: 0,
  });

  return { roleRequest: data, isError };
};
