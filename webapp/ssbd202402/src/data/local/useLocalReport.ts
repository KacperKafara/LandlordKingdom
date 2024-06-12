import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";

type LocalReport = {
  id: string;
  name: string;
  payments: 
}

export const useLocalReport = (id: string) => {
  const { api } = useAxiosPrivate();
  const {data} = useQuery({
    queryKey: ["localRaport", id],
    queryFn: async () => {
      const response = await api.get(`/locals/${id}/report`);
      return response.data;
    },
  });
};
