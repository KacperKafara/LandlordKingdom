import { UsersSearchCriteria } from "@/types/filter/UsersSearchCriteria";
import { create } from "zustand";

type UsersFilterStore = {
  criteria: UsersSearchCriteria;
  pageNumber: number;
  pageSize: number;
  setSearchCriteriaList: (list: UsersSearchCriteria) => void;
  setPageNumber: (number: number) => void;
  setPageSize: (number: number) => void;
};

export const useUsersFilterStore = create<UsersFilterStore>((set) => ({
  criteria: {
    dataOption: "all",
    searchCriteriaList: [],
    roles: ["TENANT", "OWNER", "ADMINISTRATOR"],
  },
  pageNumber: 0,
  pageSize: 10,
  setSearchCriteriaList: (list: UsersSearchCriteria) => set({ criteria: list }),
  setPageNumber: (number: number) => set({ pageNumber: number }),
  setPageSize: (number: number) => set({ pageSize: number }),
}));
