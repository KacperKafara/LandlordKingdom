import useAxiosPrivate from "../useAxiosPrivate";
import {useQuery} from "@tanstack/react-query";
import {toast} from "@/components/ui/use-toast";
import {useTranslation} from "react-i18next";
import {AxiosError} from "axios";
import {ErrorCode} from "@/@types/errorCode";
import {LocalApplications} from "@/types/local/LocalApplications.ts";

export const useGetLocalApplications = (id: string) => {
    const { api } = useAxiosPrivate();
    const { t } = useTranslation();

    const { data } = useQuery({
        queryKey: ["localApplications"],
        queryFn: async () => {
            try {
                const response = await api.get<LocalApplications>(
                    `/me/locals/${id}/applications`
                );
                return response.data;
            } catch (error) {
                const axiosError = error as AxiosError;
                toast({
                    variant: "destructive",
                    title: t("localApplications.errorTitle"),
                    description: t(
                        `errors.${(axiosError.response!.data as ErrorCode).exceptionCode}`
                    ),
                });
                return Promise.reject(error);
            }
        },
    });

    return { applications: data };
};
