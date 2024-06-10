export type LocalDetailsForAdmin = {
  name: string;
  size: number;
  description: string;
  owner: {
    userId: string;
    firstName: string;
    lastName: string;
    login: string;
    email: string;
  };
  address: {
    country: string;
    city: string;
    street: string;
    number: string;
    zipCode: string;
  };
  marginFee: number;
  rentalFee: number;
  nextMarginFee: number;
  nextRentalFee: number;
  state: string;
};
