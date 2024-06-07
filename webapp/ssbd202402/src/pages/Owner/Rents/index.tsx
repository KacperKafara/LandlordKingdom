import { useGetOwnerCurrentRents } from "@/data/mol/useGetOwnerCurrentRents";
import { FC } from "react";

const RentsPage: FC = () => {
  const { data: rents, isLoading } = useGetOwnerCurrentRents();
  console.log(rents);
  return <div>RentsPage</div>;
};

export default RentsPage;
