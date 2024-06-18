import { ActiveLocals } from "@/types/mol/Locals";
import useAxiosPrivate from "../useAxiosPrivate";
import { useQuery } from "@tanstack/react-query";

interface ActiveLocalsRequest {
  pageNumber: number;
  pageSize: number;
}

interface ActiveLocalsResponse {
  locals: ActiveLocals[];
  pages: number;
}
export const useGetActiveLocals = (request: ActiveLocalsRequest) => {
  const { api } = useAxiosPrivate();

  return useQuery({
    queryKey: [
      "activeLocals",
      request.pageNumber,
      request.pageSize,
    ],
    queryFn: async () => {
      const response = await api.get<ActiveLocalsResponse>("/locals/active", {
        params: {
          page: request.pageNumber,
          size: request.pageSize,
        },
      });
      return response.data;
    },
  });
};
