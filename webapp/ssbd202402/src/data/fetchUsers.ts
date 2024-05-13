import { AxiosInstance } from "axios";
import { UserResponse } from "@/types/user/UserResponseType";
import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "./useAxiosPrivate";

export const fetchUsers = async (api: AxiosInstance) => {
  const response = await api.get<UserResponse[]>("/users");
  return response.data;
};

export const useFetchUsersQuery = () => {
  const { api } = useAxiosPrivate();
  return useQuery({
    queryKey: ["users"],
    queryFn: () => fetchUsers(api),
  });
};
