import { create } from "zustand";

type DialogType = "roleRequest";

type DialogStore = {
  opened?: DialogType;
  isOpened: (dialog: DialogType) => boolean;
  openDialog: (dialog: DialogType) => void;
  closeDialog: () => void;
};

export const useDialogStore = create<DialogStore>((set, get) => ({
  opened: undefined,
  isOpened: (dialog) => get().opened === dialog,
  openDialog: (dialog: DialogType) => set({ opened: dialog }),
  closeDialog: () => set({ opened: undefined }),
}));
