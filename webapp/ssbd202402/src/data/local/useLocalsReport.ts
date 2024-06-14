import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { FixedFee, Payment, VariableFee } from "./useLocalReport";

export type LocalsReport = {
  payments: Payment[];
  variableFees: VariableFee[];
  fixedFees: FixedFee[];
};

export const useLocalsReport = () => {
  const { api } = useAxiosPrivate();
  const query = useQuery({
    queryKey: ["localsReport"],
    queryFn: async () => {
      const response = await api.get<LocalsReport>("/locals/report");
      return response.data;
    },
  });

  return { report: query.data };
};
