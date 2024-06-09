import { FC } from "react";
import { useLeaveLocal } from "@/data/mol/useLeaveLocal";
import { Button } from "@/components/ui/button";
import { useTranslation } from "react-i18next";
import { cn } from "@/lib/utils";
import { HTMLAttributes } from "react";

type LeaveLocalProps = {
    id: string;
} & HTMLAttributes<HTMLButtonElement>;

export const LeaveLocalButton : FC<LeaveLocalProps> = ({id, className,  ...tags}) => {
    const { mutate, isPending } = useLeaveLocal();
    const { t } = useTranslation();

    return (
        <>
            <Button variant={"destructive"} className={cn(className)} {...tags} disabled={isPending} onClick={() => mutate(id)}>
                {t("leaveLocal.buttonText")}
            </Button>

        </>
    );
}
