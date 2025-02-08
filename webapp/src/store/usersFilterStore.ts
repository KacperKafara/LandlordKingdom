import { UsersSearchCriteria } from "@/types/filter/UsersSearchCriteria";
import { create } from "zustand";

type UsersFilterStore = {
  criteria: UsersSearchCriteria;
  pageNumber: number;
  pageSize: number;
  totalPages: number;
  setSearchCriteriaList: (list: UsersSearchCriteria) => void;
  setPageNumber: (number: number) => void;
  setPageSize: (number: number) => void;
  setTotalPages: (number: number) => void;
};

export const useUsersFilterStore = create<UsersFilterStore>((set) => ({
  criteria: {
    dataOption: "all",
    searchCriteriaList: [],
    role: "ALL",
  },
  pageNumber: 0,
  pageSize: 10,
  totalPages: 0,
  setSearchCriteriaList: (list: UsersSearchCriteria) => set({ criteria: list }),
  setPageNumber: (number: number) => set({ pageNumber: number }),
  setPageSize: (number: number) => set({ pageSize: number }),
  setTotalPages: (number: number) => set({ totalPages: number }),
}));
