import { Address } from "@/types/Address";

export const getAddressString = (address: Address | undefined) => {
  if (!address) {
    return "";
  }
  return `${address.street} ${address.number}, ${address.zipCode} ${address.city}`;
};
