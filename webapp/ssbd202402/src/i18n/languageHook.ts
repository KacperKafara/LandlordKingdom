import i18next from "i18next";
import { useEffect, useState } from "react";

const useLanguage = () => {
  const [language, setLanguage] = useState<string>("");

  useEffect(() => {
    const storedLanguage = localStorage.getItem("language");

    if (storedLanguage) {
      setLanguage(storedLanguage);
    } else {
      let userLang = navigator.language;

      if (userLang !== "pl") {
        userLang = "en";
      }
    }
  }, []);

  useEffect(() => {
    if (language && (language === "en" || language === "pl")) {
      localStorage.setItem("language", language);
      i18next.changeLanguage(language);
    }
  }, [language]);

  const changeLanguage = (lang: string) => {
    setLanguage(lang);
  };

  return { language, changeLanguage };
};

export default useLanguage;
