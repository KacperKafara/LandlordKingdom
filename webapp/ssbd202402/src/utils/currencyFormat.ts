import i18n from "../i18n";

export const toLocaleFixed = (value: number) => {
    const language = i18n.language;
  return value.toFixed(2).replace(".", language === "en" ? "." : ",").toString();
}