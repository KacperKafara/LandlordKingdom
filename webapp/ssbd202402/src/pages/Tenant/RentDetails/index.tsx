import { useTenantRent } from "@/data/rent/useTenantRent";
import { FC } from "react";
import { useParams } from "react-router-dom";

const RentDetailsPage: FC = () => {
  const { id } = useParams<{ id: string }>();
  const { rent } = useTenantRent(id!);
  console.log(rent);
  return <></>;
};

export default RentDetailsPage;
