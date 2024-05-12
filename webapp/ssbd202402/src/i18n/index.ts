import i18next from "i18next";
import { initReactI18next } from "react-i18next";
import pl from "./pl/common";
import en from "./en/common";

export const defaultNS = "common";

let userLang = "en";
const storedLanguage = localStorage.getItem("language");

if (storedLanguage) {
  userLang = storedLanguage;
} else {
  userLang = navigator.language;

  if (userLang !== "pl") {
    userLang = "en";
  }
  localStorage.setItem("language", userLang);
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
