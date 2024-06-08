import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { OwnerCurrentRent } from "@/types/mol/OwnerCurrentRent";

export const useGetOwnerCurrentRents = () => {
  const { api } = useAxiosPrivate();

  return useQuery({
    queryKey: ["ownerCurrentRents"],
    queryFn: async () => {
      const response = await api.get<OwnerCurrentRent[]>("/me/rents/current");
      return response.data;
    },
  });
};
