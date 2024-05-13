import { useQuery } from "@tanstack/react-query";
import { DetailedUserResponse } from "@/types/user/DetailedUserResponseType";
import { AxiosInstance } from "axios";
import useAxiosPrivate from "./useAxiosPrivate";

export const fetchUser = async (id: string, api: AxiosInstance) => {
  const response = await api.get<DetailedUserResponse>(`/users/${id}`);
  return response.data;
};

export const useGetUserQuery = (id: string) => {
  const { api } = useAxiosPrivate();
  return useQuery({
    queryKey: ["user", id],
    queryFn: () => (id ? fetchUser(id, api) : Promise.resolve(null)),
  });
};
