import { useMutation } from "@tanstack/react-query";
import { api } from "./api";

export const useResetOtherUserPassword = () => {
  const { mutateAsync } = useMutation({
    mutationFn: async (login: string) => {
      const response = await api.post(`/user/reset-password?login=${login}`);
      return response.status;
    },
  });

  return { resetPassword: mutateAsync };
};
