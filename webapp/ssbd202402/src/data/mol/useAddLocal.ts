import { useMutation } from "@tanstack/react-query";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode.ts";
import useAxiosPrivate from "@/data/useAxiosPrivate.ts";
import { AddLocal } from "@/types/mol/Locals.ts";
import { toast } from "@/components/ui/use-toast.ts";
import { useTranslation } from "react-i18next";

export const useAddLocal = () => {
  const { api } = useAxiosPrivate();
  const { t } = useTranslation();

  const { mutateAsync, isPending } = useMutation({
    mutationFn: async (data: AddLocal) => {
      const result = await api.post("/locals", data);
      return result.data;
    },
    onSuccess: () => {
      toast({
        variant: "success",
        title: t("addLocalPage.successTitle"),
        description: t("addLocalPage.successDescription"),
      });
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("addLocalPage.error"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });

  return { addLocal: mutateAsync, isPending };
};
