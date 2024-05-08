import { useMutation } from "@tanstack/react-query";
import { api } from "./api";

export const useResetOtherUserPassword = () => {
  const { mutateAsync } = useMutation({
    mutationFn: async (email: string) => {
      const response = await api.post(`/users/reset-password?email=${email}`);
      return response.status;
    },
  });

  return { resetPassword: mutateAsync };
};
