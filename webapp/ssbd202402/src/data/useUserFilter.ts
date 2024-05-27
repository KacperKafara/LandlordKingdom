import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "./useAxiosPrivate";

type UserFilterResponse = {
  login: string;
  email: string;
  firstName: string;
  lastName: string;
  role: string;
  blocked: boolean;
  verified: boolean;
};

export const useUserFilter = () => {
  const { api } = useAxiosPrivate();
  const { data } = useQuery({
    queryKey: ["userFilter"],
    queryFn: async () => {
      const { data } = await api.get<UserFilterResponse>("/filter/user");
      return data;
    },
  });

  return { userFilter: data };
};
