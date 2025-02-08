import { Address } from "../Address";

export type TenantOwnRents = {
  id: string;
  local: {
    name: string;
    description: string;
    size: number;
    address: Address;
    marginFee: number;
    rentalFee: number;
  };
  owner: {
    firstName: string;
    lastName: string;
    login: string;
    email: string;
  };
  startDate: string;
  endDate: string;
  balance: number;
};
