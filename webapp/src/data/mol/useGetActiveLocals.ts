import useAxiosPrivate from "../useAxiosPrivate";
import { useQuery } from "@tanstack/react-query";
import {ActiveLocals} from "@/types/mol/Locals.ts";

interface ActiveLocalsRequest {
  pageNumber: number;
  pageSize: number;
  city?: string | null;
  minSize?: number | null;
  maxSize?: number | null;
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
      request.city,
      request.minSize,
      request.maxSize,
    ],
    queryFn: async () => {
      if (request.city !== null || request.minSize !== null || request.maxSize !== null) {
        const response = await api.post<ActiveLocalsResponse>("/locals/active/filters",
          {
            city: request.city,
            minSize: request.minSize,
            maxSize: request.maxSize,
          },
          {
            params: {
              page: request.pageNumber,
              size: request.pageSize,
            },
          });
        return response.data;
      } else {
        const response = await api.get<ActiveLocalsResponse>("/locals/active", {
          params: {
            page: request.pageNumber,
            size: request.pageSize,
          },
        });
        return response.data;
      }
    },
  });
};
