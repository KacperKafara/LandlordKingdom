import {useMutation, useQueryClient} from "@tanstack/react-query";
import { api } from "./api";
import {useToast} from "@/components/ui/use-toast.ts";
import {useTranslation} from "react-i18next";

export const useUnblockUser = () => {
    const { toast } = useToast();
    const queryClient = useQueryClient();
    const { t } = useTranslation();
    const { mutateAsync } = useMutation({
        mutationFn: async (userId: string) => {
            await api.post(`/users/${userId}/unblock`);
        },
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ["user"]})
            queryClient.invalidateQueries({queryKey: ["users"]})
            toast({
                title: t("block.unblockUserToastTitleSuccess"),
                description: t("block.unblockUserToastDescriptionSuccess"),
            });
        },
        onError: () => {
            toast({
                title: t("block.unblockUserToastTitleFail"),
                description: t("block.unblockUserToastDescriptionFail")
            });
        },
    });

    return { unblockUser: mutateAsync };
};
