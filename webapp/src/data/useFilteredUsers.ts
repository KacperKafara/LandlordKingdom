import { useUsersFilterStore } from "@/store/usersFilterStore";
import { useQuery } from "@tanstack/react-query";
import { api } from "./api";
import { UserResponse } from "@/types/user/UserResponseType";
import {AxiosError} from "axios";
import {toast} from "@/components/ui/use-toast.ts";
import {t} from "i18next";
import {ErrorCode} from "@/@types/errorCode.ts";

export const useFilteredUsers = () => {
  const { criteria, pageNumber, pageSize, setTotalPages } =
    useUsersFilterStore();

  const { data, isLoading } = useQuery({
    queryKey: ["filteredUsers", criteria, pageNumber, pageSize],
    queryFn: async () => {
      try {
        const query = `?pageNum=${pageNumber}&pageSize=${pageSize}`;
        const result = await api.post("/users/filtered" + query, criteria);
        setTotalPages(result.data.totalPages);
        return result.data.users;
      } catch (error) {
        const axiosError = error as AxiosError;
        toast({
          variant: "destructive",
          title: t("userDataPage.error"),
          description: t(
              `errors.${(axiosError.response?.data as ErrorCode).exceptionCode}`
          ),
        });
        return Promise.reject(error);
      }
    },
  });

  return { users: data as UserResponse[], isLoading };
};
