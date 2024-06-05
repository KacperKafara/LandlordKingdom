import { AllLocals } from "@/types/mol/Locals";
import useAxiosPrivate from "../useAxiosPrivate";
import { useQuery } from "@tanstack/react-query";

export const useGetAllLocals = () => {
  const { api } = useAxiosPrivate();

  return useQuery({
    queryKey: ["allLocals"],
    queryFn: async () => {
      const response = await api.get<AllLocals[]>("/locals");
      return response.data;
    },
  });
};
