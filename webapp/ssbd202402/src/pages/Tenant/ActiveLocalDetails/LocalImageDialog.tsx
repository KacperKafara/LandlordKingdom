import { Dialog, DialogContent } from "@/components/ui/dialog";
import { FC } from "react";

type LocalImageDialogProps = {
  id?: string;
  handleClose: () => void;
};

const LocalImageDialog: FC<LocalImageDialogProps> = ({ id, handleClose }) => {
  return (
    <Dialog open={id !== undefined} onOpenChange={handleClose}>
      <DialogContent>
        <div className="flex w-full items-center justify-center">
          <img
            src={`${import.meta.env.VITE_BACKEND_URL}/images/${id}`}
            alt="Local"
            className="max-h-[90%] object-cover"
          />
        </div>
      </DialogContent>
    </Dialog>
  );
};

export default LocalImageDialog;
