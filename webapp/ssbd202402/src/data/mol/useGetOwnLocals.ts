import { OwnLocals } from "@/types/mol/Locals";
import useAxiosPrivate from "../useAxiosPrivate";
import { useQuery } from "@tanstack/react-query";

interface OwnLocalsRequest {
  pageNumber: number;
  pageSize: number;
  state: string;
}

interface OwnLocalsResponse {
  locals: OwnLocals[];
  totalPages: number;
}

export const useGetOwnLocals = (request: OwnLocalsRequest) => {
  const { api } = useAxiosPrivate();

  return useQuery({
    queryKey: [
      "ownLocals",
      request.pageNumber,
      request.pageSize,
      request.state,
    ],
    queryFn: async () => {
      const response = await api.get<OwnLocalsResponse>("/me/locals", {
        params: {
          page: request.pageNumber,
          size: request.pageSize,
          state: request.state,
        },
      });
      return response.data;
    },
  });
};
