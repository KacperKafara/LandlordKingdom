import { api } from "./api";
import { UserResponse } from "@/types/user/UserResponseType";

export const fetchUsers = async () => {
  const response = await api.get<UserResponse[]>("/user");
  return response.data;
};
