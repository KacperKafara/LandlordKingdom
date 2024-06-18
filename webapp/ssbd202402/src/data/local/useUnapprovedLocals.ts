import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { AllLocals } from "@/types/mol/Locals";

interface UnapprovedLocalsRequest {
  pageNumber: number;
  pageSize: number;
}

interface UnapprovedLocalsResponse {
  locals: AllLocals[];
  totalPages: number;
}

export const useUnapprovedLocals = (request: UnapprovedLocalsRequest) => {
  const { api } = useAxiosPrivate();
  const { data, isLoading } = useQuery({
    queryKey: ["unapprovedLocals", request.pageNumber, request.pageSize],
    queryFn: async () => {
      const response = await api.get<UnapprovedLocalsResponse>(
        "/locals/unapproved",
        {
          params: {
            page: request.pageNumber,
            size: request.pageSize,
          },
        }
      );
      return response.data;
    },
  });

  return { unapprovedLocalsPage: data, isLoading };
};
