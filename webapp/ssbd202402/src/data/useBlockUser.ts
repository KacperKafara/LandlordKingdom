import {useMutation, useQueryClient} from "@tanstack/react-query";
import { api } from "./api";
import {useToast} from "@/components/ui/use-toast.ts";
import {useTranslation} from "react-i18next";

export const useBlockUser = () => {
    const { toast } = useToast();
    const queryClient = useQueryClient();
    const { t } = useTranslation();
    const { mutateAsync } = useMutation({
        mutationFn: async (userId: string) => {
            await api.post(`/users/${userId}/block`);
        },
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ["user"]})
            queryClient.invalidateQueries({queryKey: ["users"]})
            toast({
                title: t("block.blockUserToastTitleSuccess"),
                description: t("block.blockUserToastDescriptionSuccess"),
            });
        },
        onError: () => {
            toast({
                title: t("block.blockUserToastTitleFail"),
                description: t("block.blockUserToastDescriptionFail")
            });
        },
    });

    return { blockUser: mutateAsync };
};
