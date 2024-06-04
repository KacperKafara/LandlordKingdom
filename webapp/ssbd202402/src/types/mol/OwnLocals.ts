export interface OwnLocals {
  id: string;
  name: string;
  description: string;
  state: string;
  size: number;
  marginFee: number;
  rentalFee: number;
  address: Address;
}

export interface Address {
  country: string;
  city: string;
  street: string;
  number: string;
  zipCode: string;
}
