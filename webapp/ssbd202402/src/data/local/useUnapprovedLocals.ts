import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { AllLocals } from "@/types/mol/Locals";

export const useUnapprovedLocals = () => {
  const { api } = useAxiosPrivate();
  const { data } = useQuery({
    queryKey: ["unapprovedLocals"],
    queryFn: async () => {
      const response = await api.get<AllLocals[]>("/locals/unapproved");
      return response.data;
    },
  });

  return { unapprovedLocals: data };
};
