import defaultNS from "@/i18n/pl/common";

declare module "i18next" {
  interface CustomTypeOptions {
    defaultNS: "common";
    resources: {
      common: typeof defaultNS;
    };
  }
}
