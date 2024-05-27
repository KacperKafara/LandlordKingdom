import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "./useAxiosPrivate";

export const useAutocompletionQuery = (loginPattern: string) => {
  const { api } = useAxiosPrivate();

  const params = {
    query: loginPattern,
  };

  return useQuery({
    queryKey: ["autocompletion", loginPattern],
    queryFn: async () => {
      const response = await api.get<string[]>("/autocomplete/login", {
        params,
      });
      return response.data;
    },
  });
};
