import { api } from "./api";
import { DetailedUserResponse } from "@/types/user/DetailedUserResponseType";

export const fetchUser = async (id: string) => {
  const response = await api.get<DetailedUserResponse>(`/users/${id}`);
  return response.data;
};
