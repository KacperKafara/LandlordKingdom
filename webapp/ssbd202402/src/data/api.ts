import axios from "axios";

export const api = axios.create({
  baseURL: "https://team-2.proj-sum.it.p.lodz.pl/api/v1/",
  headers: {
    "Content-Type": "application/json",
  },
});
