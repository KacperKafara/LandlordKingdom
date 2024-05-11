import i18next from "i18next";
import { initReactI18next } from "react-i18next";
import pl from "./pl/common";
import en from "./en/common";

export const defaultNS = "common";
let userLang = navigator.language;

if (userLang !== "pl") {
  userLang = "en";
}

i18next.use(initReactI18next).init({
  debug: true,
  fallbackLng: userLang,
  defaultNS,
  resources: {
    pl: {
      common: pl,
    },
    en: {
      common: en,
    },
  },
});

export default i18next;
