import i18n from "../i18n";

export const toLocaleFixed = (value: number) => {
  return new Intl.NumberFormat(i18n.language, {
    style: "currency",
    currency: "PLN",
  }).format(value);
};
