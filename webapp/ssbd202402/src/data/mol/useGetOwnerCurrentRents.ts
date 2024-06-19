import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { OwnerCurrentRent } from "@/types/mol/OwnerCurrentRent";

interface OwnRentsRequest {
  pageNumber: number;
  pageSize: number;
}

interface OwnRentsResponse {
  rents: OwnerCurrentRent[];
  pages: number;
}
export const useGetOwnerCurrentRents = (request: OwnRentsRequest) => {
  const { api } = useAxiosPrivate();

  return useQuery({
    queryKey: [
      "ownerCurrentRents",
      request.pageNumber,
      request.pageSize,
    ],
    queryFn: async () => {
      const response = await api.get<OwnRentsResponse>("/me/rents/current", {
        params: {
          page: request.pageNumber,
          size: request.pageSize,
        },
      });
      return response.data;
    },
  });
};
