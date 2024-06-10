import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { OwnApplication } from "@/types/application/OwnApplication";

export const useGetUserApplication = (id: string) => {
  const { api } = useAxiosPrivate();

  const { data, isLoading, isError } = useQuery({
    queryKey: ["userApplication", id],
    queryFn: async () => {
      try {
        const response = await api.get<OwnApplication>(
          `/locals/${id}/applications/me`
        );
        return response.data;
      } catch (error) {
        return Promise.reject(error);
      }
    },
  });

  return { application: data, isLoading, isError };
};
