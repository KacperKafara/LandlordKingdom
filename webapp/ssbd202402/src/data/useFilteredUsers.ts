import { useUsersFilterStore } from "@/store/usersFilterStore";
import { useQuery } from "@tanstack/react-query";
import { api } from "./api";
import { UserResponse } from "@/types/user/UserResponseType";

export const useFilteredUsers = () => {
  const { criteria, pageNumber, pageSize } = useUsersFilterStore();

  const { data, isLoading } = useQuery({
    queryKey: ["filteredUsers", criteria, pageNumber, pageSize],
    queryFn: async () => {
      const query = `?pageNum=${pageNumber}&pageSize=${pageSize}`;
      const result = await api.post("/users/filtered" + query, criteria);
      return result.data;
    },
  });

  return { users: data as UserResponse[], isLoading };
};
