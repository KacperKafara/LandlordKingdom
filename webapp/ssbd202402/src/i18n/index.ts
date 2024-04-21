import i18next from "i18next";
import { initReactI18next } from "react-i18next";
import pl from "./pl/common";

export const defaultNS = "common";

i18next.use(initReactI18next).init({
  debug: true,
  fallbackLng: "pl",
  defaultNS,
  resources: {
    pl: {
      common: pl,
    },
  },
});

export default i18next;
