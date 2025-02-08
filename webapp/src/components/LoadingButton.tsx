import { FC } from "react";
import { Button } from "./ui/button";
import { Loader2 } from "lucide-react";

interface Props extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  isLoading: boolean;
  disableButton?: boolean;
  text: string;
}

const LoadingButton: FC<Props> = ({
  text,
  disableButton,
  isLoading,
  ...props
}) => {
  return (
    <>
      <Button {...props} disabled={isLoading || disableButton}>
        {isLoading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
        {text}
      </Button>
    </>
  );
};

export default LoadingButton;
