import { useMutation } from "@tanstack/react-query";
import { api } from "./api";

type ResetPasswordRequest = {
  email: string;
};

export const useResetPassword = () => {
  const { mutateAsync } = useMutation({
    mutationFn: async (request: ResetPasswordRequest) => {
      const response = await api.post(`/users/reset-password`, request);
      return response.status;
    },
  });

  return { resetPassword: mutateAsync };
};
