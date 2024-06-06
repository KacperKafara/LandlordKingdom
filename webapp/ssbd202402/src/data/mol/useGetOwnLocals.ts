import { OwnLocals } from "@/types/mol/Locals";
import useAxiosPrivate from "../useAxiosPrivate";
import { useQuery } from "@tanstack/react-query";

export const useGetOwnLocals = () => {
  const { api } = useAxiosPrivate();

  return useQuery({
    queryKey: ["ownLocals"],
    queryFn: async () => {
      const response = await api.get<OwnLocals[]>("/me/locals");
      return response.data;
    },
  });
};
