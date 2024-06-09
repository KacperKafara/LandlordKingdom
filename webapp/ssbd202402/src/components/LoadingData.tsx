import { RefreshCw } from "lucide-react";

export const LoadingData = () => {
  return (
    <div className="flex h-full justify-center">
      <div className="mt-10 h-full">
        <RefreshCw className="size-14 animate-spin" />
      </div>
    </div>
  );
};
