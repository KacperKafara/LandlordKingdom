import { FC } from "react";

type DataFieldProps = {
  label: string;
  value: string;
};

const DataField: FC<DataFieldProps> = ({ label, value }) => {
  return (
    <div className="flex flex-col">
      <div className="text-sm font-semibold">{label}</div>
      <div className="h-6">{value}</div>
    </div>
  );
};

export default DataField;
