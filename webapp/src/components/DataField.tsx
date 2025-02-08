import { cn } from "@/lib/utils";
import { FC } from "react";

type DataFieldProps = {
  label: string;
  value: string | number;
  className?: string;
};

const DataField: FC<DataFieldProps> = ({ label, value, className }) => {
  return (
    <div className={cn("flex flex-col", className)}>
      <div className="text-sm font-semibold">{label}</div>
      <div className="min-h-6">{value}</div>
    </div>
  );
};

export default DataField;
