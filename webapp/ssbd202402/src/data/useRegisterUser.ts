import { useToast } from "@/components/ui/use-toast";
import { useMutation } from "@tanstack/react-query";
import { useTranslation } from "react-i18next";
import { api } from "./api";
import { AxiosError } from "axios";

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
      if (error.response?.status === 422) {
        toast({
          variant: "destructive",
          title: t("registerPage.registerError"),
          description: t("registerPage.registerErrorIdenticalFields"),
        });
      } else {
        toast({
          variant: "destructive",
          title: t("registerPage.registerError"),
        });
      }
    },
  });

  return { registerUserAsync: mutateAsync, isPending };
};
