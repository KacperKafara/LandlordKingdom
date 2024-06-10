import { useMutation } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate.ts";
import { useTranslation } from "react-i18next";
import { useToast } from "@/components/ui/use-toast.ts";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode.ts";

type UpdateLocalData = {
    id: string;
    name: string;
    description: string;
    size: number;
}

export const useUpdateLocalData = () => {
    const { toast } = useToast();
    const { t } = useTranslation();
    const { api } = useAxiosPrivate();

    return useMutation({
        mutationFn: async (data: UpdateLocalData) => {
            await api.put(`/me/locals/${data.id}`, {
                id: data.id,
                name: data.name,
                description: data.description,
                size: data.size
            });
        },
        onSuccess: () => {
            toast({
                title: t("updateLocalPage.successTitle"),
                description: t("updateLocalPage.successDescription"),
            });
        },
        onError: (error: AxiosError) => {
            toast({
                variant: "destructive",
                title: t("updateLocalPage.errorTitle"),
                description: t(
                    `errors.${(error.response?.data as ErrorCode).exceptionCode}`
                ),
            });
        },
    });
};
