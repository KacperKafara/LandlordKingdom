import { FC } from "react";
import { Button } from "./ui/button";
import { Loader2 } from "lucide-react";

interface Props extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  isLoading: boolean;
  text: string;
}

const LoadingButton: FC<Props> = ({ text, isLoading, ...props }) => {
  return (
    <>
      <Button {...props} disabled={isLoading}>
        {isLoading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
        {text}
      </Button>
    </>
  );
};

export default LoadingButton;
