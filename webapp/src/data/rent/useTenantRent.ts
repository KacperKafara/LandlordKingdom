import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { TenantOwnRents } from "@/types/tenant/rentForTenant";

export const useTenantRent = (id: string) => {
  const { api } = useAxiosPrivate();
  const { data } = useQuery({
    queryKey: ["tenantRent", id],
    queryFn: async () => {
      const response = await api.get<TenantOwnRents>(`/me/rents/${id}`);
      return response.data;
    },
  });

  return { rent: data };
};
