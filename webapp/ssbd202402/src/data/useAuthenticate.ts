import { useMutation } from "react-query";
import { api } from "./api";

type AuthenticateRequest = {
  login: string;
  password: string;
};

type AuthenticateResponse = {
  token: string;
};

export const useAuthenticate = () => {
  const { mutateAsync } = useMutation({
    mutationFn: async (data: AuthenticateRequest) => {
      const response = await api.post<AuthenticateResponse>(
        "/auth/signin",
        data
      );
      return response.data;
    },
  });

  return { authenticate: mutateAsync };
};
