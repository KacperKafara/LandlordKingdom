import { LocalState } from "@/@types/localState";
import { Address } from "../Address";

export interface AllLocals {
  id: string;
  name: string;
  ownerLogin: string;
  description: string;
  state: LocalState;
  size: number;
  marginFee: number;
  rentalFee: number;
  nextMarginFee: number;
  nextRentalFee: number;
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
  nextMarginFee: number;
  nextRentalFee: number;
  address: Address;
}

export interface ActiveLocals {
  id: string;
  name: string;
  ownerLogin: string;
  description: string;
  size: number;
  marginFee: number;
  rentalFee: number;
  address: Address;
}
