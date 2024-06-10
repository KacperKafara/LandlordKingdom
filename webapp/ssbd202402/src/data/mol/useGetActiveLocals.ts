import { ActiveLocals } from "@/types/mol/Locals";
import useAxiosPrivate from "../useAxiosPrivate";
import { useQuery } from "@tanstack/react-query";

export const useGetActiveLocals = () => {
  const { api } = useAxiosPrivate();

  return useQuery({
    queryKey: ["activeLocals"],
    queryFn: async () => {
      const response = await api.get<ActiveLocals[]>("/locals/active");
      return response.data;
    },
  });
};
