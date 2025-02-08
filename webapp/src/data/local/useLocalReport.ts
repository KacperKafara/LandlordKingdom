import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";

export type LocalReport = {
  id: string;
  name: string;
  payments: Payment[];
  variableFees: VariableFee[];
  fixedFees: FixedFee[];
  rentCount: number;
  longestRentDays: number;
  shortestRentDays: number;
};

export type VariableFee = {
  date: string;
  amount: number;
};

export type FixedFee = {
  date: string;
  rentalFee: number;
  marginFee: number;
};

export type Payment = {
  date: string;
  amount: number;
};

export const useLocalReport = (id: string) => {
  const { api } = useAxiosPrivate();
  const { data } = useQuery({
    queryKey: ["localRaport", id],
    queryFn: async () => {
      const response = await api.get<LocalReport>(
        `/locals/${id}/report?startDate=2021-01-01&endDate=2021-12-31`
      );
      return response.data;
    },
  });

  return { report: data };
};
