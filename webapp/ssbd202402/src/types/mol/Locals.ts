import { LocalState } from "@/@types/localState";

export interface AllLocals {
  id: string;
  name: string;
  ownerLogin: string;
  description: string;
  state: LocalState;
  size: number;
  marginFee: number;
  rentalFee: number;
  address: Address;
}

export interface OwnLocals {
  id: string;
  name: string;
  description: string;
  state: LocalState;
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
