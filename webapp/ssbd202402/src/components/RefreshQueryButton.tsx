import { Button } from "./ui/button";
import { useQueryClient } from "@tanstack/react-query";
import { FC } from "react";
import { FiRefreshCw } from "react-icons/fi";




type RefreshQueryButtonProps = {
    queryKeys: string[];
    children?: React.ReactNode;
} & React.HTMLAttributes<HTMLButtonElement>;

const RefreshQueryButton: FC<RefreshQueryButtonProps> = ({ queryKeys, children, ...tags }) => {
    const queryClient = useQueryClient();
    const refresh = () => {
        queryClient.invalidateQueries({ queryKey: [...queryKeys] });

    };

    return <Button {...tags} variant="ghost" className="flex gap-2 align-middle " onClick={refresh}>
        <FiRefreshCw size={20} />
        {children}
    </Button>;
};

export default RefreshQueryButton;

