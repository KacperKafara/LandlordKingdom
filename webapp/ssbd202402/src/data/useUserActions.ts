import { useToast } from "@/components/ui/use-toast";
import { useBlockUser } from "./useBlockUser";
import { useUnblockUser } from "./useUnblockUser";
import { useTranslation } from "react-i18next";

export const useUserActions = () => {
    const { toast } = useToast();
    const { t } = useTranslation();
    const { blockUser } = useBlockUser();
    const { unblockUser } = useUnblockUser();

    const handleBlockUser = async (userId: string) => {
        const result = await blockUser(userId);
        if (result === 200) {
            toast({
                title: t("block.blockUserToastTitleSuccess"),
                description: t("block.blockUserToastDescriptionSuccess"),
            });
            window.location.reload();
        } else {
            toast({
                title: t("block.blockUserToastTitleFail"),
                description: t("block.blockUserToastDescriptionFail"),
            });
        }
    };

    const handleUnblockUser = async (userId: string) => {
        const result = await unblockUser(userId);
        if (result === 200) {
            toast({
                title: t("block.unblockUserToastTitleSuccess"),
                description: t("block.unblockUserToastDescriptionSuccess"),
            });
            window.location.reload();
        } else {
            toast({
                title: t("block.unblockUserToastTitleFail"),
                description: t("block.unblockUserToastDescriptionFail"),
            });
        }
    };

    return { handleBlockUser, handleUnblockUser };
};
