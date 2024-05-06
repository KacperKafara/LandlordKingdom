import { useMutation } from "@tanstack/react-query";
import { api } from "./api";

type ChangePasswordType = {
  password: string;
  token: string;
};

export const useChangeUserPassword = () => {
  const { mutateAsync } = useMutation({
    mutationFn: async (data: ChangePasswordType) => {
      const response = await api.post("/me/change-password", data);
      console.log(response);
      return response.status;
    },
  });

  return { changePassword: mutateAsync };
};
