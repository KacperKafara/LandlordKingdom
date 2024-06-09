export type OwnLocalDetails = {
  name: string;
  size: number;
  description: string;
  address: {
    country: string;
    city: string;
    street: string;
    number: string;
    zipCode: string;
  };
  marginFee: number;
  rentalFee: number;
  state: string;
};
