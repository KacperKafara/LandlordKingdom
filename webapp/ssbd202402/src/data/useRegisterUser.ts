import { useToast } from "@/components/ui/use-toast";
import { useMutation } from "@tanstack/react-query";
import { useTranslation } from "react-i18next";
import { api } from "./api";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";

type RegistrationRequest = {
  firstName: string;
  lastName: string;
  email: string;
  login: string;
  password: string;
  language: string;
};

export const useRegisterUser = () => {
  const { toast } = useToast();
  const { t } = useTranslation();

  const { mutateAsync, isPending } = useMutation({
    mutationFn: async (data: RegistrationRequest) => {
      await api.post("/auth/signup", data);
    },
    onSuccess: () => {
      toast({
        description: t("registerPage.registerSuccess"),
      });
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("registerPage.registerError"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });

  return { registerUserAsync: mutateAsync, isPending };
};
