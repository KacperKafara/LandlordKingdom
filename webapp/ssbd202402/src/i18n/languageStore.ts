import i18next from "i18next";
import { create } from "zustand";

interface LanguageStore {
  language?: string;
  setLanguage: (language: string) => void;
}

const LSLanguage = localStorage.getItem("language");

export const useLanguageStore = create<LanguageStore>((set) => ({
  language: LSLanguage === null ? undefined : LSLanguage,
  setLanguage: (language: string) =>
    set(() => {
      if (language !== "en" && language !== "pl") {
        localStorage.setItem("language", "en");
        i18next.changeLanguage("en");
        return { language: "en" };
      }
      localStorage.setItem("language", language);
      i18next.changeLanguage(language);
      return { language };
    }),
}));
